package com.softwaredos.clinica.Model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue
    Integer id;

    @Basic
    @Column(nullable = false, length = 100)
    String name;

    @Basic
    @Column(name = "last_name", nullable = false, length = 100)
    String lastName;

    @Basic
    @Column(length = 255)
    String address;

    @Basic
    @Column(length = 50)
    String ci;

    @Basic
    @Column(nullable = false, length = 1)
    char sexo;

    @Basic
    @Column(name = "contact_number", nullable = false, length = 15)
    String contactNumber;

    @Basic
    @Column(name = "tipo_user", nullable = false)
    Short tipoUser;

    @Basic
    @Column(length = 255)
    String titulo;

    @Basic
    @Column(name = "birth_date")
    Date birthDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
