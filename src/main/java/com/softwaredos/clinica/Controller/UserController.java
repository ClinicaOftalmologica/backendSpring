package com.softwaredos.clinica.Controller;

import java.util.Arrays;
import java.util.List;

import com.softwaredos.clinica.Model.Image;
import com.softwaredos.clinica.Model.Person;
import com.softwaredos.clinica.Repository.ImageRepository;
import com.softwaredos.clinica.Repository.PersonRepository;
import com.softwaredos.clinica.Request.RegisterRequest;
import com.softwaredos.clinica.config.Auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userrepo;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private Auth auth;

    @Secured("ROLE_PACIENTE")
    @QueryMapping
    public List<Person> listarUsuario() {
        // List<Role> roles = Arrays.asList(Role.DOCTOR, Role.ADMIN); // Añade los roles
        // que necesites
        // return userrepo.findByRoleIn(roles);
        return personRepository.findBytipoUserNot((short) 1);

    }

    @Secured({ "ROLE_DOCTOR", "ROLE_ADMIN" })
    @QueryMapping
    public User findUser(@Argument String username) {
        LOGGER.info("esta filtrando por su username");
        Optional<User> userOptional = userrepo.findByUsername(username);
        return userOptional.orElse(null);
    }

    @MutationMapping
    public String storeDoctor(@Argument RegisterRequest request) {
        User user = User.builder().email(request.getEmail())
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.DOCTOR).build();
        userrepo.save(user);

        Person person = Person.builder()
                .name(request.getName())
                .lastName(request.getLast_name())
                .address(request.getAddress())
                .ci(request.getCi())
                .sexo(request.getSexo().charAt(0))
                .tipoUser((short) 2)
                .contactNumber(request.getContact_number())
                .titulo(request.getTitulo())
                .user(user)
                .build();

        personRepository.save(person);
        return "Doctor Creado Con Exito";
    }

    @MutationMapping
    public String deleteDoctor(@Argument String id) {
        if (personRepository.existsById(id)) {
            Person person = personRepository.findById(id).orElse(null);
            try {
                personRepository.deleteById(id);
                userrepo.deleteById(person.getUser().getId());
            } catch (Exception e) {
                return "Error en Catch : " + e.getMessage();
            }

            return "Se elimino Con Exito : ";
        } else {
            return "Error al eliminar el usuario";
        }
    }

    @MutationMapping
    public String updateDoctor(@Argument String id, @Argument RegisterRequest request) {
        Person person = personRepository.findById(id).orElse(null);
        User user = person.getUser();
        // return user.getPassword();
        if (person != null && user != null) {
            user.setEmail(request.getEmail());
            user.setUsername(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userrepo.save(user);
            person.setCi(request.getCi());
            person.setTitulo(request.getTitulo());
            person.setLastName(request.getLast_name());
            person.setAddress(request.getAddress());
            // person.setSexo(request.getSexo().charAt(0));
            person.setName(request.getName());
            person.setUser(user);
            personRepository.save(person);
            return "Doctor acutalizado Con Exito";
        }
        return "No se pudieron actualizar los datos";
    }

    @QueryMapping
    public Person showDoctor(@Argument String id) {
        return personRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Image> miImage() {
        return imageRepository.findByUser(auth.user());
    }

}
