package com.softwaredos.clinica.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softwaredos.clinica.Model.User;
import com.softwaredos.clinica.Model.User.Role;
import com.softwaredos.clinica.Repository.UserRepository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.security.access.annotation.Secured;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userrepo;

    @Secured("ROLE_PACIENTE")
    @QueryMapping
    public List<User> listarUsuario() {
        return userrepo.findByRole(Role.PACIENTE);
    }

    @Secured({ "ROLE_DOCTOR", "ROLE_ADMIN" })
    @QueryMapping
    public User findUser(@Argument String username) {
        LOGGER.info("esta filtrando por su username");
        Optional<User> userOptional = userrepo.findByUsername(username);
        return userOptional.orElse(null);
    }

    @QueryMapping
    public boolean user_store(@Argument Userreeques username) {
        LOGGER.info("esta filtrando por su username");
        Optional<User> userOptional = userrepo.findByUsername(username);
        return userOptional.orElse(null);
    }

}
