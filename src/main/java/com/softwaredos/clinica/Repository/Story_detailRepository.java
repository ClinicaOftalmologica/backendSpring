package com.softwaredos.clinica.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.softwaredos.clinica.Model.Person;
import com.softwaredos.clinica.Model.Story_detail;

@Repository
public interface Story_detailRepository extends MongoRepository<Story_detail,String> {
    List<Story_detail> findByPaciente(Person paciente);
}
