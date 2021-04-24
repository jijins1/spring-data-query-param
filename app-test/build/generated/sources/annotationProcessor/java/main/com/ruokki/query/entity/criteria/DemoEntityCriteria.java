package com.ruokki.query.entity.criteria;

import com.ruokki.query.annotation.CommonCriteria;
import com.ruokki.query.entity.DemoEntity;
import java.util.List;
import com.google.common.collect.Lists;
import java.util.Date;

public class DemoEntityCriteria extends CommonCriteria<DemoEntity> {

    public DemoEntityCriteria() {
        super(DemoEntity.class);
    }

    private List<Long> ids;

    public List<Long> getIds() {
        return this.ids;
    }

    public DemoEntityCriteria setIds(List<Long> ids) {
        this.ids = ids;
        return this;
    }

    public DemoEntityCriteria setIds(Long id[]) {
        this.ids =  Lists.newArrayList(id);
        return this;
    }

    public DemoEntityCriteria setId(Long id) {
        this.ids =  Lists.newArrayList(id);
        return this;
    }

    private List<String> stringAs;

    public List<String> getStringAs() {
        return this.stringAs;
    }

    public DemoEntityCriteria setStringAs(List<String> stringAs) {
        this.stringAs = stringAs;
        return this;
    }

    public DemoEntityCriteria setStringAs(String stringA[]) {
        this.stringAs =  Lists.newArrayList(stringA);
        return this;
    }

    public DemoEntityCriteria setStringA(String stringA) {
        this.stringAs =  Lists.newArrayList(stringA);
        return this;
    }

    private Date dateStart;

    private Date dateEnd;

    public DemoEntityCriteria setDateStart(Date dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public DemoEntityCriteria setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public Date getDateStart() {
        return this.dateStart;
    }

    public Date getDateEnd() {
        return this.dateEnd;
    }
}

