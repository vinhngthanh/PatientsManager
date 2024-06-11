package com.vnguy.crud_demo.service;

import com.baeldung.openapi.model.JwtResponse;
import com.baeldung.openapi.model.SignInRequest;
import com.baeldung.openapi.model.SignUpRequest;

public interface AuthService {
    JwtResponse signIn(SignInRequest signInRequest);
    void signUp(SignUpRequest signUpRequest);
    void logout();
}
