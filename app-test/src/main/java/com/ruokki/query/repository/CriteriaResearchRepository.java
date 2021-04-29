package com.ruokki.query.repository;

import com.ruokki.query.annotation.CommonCriteria;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;


@Repository
public class CriteriaResearchRepository implements DemoEntityRepository {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CriteriaResearchRepository.class);
    public static final String START = "Start";
    public static final String END = "End";
    private EntityManager entityManager;

    @Autowired
    public CriteriaResearchRepository(EntityManager entityManager) {
        log.info("Init");
        this.entityManager = entityManager;
    }

    public <T> List<T> searchByCriteria(CommonCriteria<T> commonCriteria) {

        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> query = criteriaBuilder.createQuery(commonCriteria.getFromClass());
        final Root<T> from = query.from(commonCriteria.getFromClass());
        List<Predicate> predicates = new ArrayList<>();

        Map<CommonCriteria.TypeCriteria, Map<String, ?>> packedCriteria = commonCriteria.getCriteriaMap();

        final Map<String, ?> dateMapCriteria = packedCriteria.getOrDefault(CommonCriteria.TypeCriteria.DATE, new HashMap<>());
        dateMapCriteria.forEach((fieldName, dateToCast) -> {
            if (dateToCast instanceof Date) {
                Date date = (Date) dateToCast;
                if (fieldName.endsWith(START)) {
                    final String fieldToQuery = fieldName.substring(0, fieldName.length() - 5);
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get(fieldToQuery), date));
                } else if (fieldName.endsWith(END)) {
                    final String fieldToQuery = fieldName.substring(0, fieldName.length() - 3);
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get(fieldToQuery), date));
                }
            }
        });

        final Map<String, ?> primitifMapCriteria = packedCriteria.getOrDefault(CommonCriteria.TypeCriteria.PRIMITIF, new HashMap<>());
        primitifMapCriteria.forEach((fieldName, listToCast) -> {
            if (listToCast instanceof List) {
                List<?> listOfValue = (List<?>) listToCast;
                final String fieldToQuery = fieldName;
                predicates.add(criteriaBuilder.in(from.get(fieldToQuery)).value(listOfValue));
            } else if (listToCast == null) {
                log.debug("Param {} is not set", fieldName);
            } else {
                log.error("listToCast is not a List");
            }
        });

        log.info("Launch request from criteria");
        final CriteriaQuery<T> where = query.where(predicates.stream().toArray(Predicate[]::new));

        return entityManager.createQuery(where).getResultList();
    }
}
