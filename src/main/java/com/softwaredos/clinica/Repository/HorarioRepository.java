package com.softwaredos.clinica.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.softwaredos.clinica.Model.Available_time;
import com.softwaredos.clinica.Model.Person;

@Repository
public interface HorarioRepository extends MongoRepository<Available_time, String> {
    List<Available_time> findByDoctor(Person doctor);

    @Query("{ 'date' : { $gt: new Date() } }")
    List<Available_time> findAvailableTimesAfterDate();
}
