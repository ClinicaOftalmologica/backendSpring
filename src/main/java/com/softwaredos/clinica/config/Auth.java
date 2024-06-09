package com.softwaredos.clinica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.softwaredos.clinica.Model.Person;
import com.softwaredos.clinica.Model.User;
import com.softwaredos.clinica.Repository.PersonRepository;
import com.softwaredos.clinica.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Auth {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserRepository userRepository;

    public Person persona() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        return personRepository.findByUser_id(user.getId());
    }

    public User user() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        return user;
    }
}