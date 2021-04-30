package com.ruokki.query.repository;


import com.ruokki.query.entity.DemoSubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoSubEntityJpaRepository extends JpaRepository<DemoSubEntity, Long> {
}
