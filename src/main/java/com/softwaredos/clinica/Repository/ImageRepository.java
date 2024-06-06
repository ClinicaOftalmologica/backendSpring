package com.softwaredos.clinica.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.softwaredos.clinica.Model.Image;

@Repository
public interface ImageRepository extends MongoRepository<Image, String> {


}