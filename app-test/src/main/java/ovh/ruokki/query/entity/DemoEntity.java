package ovh.ruokki.query.entity;

import ovh.ruokki.query.annotation.Criteria;
import ovh.ruokki.query.annotation.SubCriteria;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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


    @SubCriteria
    @ManyToMany()
    @JoinTable(name="DEMO_DEMO_SUB")
    private List<DemoSubEntity> demoSubEntityList;

    public String otherMethod() {
        return "";
    }
}
