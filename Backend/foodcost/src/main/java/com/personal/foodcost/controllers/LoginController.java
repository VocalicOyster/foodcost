package com.personal.foodcost.controllers;

import com.personal.foodcost.models.DTOs.*;
import com.personal.foodcost.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller("/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailService myUserDetailService;

    @PostMapping("/login")
    public ResponseEntity<Response> authAndGetToken(@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));

        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(
                    new ResponseValid(
                            200,
                            "Authenticated!",
                            "Token: " + jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()))
                    )
            );
        }
        return ResponseEntity.status(404).body(
                new ResponseInvalid(
                        404,
                        "No user found with this username"
                )
        );
    }
}
