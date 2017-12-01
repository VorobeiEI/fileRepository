package com.fileRepository.fileRepository.service;

public interface CRUDService<T> {

    T findOneById(Long id);
}
