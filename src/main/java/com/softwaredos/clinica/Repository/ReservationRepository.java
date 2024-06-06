package com.softwaredos.clinica.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.softwaredos.clinica.Model.Person;
import com.softwaredos.clinica.Model.Reservation;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation,String> {
     List<Reservation> findByStateAndPaciente(String state, Person paciente);
}
