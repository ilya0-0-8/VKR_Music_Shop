package com.Shop.Music.services;

import com.Shop.Music.dto.auth.JwtAuthenticationResponse;
import com.Shop.Music.dto.auth.SignInRequest;
import com.Shop.Music.dto.auth.SignUpRequest;
import com.Shop.Music.models.User;
import com.Shop.Music.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IsValidAuthService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public ResponseEntity<?> isValidLogin(SignInRequest signInRequest)
    {
        User existingUser = userRepository.findByNameIsValidAuth(signInRequest.getUsername());

        if(existingUser != null)
        {
            if(existingUser.isActive())
            {
                JwtAuthenticationResponse response = authenticationService.signIn(signInRequest);
                return ResponseEntity.ok(response);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.LOCKED);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    public ResponseEntity<?> isValidRegister(SignUpRequest signUpRequest)
    {
        if(!userRepository.existsByUsername(signUpRequest.getUsername()))
        {
            if(!userRepository.existsByEmail(signUpRequest.getEmail()))
            {
                authenticationService.signUp(signUpRequest);
                return ResponseEntity.ok("Регистрация прошла успешно!");
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
