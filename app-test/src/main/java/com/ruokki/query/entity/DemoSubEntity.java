package com.ruokki.query.entity;

import com.ruokki.query.annotation.Criteria;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class DemoSubEntity {
    @Id
    private Long id;

}
