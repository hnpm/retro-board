package com.example.retroboard.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.retroboard.entity.User;
import com.example.retroboard.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void init() {
        userService = new UserService(userRepository);
    }

    @Test
    public void getAllCommentsForToday_HappyPath_ShouldReturn1Comment() {
        // Given
        User user = new User();
        user.setUsername("hien");
        user.setPassword("password");
        user.setRole("USER");
        when(userRepository.findByUsername("hien")).thenReturn(user);

        // When
        userService.loadUserByUsername("hien");

        // Then
        verify(userRepository, times(1)).findByUsername("hien");
    }
}
