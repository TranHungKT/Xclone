package com.xclone.userservice.repository.db.dao;

import java.util.Collection;

public interface PersistAllRepository<T> {
    <S extends T> Collection<S> persistAll(Collection<S> entities);
    <S extends T> S persist(S entity);
}
