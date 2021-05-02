package ovh.ruokki.query.entity;

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
