package com.softwaredos.clinica.Request;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    String id;
    String place;
    String available_time_id;
}
