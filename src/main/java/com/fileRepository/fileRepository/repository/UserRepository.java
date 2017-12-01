package com.fileRepository.fileRepository.repository;

import com.fileRepository.fileRepository.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
