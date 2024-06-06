package com.softwaredos.clinica.Request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailRequest {
    String id;
    String title;
    String notes;
    String paciente_id;
}
