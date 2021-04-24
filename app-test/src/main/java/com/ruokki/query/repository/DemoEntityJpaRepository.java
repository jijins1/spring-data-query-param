package com.ruokki.query.repository;

import com.ruokki.query.entity.DemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;


public interface DemoEntityJpaRepository extends CrudRepository<DemoEntity, Long> {
}
