package com.xclone.userservice.repository.db.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class PersistAllRepositoryImpl<T> implements PersistAllRepository<T> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <S extends T> Collection<S> persistAll(Collection<S> entities) {
        for (S entity : entities) {
            entityManager.persist(entity);
        }
        return entities;
    }

    @Override
    public <S extends T> S persist(S entity) {
        entityManager.persist(entity);
        return entity;
    }
}
