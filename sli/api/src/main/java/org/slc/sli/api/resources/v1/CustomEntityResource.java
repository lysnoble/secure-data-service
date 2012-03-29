package org.slc.sli.api.resources.v1;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slc.sli.api.config.EntityDefinition;
import org.slc.sli.api.representation.EntityBody;

/**
 * Subresource for custom entities
 * 
 */
public class CustomEntityResource {
    
    String entityId;
    EntityDefinition entityDef;
    
    public CustomEntityResource(String entityId, EntityDefinition entityDef) {
        this.entityId = entityId;
        this.entityDef = entityDef;
    }
    
    @GET
    @Path("/")
    public Response read() {
        if (entityDef == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        EntityBody entityBody = entityDef.getService().getCustom(entityId);
        return Response.status(Status.OK).entity(entityBody).build();
    }
    
    @PUT
    @Path("/")
    public Response createOrUpdatePut(EntityBody customEntity) {
        if (entityDef == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        entityDef.getService().createOrUpdateCustom(entityId, customEntity);
        return Response.status(Status.NO_CONTENT).build();
    }
    
    @POST
    @Path("/")
    public Response createOrUpdatePost(EntityBody customEntity) {
        if (entityDef == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        entityDef.getService().createOrUpdateCustom(entityId, customEntity);
        return Response.status(Status.NO_CONTENT).build();
    }
    
    @DELETE
    @Path("/")
    public Response delete() {
        if (entityDef == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        entityDef.getService().deleteCustom(entityId);
        return Response.status(Status.NO_CONTENT).build();
    }
}
