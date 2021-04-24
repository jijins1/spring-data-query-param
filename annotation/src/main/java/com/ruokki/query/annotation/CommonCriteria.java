package com.ruokki.query.annotation;

import java.util.Map;

public abstract class CommonCriteria<T> {
    private Class<T> fromClass;

    public CommonCriteria(Class<T> fromClass) {
        this.fromClass = fromClass;
    }

    public Class<T> getFromClass() {
        return fromClass;
    }

    public abstract Map<TypeCriteria, Map<String, ?>> getCriteriaMap();

    public enum TypeCriteria {
        DATE,
        PRIMITIF
    }
}
