package com.softwaredos.clinica.Request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentRequest {
    String id;
    String detail;
    String title;
    String recipe;
    String paciente_id;
}

