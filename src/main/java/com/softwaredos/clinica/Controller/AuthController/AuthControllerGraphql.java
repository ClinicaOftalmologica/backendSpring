
package com.softwaredos.clinica.Controller.AuthController;

import graphql.kickstart.tools.GraphQLMutationResolver;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softwaredos.clinica.Request.LoginRequest;
import com.softwaredos.clinica.Request.RegisterRequest;
import com.softwaredos.clinica.Response.AuthResponse;

@RestController
@RequiredArgsConstructor
public class AuthControllerGraphql implements GraphQLMutationResolver {

    @Autowired
    private final AuthService authService;

    @MutationMapping
    public AuthResponse login(@Argument LoginRequest request) {
        return authService.login(request);
    }

    @MutationMapping
    public AuthResponse register(@Argument RegisterRequest request) {

        return authService.register(request);
    }

}