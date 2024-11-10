package com.cwm.ecom.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cwm.ecom.dao.RoleDao;
import com.cwm.ecom.model.Role;
import com.cwm.ecom.model.User;
import com.cwm.ecom.model.UserRole;
import com.cwm.ecom.service.impl.UserServiceImpl;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private RoleDao roleDao;

    private User user;
    private UserRole userRole;
    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("testUser");
        user.setPassword("plainPassword");

        role = new Role();
        role.setName("USER");

        userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
    }

//    @Test
//    void testSaveUser() throws Exception {
//        // Mock the password encoder
//        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
//        
//        // Mock the roleDao
//        when(roleDao.findByName("USER")).thenReturn(role);
//        
//        System.out.println(user.getUserRoles());
//        // Mock the userService
//        when(userService.saveUser(any(User.class), anySet())).thenReturn(user);
//        user.setPassword("encodedPassword");
//        User savedUser = userController.saveUser(user);
//        assertEquals("encodedPassword", savedUser.getPassword());
//        assertEquals("testUser", savedUser.getUsername());
//        verify(userService).saveUser(any(User.class), anySet());
//        verify(passwordEncoder).encode(user.getPassword());
//    }

    @Test
    void testGetSingleUser() {
        // Mock the userService
        when(userService.getSingleUser(1L)).thenReturn(user);

        User foundUser = userController.getSingleUser(1L);

        assertEquals(user, foundUser);
        verify(userService).getSingleUser(1L);
    }

  

    @Test
    void testGetByUsername() {
        when(userService.findByUsername("testUser")).thenReturn(Optional.of(user));

        User foundUser = userController.getByUsername("testUser");

        assertEquals(user, foundUser);
        verify(userService).findByUsername("testUser");
    }

    @Test
    void testGetByUsername_UserNotFound() {
        when(userService.findByUsername("unknownUser")).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            userController.getByUsername("unknownUser");
        });

        assertEquals("No value present", exception.getMessage());
    }
}
