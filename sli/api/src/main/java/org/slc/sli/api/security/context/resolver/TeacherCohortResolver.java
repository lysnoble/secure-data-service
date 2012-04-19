package org.slc.sli.api.security.context.resolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slc.sli.api.security.context.AssociativeContextHelper;
import org.slc.sli.common.constants.EntityNames;
import org.slc.sli.domain.Entity;
import org.slc.sli.domain.NeutralCriteria;
import org.slc.sli.domain.NeutralQuery;
import org.slc.sli.domain.Repository;
/**
 * Resolves which cohorts a given teacher is allowed to see
 *
 * @author vmcglaughlin
 *
 */
@Component
public class TeacherCohortResolver implements EntityContextResolver {

    @Autowired
    private AssociativeContextHelper helper;

    @Autowired
    private Repository<Entity> repository;

    @Override
    public boolean canResolve(String fromEntityType, String toEntityType) {
        return EntityNames.TEACHER.equals(fromEntityType) && EntityNames.COHORT.equals(toEntityType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> findAccessible(Entity principal) {
        NeutralQuery neutralQuery = new NeutralQuery();

        List<String> referenceIds = new ArrayList<String>();
        referenceIds.add(principal.getEntityId());

        List<String> references = helper.findEntitiesContainingReference(EntityNames.STAFF_COHORT_ASSOCIATION, "staffId", referenceIds);

        if (references.isEmpty()) {
            return new ArrayList<String>();
        }

        neutralQuery.addCriteria(new NeutralCriteria("_id", "in", references));

        Iterable<Entity> staffCohortAssociations = repository.findAll(EntityNames.STAFF_COHORT_ASSOCIATION, neutralQuery);
        Set<String> cohortIds = new HashSet<String>();

        for (Entity staffCohortAssociation : staffCohortAssociations) {
            for (String cohortId : (List<String>) staffCohortAssociation.getBody().get("cohortId")) {
                if (cohortId != null) {
                    cohortIds.add(cohortId);
                }
            }
        }

        return new ArrayList<String>(cohortIds);
    }
}
