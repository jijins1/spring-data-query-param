package ovh.ruokki.query.repository;

import ovh.ruokki.query.entity.DemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DemoEntityJpaRepository extends JpaRepository<DemoEntity, Long> {
}
