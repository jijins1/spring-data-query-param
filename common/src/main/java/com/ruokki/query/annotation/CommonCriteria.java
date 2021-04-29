package com.ruokki.query.annotation;

import java.util.Map;

public abstract class CommonCriteria<T> {
    private Class<T> fromClass;

    public CommonCriteria(Class<T> fromClass) {
        this.fromClass = fromClass;
    }

    /**
     * Base class returned by your request
     * <p>
     * Exemple :
     *
     * <code>List result = criteriaResearchRepository.searchByCriteria(ACriteria)</code>
     * {@link #getFromClass()} on ACriteria return `A.class`
     * this class is setted by the generated code
     * @return the base class from your request
     */
    public Class<T> getFromClass() {
        return fromClass;
    }

    /**
     * Return a map with all setted value in your criteria
     * this method is used for creating the request
     * @return
     */
    public abstract Map<TypeCriteria, Map<String, ?>> getCriteriaMap();

    public enum TypeCriteria {
        DATE,
        PRIMITIF
    }
}
