package com.softwaredos.clinica.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.softwaredos.clinica.Model.Image;
import com.softwaredos.clinica.Model.User;

@Repository
public interface ImageRepository extends MongoRepository<Image, String> {

List<Image> findByUser(User user);
}