package com.cwm.ecom.controller;

import com.cwm.ecom.entity.UserRequest;
import com.cwm.ecom.entity.UserResponse;
import com.cwm.ecom.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRequest = new UserRequest();
        userRequest.setUsername("testUser");
        userRequest.setPassword("testPassword");
    }

    @Test
    void testLoginSuccess() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtils.gernerateToken(userRequest.getUsername())).thenReturn("mockToken");
        UserResponse response = authController.login(userRequest);

      
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Success", response.getMessage());
        assertEquals("mockToken", response.getToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).gernerateToken(userRequest.getUsername());
    }
}
