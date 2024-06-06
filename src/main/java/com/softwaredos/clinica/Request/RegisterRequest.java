package com.softwaredos.clinica.Request;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    //String username;
    String email;
    String password;

    // Dato Paciente
    String name;
    String last_name;
    String address;
    String ci;
    String sexo;
    String contact_number;
    String birth_date;
    String url;
    String titulo;
    
}
