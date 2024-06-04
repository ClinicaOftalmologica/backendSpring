package com.softwaredos.clinica.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softwaredos.clinica.Model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {


}