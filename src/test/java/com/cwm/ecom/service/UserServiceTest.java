//package com.cwm.ecom.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.cwm.ecom.dao.RoleDao;
//import com.cwm.ecom.dao.UserDao;
//import com.cwm.ecom.model.Role; // Import your Role class
//import com.cwm.ecom.model.User;
//import com.cwm.ecom.model.UserRole;
//import com.cwm.ecom.service.impl.UserServiceImpl;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @Mock
//    private UserDao userDao;
//
//    @Mock
//    private RoleDao roleDao;
//
//    private User user;
//    private UserRole userRole;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        user = new User();
//        user.setUsername("testUser");
//        user.setPassword("password");
//        userRole = new UserRole();
//        Role role=new Role();
//        role.setName("ROLE_USER");
//        userRole.setRole(role); // Ensure the Roler class is correctly referenced
//    }
//
//    @Test
//    void testSaveUser_UserAlreadyExists() {
//        when(userDao.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
//
//        Exception exception = assertThrows(Exception.class, () -> {
//            userService.saveUser(user, new HashSet<>(Collections.singletonList(userRole)));
//        });
//
//        assertEquals("User already Exist!", exception.getMessage());
//    }
//
//    @Test
//    void testSaveUser_Success() throws Exception {
//        when(userDao.findByUsername(user.getUsername())).thenReturn(Optional.empty());
//        when(userDao.save(any(User.class))).thenReturn(user);
//        
//        User savedUser = userService.saveUser(user, new HashSet<>(Collections.singletonList(userRole)));
//
//        assertEquals(user.getUsername(), savedUser.getUsername());
//        verify(roleDao).save(userRole.getRole());
//        verify(userDao).save(user);
//    }
//
//    @Test
//    void testFindAllUsers() {
//        when(userDao.findAll()).thenReturn(Collections.singletonList(user));
//        
//        List<User> users = userService.findAllUsers();
//
//        assertEquals(1, users.size());
//        assertEquals(user, users.get(0));
//    }
//
//    @Test
//    void testGetSingleUser() {
//        when(userDao.findById(1L)).thenReturn(Optional.of(user));
//        
//        User foundUser = userService.getSingleUser(1L);
//
//        assertEquals(user, foundUser);
//    }
//
//    @Test
//    void testFindByUsername() {
//        when(userDao.findByUsername("testUser")).thenReturn(Optional.of(user));
//        
//        Optional<User> foundUser = userService.findByUsername("testUser");
//
//        assertTrue(foundUser.isPresent());
//        assertEquals(user, foundUser.get());
//    }
//
//    @Test
//    void testLoadUserByUsername_UserFound() {
//        when(userDao.findByUsername("testUser")).thenReturn(Optional.of(user));
//        
//        UserDetails userDetails = userService.loadUserByUsername("testUser");
//
//        assertEquals("testUser", userDetails.getUsername());
//        assertEquals(user.getPassword(), userDetails.getPassword());
//        assertFalse(userDetails.getAuthorities().isEmpty());
//    }
//
//    @Test
//    void testLoadUserByUsername_UserNotFound() {
//        when(userDao.findByUsername("unknownUser")).thenReturn(Optional.empty());
//        
//        assertThrows(UsernameNotFoundException.class, () -> {
//            userService.loadUserByUsername("unknownUser");
//        });
//    }
//}
