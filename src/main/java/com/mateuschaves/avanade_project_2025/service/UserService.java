package com.mateuschaves.avanade_project_2025.service;

import com.mateuschaves.avanade_project_2025.domain.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User create(User userToCreate);

    User update(Long id, User updatedUser);

    List<User> findAll();

    void delete(Long id);


}
