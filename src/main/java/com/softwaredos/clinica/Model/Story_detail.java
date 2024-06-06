package com.softwaredos.clinica.Model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "story_detail")

public class Story_detail {
    @Id
    private String id;
    private String title;
    private Date data_time;
    private String notes;

    @DBRef
    private Person paciente;

    @DBRef
    private Person doctor;
      
    
}
