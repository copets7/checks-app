package com.yarosh.library.repository.spring.data;

import com.yarosh.library.repository.api.entity.CheckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckSpringDataRepository extends JpaRepository<CheckEntity, Long> { }
