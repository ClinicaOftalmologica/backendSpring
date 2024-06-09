package com.softwaredos.clinica.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.softwaredos.clinica.Model.Person;
import com.softwaredos.clinica.Model.Treatment;

public interface TreatmentRepository extends MongoRepository<Treatment,String> {
    List<Treatment> findByPaciente(Person paciente);
}
