
package com.softwaredos.clinica.controller;

import org.springframework.web.bind.annotation.RestController;

import com.softwaredos.clinica.Model.User;
import com.softwaredos.clinica.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {

    @Autowired

    UserRepository repo;


    @PostMapping("/addUsers")
    public void addUsers(@RequestBody User user){
        repo.save(user);
    }
    
}
