package com.ruokki.query.entity;

import com.ruokki.query.annotation.Criteria;
import com.ruokki.query.annotation.SubCriteria;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Setter
@Getter
@Entity
@Criteria
public class DemoEntity {

    @Id
    private Long id;

    private String stringA;

    private Date date;

    @SubCriteria
    @OneToOne
    @JoinColumn(name = "demo_sub_entity_id")
    private DemoSubEntity demoSubEntity;


    public String otherMethod() {
        return "";
    }
}
