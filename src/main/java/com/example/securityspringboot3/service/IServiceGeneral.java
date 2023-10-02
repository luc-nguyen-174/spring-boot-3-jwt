package com.example.securityspringboot3.service;

import java.util.Optional;

public interface IServiceGeneral<T> {
    Iterable<T> findAll();

    Optional<T> findById(Long id);

    T save(T t);

    void remove(Long id);
}
