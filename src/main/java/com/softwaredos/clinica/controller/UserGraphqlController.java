
package com.softwaredos.clinica.controller;


import com.softwaredos.clinica.Model.User;
import com.softwaredos.clinica.repo.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserGraphqlController {
    @Autowired

    private UserRepository userrepo;

    @QueryMapping
    public List<User> listarUsuario() {
        return userrepo.findAll();
    }
    
    
//    @MutationMapping(name = "register")
//    public User register(@Argument User user) {
//        return userrepo.save(user);
//    }
    
}