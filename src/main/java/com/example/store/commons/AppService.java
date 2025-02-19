package com.example.store.commons;

import java.util.List;
import java.util.Optional;

public interface AppService<T> {
    T create(T t);

    Optional<T> findById(long id);

    List<T> findAll();
}
