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
     * <code>List<A> result = criteriaResearchRepository.searchByCriteria(ACriteria)</code>
     * {@link #getFromClass()} on ACriteria return `A.class`
     *
     * @return the base class from your request
     * @implNote this class is setted by the generated code
     */
    public Class<T> getFromClass() {
        return fromClass;
    }

    /**
     * Return a map with all setted value in your criteria
     * @return
     * @implNote this method is used for creating the request
     */
    public abstract Map<TypeCriteria, Map<String, ?>> getCriteriaMap();

    public enum TypeCriteria {
        DATE,
        PRIMITIF
    }
}
