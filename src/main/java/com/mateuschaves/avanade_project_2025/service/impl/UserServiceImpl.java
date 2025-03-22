package com.mateuschaves.avanade_project_2025.service.impl;

import com.mateuschaves.avanade_project_2025.domain.model.User;
import com.mateuschaves.avanade_project_2025.repository.UserRepository;
import com.mateuschaves.avanade_project_2025.service.UserService;
import com.mateuschaves.avanade_project_2025.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));
    }

    @Transactional
    public User create(User userToCreate) {
        if (userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber())){
            throw new IllegalArgumentException("This Account number already exists.");
        }
        return userRepository.save(userToCreate);
    }

    @Transactional
    public User update(Long id, User updatedUser) {
        User userUpdated = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));

        userUpdated.setName(updatedUser.getName());
        userUpdated.setAccount(updatedUser.getAccount());
        userUpdated.setCard(updatedUser.getCard());
        userUpdated.setFeatures(updatedUser.getFeatures());
        userUpdated.setNews(updatedUser.getNews());

        return userRepository.save(userUpdated);
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }



}
