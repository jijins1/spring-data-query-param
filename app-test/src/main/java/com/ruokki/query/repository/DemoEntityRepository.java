package com.ruokki.query.repository;

import com.ruokki.query.entity.DemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;


public interface DemoEntityRepository extends Repository<DemoEntity, Long> {
}
