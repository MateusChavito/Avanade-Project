package com.mateuschaves.avanade_project_2025.service;

import com.mateuschaves.avanade_project_2025.domain.model.Account;
import com.mateuschaves.avanade_project_2025.domain.model.User;
import com.mateuschaves.avanade_project_2025.repository.UserRepository;
import com.mateuschaves.avanade_project_2025.service.exception.NotFoundException;
import com.mateuschaves.avanade_project_2025.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();

        Account account = new Account();
        account.setNumber("123456");

        user = new User();
        user.setId(1L);
        user.setName("Mateus Chaves");
        user.setAccount(account);
    }

    @Test
    void findById_UserExists_ReturnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Mateus Chaves", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findById_UserNotFound_ThrowsNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void createUser_AccountExists_ThrowsException() {
        Account account = new Account();
        account.setNumber("12345");

        User newUser = new User();
        newUser.setId(2L);
        newUser.setAccount(account);

        when(userRepository.existsByAccountNumber("12345")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.create(newUser));
        assertEquals("This Account number already exists.", exception.getMessage());

        verify(userRepository, times(1)).existsByAccountNumber("12345");
    }


    @Test
    void deleteUser_UserExists_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.delete(1L));

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_UserNotFound_ThrowsException() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.delete(1L));

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, never()).deleteById(1L);
    }
}
