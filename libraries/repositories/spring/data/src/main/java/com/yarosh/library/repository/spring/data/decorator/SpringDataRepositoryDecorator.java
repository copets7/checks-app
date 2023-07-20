package com.yarosh.library.repository.spring.data.decorator;

import com.yarosh.library.repository.api.CrudRepository;
import com.yarosh.library.repository.api.entity.BaseEntity;
import com.yarosh.library.repository.api.pagination.DatabasePage;
import com.yarosh.library.repository.api.pagination.DatabasePageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class SpringDataRepositoryDecorator<E extends BaseEntity, ID> implements CrudRepository<E, ID> {

    private final JpaRepository<E, ID> jpaRepository;

    public SpringDataRepositoryDecorator(JpaRepository<E, ID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public E insert(E entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public Optional<E> select(ID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<E> selectAll() {
        return jpaRepository.findAll();
    }

    @Override
    public DatabasePage<E> findAllWithPagination(DatabasePageRequest request) {
        return (DatabasePage<E>) jpaRepository.findAll();
    }

    @Override
    public E update(E entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public void delete(ID id) {
        jpaRepository.deleteById(id);
    }
}
