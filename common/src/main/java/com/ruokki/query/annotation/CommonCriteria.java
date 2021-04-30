package com.ruokki.query.annotation;

import java.util.Map;

/**
 * The type Common criteria.
 *
 * @param <T> the type parameter
 */
public abstract class CommonCriteria<T> {
    /**
     * The From class.
     */
    private Class<T> fromClass;

    /**
     * Instantiates a new Common criteria.
     *
     * @param fromClass the from class
     */
    public CommonCriteria(Class<T> fromClass) {
        this.fromClass = fromClass;
    }

    /**
     * Base class returned by your request
     * <p>
     * this class is setted by the generated code
     * Exemple :
     *
     * <code>A result = criteriaResearchRepository.searchByCriteria(ACriteria)</code>
     * {@link #getFromClass()} on ACriteria return `A.class`
     *
     * @return the base class from your request
     */
    public Class<T> getFromClass() {
        return fromClass;
    }

    /**
     * Return a map with all setted value in your criteria
     * this method is used for creating the request
     *
     * @return criteria map
     */
    public abstract Map<TypeCriteria, Map<String, ?>> getCriteriaMap();

    /**
     * The enum Type criteria.
     */
    public enum TypeCriteria {
        /**
         * Date type criteria.
         */
        DATE,
        /**
         * Primitif type criteria.
         */
        PRIMITIF,
        /**
         * Subentity type criteria.
         */
        SUB_ENTITY,
        /**
         * Subentity type criteria.
         */
        X_TO_MANY_SUBENTITY
    }
}
