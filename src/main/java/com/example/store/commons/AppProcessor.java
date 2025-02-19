package com.example.store.commons;

import java.util.List;

public interface AppProcessor<R, T> {
    R create(T t);

    R findById(long id);

    List<R> findAll();
}
