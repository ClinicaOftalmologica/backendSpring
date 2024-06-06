package com.softwaredos.clinica.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.web.bind.annotation.RestController;

import com.softwaredos.clinica.Model.Available_time;
import com.softwaredos.clinica.Model.Reservation;
import com.softwaredos.clinica.Repository.HorarioRepository;
import com.softwaredos.clinica.Repository.ReservationRepository;
import com.softwaredos.clinica.Request.ReservationRequest;
import com.softwaredos.clinica.config.Auth;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
// @Secured("ROLE_PACIENTE")
public class ReservationController {

    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private Auth auth;

    // listar de todo los horarios disponible con el nombre del doctor para que se
    // pueda hacer la reserva
    @QueryMapping
    public List<Available_time> getHorarioDisponible() {
        return horarioRepository.findAvailableTimesAfterDate();
    }

    // Funcion para hacere la reserva id horario
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

    // Funcion para ver mis reservas Pendiente
    @QueryMapping
    public List<Reservation> getReservaPendiente() {
        return reservationRepository.findByStateAndPaciente("PENDIENTE", auth.persona());
    }
}
