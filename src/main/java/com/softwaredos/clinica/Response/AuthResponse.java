package com.softwaredos.clinica.Response;

import com.softwaredos.clinica.Model.Person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    String token;
    String message;
    Person person;
}
