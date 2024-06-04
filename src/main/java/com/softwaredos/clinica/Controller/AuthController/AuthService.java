package com.softwaredos.clinica.Controller.AuthController;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.softwaredos.clinica.Model.Image;
import com.softwaredos.clinica.Model.Person;
import com.softwaredos.clinica.Model.User;
import com.softwaredos.clinica.Model.User.Role;
import com.softwaredos.clinica.Repository.ImageRepository;
import com.softwaredos.clinica.Repository.PersonRepository;
import com.softwaredos.clinica.Repository.UserRepository;
import com.softwaredos.clinica.Request.LoginRequest;
import com.softwaredos.clinica.Request.RegisterRequest;
import com.softwaredos.clinica.utils.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final UserRepository userRepository;
        private final PersonRepository personRepository;
        private final ImageRepository imageRepository;

        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;

        public AuthResponse login(LoginRequest request) {
                authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                                                request.getPassword()));
                UserDetails user = userRepository.findByUsername(request.getEmail()).orElseThrow();
                String token = jwtService.getToken(user);
                return AuthResponse.builder()
                                .token(token)
                                .build();
        }

        public AuthResponse register(RegisterRequest request) {
                User user = User.builder()
                                .username(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .email(request.getEmail())
                                .role(Role.PACIENTE)
                                .build();

                LocalDate localDate = LocalDate.parse(request.getBirth_date(),
                                DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                Date date = java.sql.Date.valueOf(localDate);

                Person person = Person.builder()
                                .name(request.getName())
                                .lastName(request.getLast_name())
                                .address(request.getAddress())
                                .ci(request.getCi())
                                .sexo(request.getSexo().charAt(0))
                                .tipoUser((short) 1)
                                .contactNumber(request.getContact_number())
                                .birthDate(date)
                                .user(user)
                                .build();
                Image imagen = Image.builder()
                                .url(request.getUrl())
                                .user(user).build();

                userRepository.save(user);
                personRepository.save(person);
                imageRepository.save(imagen);

                return AuthResponse.builder()
                                .token(jwtService.getToken(user))
                                .build();
        }

}
