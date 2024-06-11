package com.vnguy.crud_demo.rest;

import com.baeldung.openapi.api.AuthApiDelegate;
import com.baeldung.openapi.model.JwtResponse;
import com.baeldung.openapi.model.SignInRequest;
import com.baeldung.openapi.model.SignUpRequest;
import com.vnguy.crud_demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthApiDelegateImpl implements AuthApiDelegate {
    @Autowired
    private final AuthService authService;

    @Override
    public ResponseEntity<Void> logout(){
        authService.logout();
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<JwtResponse> signIn(SignInRequest signInRequest){
        JwtResponse jwtResponse = authService.signIn(signInRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @Override
    public ResponseEntity<Void> signUp(SignUpRequest signUpRequest){
        authService.signUp(signUpRequest);
        return ResponseEntity.ok().build();
    }
}
