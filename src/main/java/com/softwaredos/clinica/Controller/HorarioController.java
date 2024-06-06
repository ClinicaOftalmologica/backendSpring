package com.softwaredos.clinica.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.web.bind.annotation.RestController;

import com.softwaredos.clinica.Model.Available_time;
import com.softwaredos.clinica.Repository.HorarioRepository;

import com.softwaredos.clinica.Request.HorarioRequest;
import com.softwaredos.clinica.Response.HorarioResponse;
import com.softwaredos.clinica.config.Auth;

import jakarta.persistence.EntityNotFoundException;

@RestController
@Secured("ROLE_DOCTOR")
public class HorarioController {
    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private Auth auth;

    // Esta funcion lista los Horarios de un doctor
    @QueryMapping
    public HorarioResponse miHorario() {
        return HorarioResponse.builder()
                .status(200)
                .message("Exito: La solicitud se a completa correctamente")
                .data(horarioRepository.findByDoctor(auth.persona()))
                .build();
    }

    @MutationMapping
    public Available_time storeHorario(@Argument HorarioRequest request) {
        LocalDate localDate = LocalDate.parse(request.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalTime localTime = LocalTime.parse(request.getTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));

        LocalDateTime localDateTime = localDate.atTime(localTime);
        Date dateTime = java.sql.Timestamp.valueOf(localDateTime);

        Available_time available_time = Available_time.builder()
                .date(dateTime)
                .time(localTime)
                .doctor(auth.persona())
                .build();
        return horarioRepository.save(available_time);
    }

    @MutationMapping
    public Available_time updateHorario(@Argument HorarioRequest request) {
        Optional<Available_time> optionalAvailableTime = horarioRepository.findById(request.getId());
        if (optionalAvailableTime.isPresent()) {
            Available_time availableTime = optionalAvailableTime.get();

            LocalDate localDate = LocalDate.parse(request.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalTime localTime = LocalTime.parse(request.getTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));

            availableTime.setDate(java.sql.Date.valueOf(localDate));
            availableTime.setTime(localTime);

            return horarioRepository.save(availableTime);
        } else {
            throw new EntityNotFoundException("Available_time not found with ID: " + request.getId());
        }
    }

    @MutationMapping
    public Boolean deleteHorario(@Argument String id) {
        if (horarioRepository.existsById(id)) {
            horarioRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
