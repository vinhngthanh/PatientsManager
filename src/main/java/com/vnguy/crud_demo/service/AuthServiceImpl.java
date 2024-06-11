package com.vnguy.crud_demo.service;

import com.baeldung.openapi.model.JwtResponse;
import com.baeldung.openapi.model.SignInRequest;
import com.baeldung.openapi.model.SignUpRequest;
import com.vnguy.crud_demo.model.User;
import com.vnguy.crud_demo.repository.UserRepository;
import com.vnguy.crud_demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vnguy.crud_demo.model.User.UserRole;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public JwtResponse signIn(SignInRequest signInRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String userRole = ((User) authentication.getPrincipal()).getRole().getValue();
        String jwt = jwtUtil.generateToken(signInRequest.getUsername());
        JwtResponse response = new JwtResponse();
        response.setAccessToken(jwt);
        response.setRole(userRole);
        return response;
    }

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        UserRole role;
        try {
            role = UserRole.valueOf(signUpRequest.getRole().getValue());
        } catch (IllegalArgumentException ex) {
            role = UserRole.USER;
        }

        userRepository.save(new User(
                signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                role));
    }
    
    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
