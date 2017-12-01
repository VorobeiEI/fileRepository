package com.fileRepository.fileRepository.service;

import com.fileRepository.fileRepository.entity.User;

public interface UserService extends CRUDService<User> {
    void save(User user);

    User findByUsername(String username);
}
