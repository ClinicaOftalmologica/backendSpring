package com.softwaredos.clinica.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reservation")
public class Reservation {
    @Id
    private String id;

    private String place;

    private String state; // Cancelado,Pendiente,Finalizado

    @DBRef
    private Person paciente; 

    @DBRef
    private Available_time available_time;
}
