package ovh.ruokki.query.repository;


import ovh.ruokki.query.entity.DemoSubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoSubEntityJpaRepository extends JpaRepository<DemoSubEntity, Long> {
}
