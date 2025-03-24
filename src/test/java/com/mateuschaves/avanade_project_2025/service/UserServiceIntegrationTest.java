package com.mateuschaves.avanade_project_2025.service;

import com.mateuschaves.avanade_project_2025.domain.model.Account;
import com.mateuschaves.avanade_project_2025.domain.model.User;
import com.mateuschaves.avanade_project_2025.repository.UserRepository;
import com.mateuschaves.avanade_project_2025.service.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Nested
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void cleanUp() {
        userRepository.deleteAll();
    }


    @Test
    void findUserById_WhenUserExists_ShouldReturnUser() {
        Account account = new Account();
        account.setNumber("12345");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setAccount(account);
        userRepository.save(existingUser);

        User foundUser = userService.findById(existingUser.getId());
        assertNotNull(foundUser);
        assertEquals(existingUser.getId(), foundUser.getId());
        assertEquals(account.getNumber(), foundUser.getAccount().getNumber());
    }


    @Test
    void findUserById_WhenUserDoesNotExist_ShouldThrowException() {
        Long id = 999L;
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.findById(id));

        assertTrue(exception.getMessage().contains("User with ID " + id + " not found"));
    }


    @Test
    void createUser_WhenAccountExists_ShouldThrowException() {
        Account account = new Account();
        account.setNumber("12345");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setAccount(account);
        userRepository.save(existingUser);

        User newUser = new User();
        newUser.setId(2L);
        newUser.setAccount(account);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.create(newUser));
        assertEquals("This Account number already exists.", exception.getMessage());
    }

    @Test
    void createUser_WhenAccountDoesNotExist_ShouldCreateUser() {
        Account account = new Account();
        account.setNumber("67890");

        User newUser = new User();
        newUser.setId(2L);
        newUser.setAccount(account);

        User savedUser = userService.create(newUser);
        assertNotNull(savedUser.getId());
        assertEquals(account.getNumber(), savedUser.getAccount().getNumber());
    }

    @Test
    void updateUser_WhenUserExists_ShouldUpdateUser() {
        Account account = new Account();
        account.setNumber("12345");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Original Name");
        existingUser.setAccount(account);

        userRepository.save(existingUser);

        User updatedUser = new User();
        updatedUser.setName("Updated Name");
        updatedUser.setAccount(account);
        updatedUser.setCard(existingUser.getCard());
        updatedUser.setFeatures(existingUser.getFeatures());
        updatedUser.setNews(existingUser.getNews());

        User result = userService.update(existingUser.getId(), updatedUser);

        assertEquals("Updated Name", result.getName());
        assertEquals(account.getNumber(), result.getAccount().getNumber());
    }


    @Test
    void updateUser_WhenUserDoesNotExist_ShouldThrowNotFoundException() {
        User nonExistentUser = new User();
        nonExistentUser.setId(999L);

        User updatedUser = new User();
        updatedUser.setName("Updated Name");

        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.update(nonExistentUser.getId(), updatedUser));
        assertEquals("User with ID 999 not found", exception.getMessage());
    }


    @Test
    void deleteUser_WhenUserExists_ShouldDeleteUser() {
        Account account = new Account();
        account.setNumber("12345");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setAccount(account);
        userRepository.save(existingUser);

        userService.delete(existingUser.getId());

        Optional<User> deletedUser = userRepository.findById(existingUser.getId());
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void deleteUser_WhenUserDoesNotExist_ShouldThrowException() {
        Long id = 999L;
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.delete(id));
        assertTrue(exception.getMessage().contains("User with ID " + id + " not found"));
    }

}


