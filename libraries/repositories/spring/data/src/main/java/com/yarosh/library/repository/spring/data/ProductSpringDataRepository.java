package com.yarosh.library.repository.spring.data;

import com.yarosh.library.repository.api.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSpringDataRepository extends JpaRepository<ProductEntity, Long> { }
