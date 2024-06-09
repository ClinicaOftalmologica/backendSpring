package com.softwaredos.clinica.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "treatment")
public class Treatment {
    @Id
    private String id;
    private String detail;
    private String title;
    private String recipe;

    @DBRef
    private Person paciente;
    @DBRef
    private Person doctor;

}
