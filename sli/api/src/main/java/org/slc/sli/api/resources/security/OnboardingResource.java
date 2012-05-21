package org.slc.sli.api.resources.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.slc.sli.api.representation.EntityBody;
import org.slc.sli.api.resources.Resource;
import org.slc.sli.api.resources.security.TenantResource.LandingZoneInfo;
import org.slc.sli.api.resources.security.TenantResource.TenantResourceCreationException;
import org.slc.sli.api.util.SecurityUtil;
import org.slc.sli.common.constants.EntityNames;
import org.slc.sli.common.constants.ResourceConstants;
import org.slc.sli.domain.Entity;
import org.slc.sli.domain.NeutralCriteria;
import org.slc.sli.domain.NeutralQuery;
import org.slc.sli.domain.Repository;
import org.slc.sli.domain.enums.Right;

/**
 * Resources available to administrative apps during the onboarding and provisioning process.
 */
@Component
@Scope("request")
@Path("/provision")
@Produces({ Resource.JSON_MEDIA_TYPE })
public class OnboardingResource {

    @Autowired
    private Repository<Entity> repo;

    @Autowired
    private TenantResource tenantResource;
    
    //Use this to check if we're in sandbox mode
    @Value("${sli.simple-idp.sandboxImpersonationEnabled}")
    protected boolean isSandboxImpersonationEnabled;

    public static final String STATE_EDUCATION_AGENCY = "State Education Agency";
    public static final String STATE_EDORG_ID = "stateOrganizationId";
    public static final String EDORG_INSTITUTION_NAME = "nameOfInstitution";
    public static final String ADDRESSES = "address";
    public static final String ADDRESS_STREET = "streetNumberName";
    public static final String ADDRESS_CITY = "city";
    public static final String ADDRESS_STATE_ABRV = "stateAbbreviation";
    public static final String ADDRESS_POSTAL_CODE = "postalCode";
    public static final String CATEGORIES = "organizationCategories";  // 'State Education Agency'
    public static final String APPLICATION_RESOURCE_NAME = "application";
    public static final String APPLICATION_AUTH_RESOURCE_NAME = "applicationAuthorization";
    public static final String APPLICATION_NAME = "name";
    public static final String APPLICATION_AUTH_EDORGS = "authorized_ed_orgs";
    public static final String AUTH_TYPE_EDUCATION_ORGANIZATION = "EDUCATION_ORGANIZATION";
    public static final String AUTH_TYPE = "authType";
    public static final String AUTH_ID = "authId";
    public static final String APP_IDS = "appIds";
    public static final String APP_BOOTSTRAP = "bootstrap";

    private final String landingZoneServer;


    @Autowired
    public OnboardingResource(@Value("${sli.landingZone.server}") String landingZoneServer) {
        super();
        this.landingZoneServer = landingZoneServer;
    }

    /**
     * Provision a landing zone for the provide educational organization.
     *
     * @QueryParam stateOrganizationId -- the unique identifier for this ed org
     * @QueryParam tenantId -- the tenant ID for this edorg.
     */
    @POST
    public Response provision(Map<String, String> reqBody, @Context final UriInfo uriInfo) {
        String orgId = reqBody.get(STATE_EDORG_ID);
        String tenantId = reqBody.get(ResourceConstants.ENTITY_METADATA_TENANT_ID);

        // Ensure the user is an admin.
        Right requiredRight = Right.INGEST_DATA;
        if (isSandboxImpersonationEnabled) {
            requiredRight = Right.ADMIN_ACCESS;
        }
        
        if (!SecurityUtil.hasRight(requiredRight)) {
            EntityBody body = new EntityBody();
            body.put("response", "You are not authorized to provision a landing zone.");
            return Response.status(Status.FORBIDDEN).entity(body).build();
        }

        String edOrgId = "";
        Response r = createEdOrg(orgId, tenantId, edOrgId);

        if (Status.fromStatusCode(r.getStatus()) != Status.CREATED) {
            return r;
        }

        return r;
    }

    /**
     * Create an EdOrg if it does not exists.
     *
     * @param orgId
     *            The State Educational Organization identifier.
     * @param tenantId
     *            The EdOrg tenant identifier.
     * @return Response of the request as an HTTP Response.
     */
    public Response createEdOrg(final String orgId, final String tenantId, String uuid) {

        NeutralQuery query = new NeutralQuery();
        query.addCriteria(new NeutralCriteria(STATE_EDORG_ID, "=", orgId));
        query.addCriteria(new NeutralCriteria("metaData." + ResourceConstants.ENTITY_METADATA_TENANT_ID, "=", tenantId,
                false));

        if (repo.findOne(EntityNames.EDUCATION_ORGANIZATION, query) != null) {
            return Response.status(Status.CONFLICT).build();
        }

        EntityBody body = new EntityBody();
        body.put(STATE_EDORG_ID, orgId);
        body.put(EDORG_INSTITUTION_NAME, orgId);

        List<String> categories = new ArrayList<String>();
        categories.add(STATE_EDUCATION_AGENCY);
        body.put(CATEGORIES, categories);

        List<Map<String, String>> addresses = new ArrayList<Map<String, String>>();
        Map<String, String> address = new HashMap<String, String>();
        address.put(ADDRESS_STREET, "unknown");
        address.put(ADDRESS_CITY, "unknown");
        address.put(ADDRESS_STATE_ABRV, "NC");
        address.put(ADDRESS_POSTAL_CODE, "27713");
        addresses.add(address);

        body.put(ADDRESSES, addresses);

        Map<String, Object> meta = new HashMap<String, Object>();
        meta.put(ResourceConstants.ENTITY_METADATA_TENANT_ID, tenantId);

        Entity e = repo.create(EntityNames.EDUCATION_ORGANIZATION, body, meta, EntityNames.EDUCATION_ORGANIZATION);
        if (e == null) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        uuid = e.getEntityId();

        // retrieve the application ids for common applications that already exist in mongod
        List<String> appIds = getAppIds();

        // update common applications to include new edorg uuid in the field "authorized_ed_orgs"
        updateApps(uuid, appIds);

        // create or update the applicationAuthorization collection in mongod for new edorg entity
        createAppAuth(uuid, appIds);

        try {
            LandingZoneInfo landingZone = tenantResource.createLandingZone(tenantId, orgId);

            Map<String, String> returnObject = new HashMap<String, String>();
            returnObject.put("landingZone", landingZone.getLandingZonePath());
            returnObject.put("serverName", landingZoneServer);
            returnObject.put("edOrg", e.getEntityId());

            return Response.status(Status.CREATED).entity(returnObject).build();
        } catch (TenantResourceCreationException trce) {
            EntityBody entity = new EntityBody();
            body.put("message", trce.getMessage());
            return Response.status(trce.getStatus()).entity(entity).build();
        }
    }

    /**
     * @param commonAppNames
     *            collection of common application names
     * @return collection of common application id
     */
    private List<String> getAppIds() {
        List<String> appIds = new ArrayList<String>();
        NeutralQuery query = new NeutralQuery();
        query.addCriteria(new NeutralCriteria(APP_BOOTSTRAP, NeutralCriteria.OPERATOR_EQUAL, true));
        Iterable<String> ids = repo.findAllIds(APPLICATION_RESOURCE_NAME, query);
        for (String id : ids) {
            appIds.add(id);
        }
        return appIds;
    }

    /**
     * @param edOrgId
     *            the uuid of top level education organization
     * @param appIds
     *            collection of application id that the field "authorized_ed_orgs" need to be
     *            updated
     */
    @SuppressWarnings("unchecked")
    private void updateApps(String edOrgId, List<String> appIds) {
        for (String appId : appIds) {
            Entity app = repo.findById(APPLICATION_RESOURCE_NAME, appId);
            if (app != null) {
                List<String> authorizedEdOrgIds = (List<String>) app.getBody().get(APPLICATION_AUTH_EDORGS);
                if (authorizedEdOrgIds == null) {
                    authorizedEdOrgIds = new ArrayList<String>();
                    authorizedEdOrgIds.add(edOrgId);
                } else if (authorizedEdOrgIds.contains(edOrgId)) {
                    continue;
                } else {
                    authorizedEdOrgIds.add(edOrgId);
                }
                app.getBody().put(APPLICATION_AUTH_EDORGS, authorizedEdOrgIds);
                repo.update(APPLICATION_RESOURCE_NAME, app);
            }
        }
    }

    /**
     * @param edOrgId
     *            the uuid of top level education organization
     * @param appIds
     *            collection of application id that edorg need to be authorized
     */
    @SuppressWarnings("unchecked")
    private void createAppAuth(String edOrgId, List<String> appIds) {
        NeutralQuery query = new NeutralQuery();
        query.addCriteria(new NeutralCriteria(AUTH_ID, NeutralCriteria.OPERATOR_EQUAL, edOrgId));
        query.addCriteria(new NeutralCriteria(AUTH_TYPE, NeutralCriteria.OPERATOR_EQUAL,
                AUTH_TYPE_EDUCATION_ORGANIZATION));
        Entity appAuth = repo.findOne(APPLICATION_AUTH_RESOURCE_NAME, query);
        if (appAuth != null) {
            List<String> ids = (List<String>) appAuth.getBody().get(APP_IDS);
            for (String id : appIds) {
                if (!ids.contains(id)) {
                    ids.add(id);
                }
            }
            appAuth.getBody().put(APP_IDS, ids);
            repo.update(APPLICATION_AUTH_RESOURCE_NAME, appAuth);
        } else {
            EntityBody body = new EntityBody();
            body.put(AUTH_ID, edOrgId);
            body.put(AUTH_TYPE, AUTH_TYPE_EDUCATION_ORGANIZATION);
            body.put(APP_IDS, appIds);
            repo.create(APPLICATION_AUTH_RESOURCE_NAME, body);
        }
    }
}
