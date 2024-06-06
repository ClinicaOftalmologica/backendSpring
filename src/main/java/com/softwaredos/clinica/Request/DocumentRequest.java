package com.softwaredos.clinica.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequest {
    String id;
    String descripcion;
    String url;
    String type_file;
    String story_detail_id;
}


