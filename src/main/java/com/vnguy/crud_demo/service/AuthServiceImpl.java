package com.vnguy.crud_demo.service;

import com.baeldung.openapi.model.JwtResponse;
import com.baeldung.openapi.model.SignInRequest;
import com.baeldung.openapi.model.SignUpRequest;
import com.vnguy.crud_demo.model.User;
import com.vnguy.crud_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vnguy.crud_demo.model.User.UserRole;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public JwtResponse signIn(SignInRequest signInRequest) {
        return null;
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

    }
}
