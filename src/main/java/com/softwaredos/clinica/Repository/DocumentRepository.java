package com.softwaredos.clinica.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.softwaredos.clinica.Model.Documents;


@Repository
public interface DocumentRepository extends MongoRepository<Documents,String> {
    // List<Documents> findByStory_detail(Story_detail story_detail);
}
