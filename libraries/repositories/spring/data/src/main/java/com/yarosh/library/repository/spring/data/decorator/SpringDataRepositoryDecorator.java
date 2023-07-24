package com.yarosh.library.repository.spring.data.decorator;

import com.yarosh.library.repository.api.CrudRepository;
import com.yarosh.library.repository.api.entity.BaseEntity;
import com.yarosh.library.repository.api.pagination.RepositoryPage;
import com.yarosh.library.repository.api.pagination.RepositoryPageRequest;
import com.yarosh.library.repository.api.pagination.RepositorySortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public RepositoryPage<E> selectAll(RepositoryPageRequest request) {
        final Page<E> page = jpaRepository.findAll(
                PageRequest.of(request.pageNumber(), request.size(), convertToSpringSort(request.sortBy()))
        );

        return convertToDatabasePage(page);
    }

    @Override
    public E update(E entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public void delete(ID id) {
        jpaRepository.deleteById(id);
    }

    private Sort convertToSpringSort(RepositorySortBy sortBy) {
        final Sort sort = Sort.by(sortBy.column());
        return sortBy.isDesc() ? sort.descending() : sort;
    }

    private RepositoryPage<E> convertToDatabasePage(Page<E> page) {
        return new RepositoryPage<>(page.getContent(), page.getSize());
    }
}
