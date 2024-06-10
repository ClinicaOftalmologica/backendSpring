package com.softwaredos.clinica.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.web.bind.annotation.RestController;

import com.softwaredos.clinica.Model.Available_time;
import com.softwaredos.clinica.Model.Person;
import com.softwaredos.clinica.Model.Reservation;
import com.softwaredos.clinica.Repository.HorarioRepository;
import com.softwaredos.clinica.Repository.ReservationRepository;
import com.softwaredos.clinica.Request.ReservationRequest;
import com.softwaredos.clinica.config.Auth;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

public class ReservationController {

    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private Auth auth;

    // listar de todo los horarios disponible con el nombre del doctor para que se
    // pueda hacer la reserva
    @Secured("ROLE_PACIENTE")
    @QueryMapping
    public List<Available_time> getHorarioDisponible() {
        return horarioRepository.findAvailableTimesAfterDate();
    }

    // Funcion para hacere la reserva id horario
    @Secured("ROLE_PACIENTE")
    @MutationMapping
    public Reservation saveReservation(@Argument ReservationRequest request) {
        try {
            if (horarioRepository.existsById(request.getAvailable_time_id())) {
                Available_time available_time = horarioRepository.findById(request.getAvailable_time_id()).get();
                Reservation reservation = Reservation.builder()
                        .place(request.getPlace())
                        .paciente(auth.persona())
                        .available_time(available_time)
                        .state("PENDIENTE")
                        .build();
                return reservationRepository.save(reservation);
            } else {
                throw new IllegalArgumentException("El horario no existe");
            }
        } catch (Exception e) {
            return null;
        }
    }

    // Funcion para cancelar debe poner una Id valida para proceder a cancelar
    @Secured("ROLE_PACIENTE")
    @MutationMapping
    public Reservation cancelReserva(@Argument String id) {
        try {
            if (reservationRepository.existsById(id)) {
                Reservation reserva = reservationRepository.findById(id).get();
                reserva.setState("CANCELADO");
                return reservationRepository.save(reserva);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Secured("ROLE_DOCTOR")
    @MutationMapping
    public Reservation FinalizarReserva(@Argument String id) {
        try {
            if (reservationRepository.existsById(id)) {
                Reservation reserva = reservationRepository.findById(id).get();
                reserva.setState("FINALIZADA");
                return reservationRepository.save(reserva);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // Funcion para ver mis reservas Pendiente
    @Secured("ROLE_PACIENTE")
    @QueryMapping
    public List<Reservation> getReservaPendiente() {
        return reservationRepository.findByStateAndPaciente("PENDIENTE", auth.persona());
    }

    /*
     * Como doctor publique mi horario disponible y ahi an echo la reserva mis
     * cliente quiero listar esa reserva que
     */
    @Secured("ROLE_DOCTOR")
    @QueryMapping
    public List<Reservation> getReservacion() {
        Person doctor = auth.persona();
        List<Reservation> reservasPendientes = reservationRepository.findByState("PENDIENTE");
        List<Available_time> horariosDisponibles = horarioRepository.findByDoctor(doctor);

        List<Reservation> reservasFiltradas = new ArrayList<>();

        for (Reservation reserva : reservasPendientes) {
            for (Available_time horario : horariosDisponibles) {
                if (reserva.getAvailable_time().getId().equals(horario.getId())) {
                    reservasFiltradas.add(reserva);
                    break;
                }
            }
        }
        return reservasFiltradas;
    }
}
