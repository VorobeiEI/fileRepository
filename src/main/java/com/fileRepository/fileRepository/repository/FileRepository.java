package com.fileRepository.fileRepository.repository;

import com.fileRepository.fileRepository.entity.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {
}
