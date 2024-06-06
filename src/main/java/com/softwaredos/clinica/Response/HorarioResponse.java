package com.softwaredos.clinica.Response;

import java.util.List;

import com.softwaredos.clinica.Model.Available_time;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorarioResponse {
    Integer status;
    String message;
    List<Available_time> data;

}
