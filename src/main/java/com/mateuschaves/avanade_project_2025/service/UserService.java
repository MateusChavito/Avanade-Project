package com.mateuschaves.avanade_project_2025.service;

import com.mateuschaves.avanade_project_2025.domain.model.User;

public interface UserService {

    User findById(Long id);

    User create(User userToCreate);

}
