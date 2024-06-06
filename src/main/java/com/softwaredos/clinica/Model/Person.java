package com.softwaredos.clinica.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "person")
public class Person {

    @Id
    private String id;

    private String name;

    private String lastName;

    private String address;

    private String ci;

    private char sexo;

    private String contactNumber;

    private Short tipoUser;

    private String titulo;

    private Date birthDate;

    @DBRef
    private User user;
}