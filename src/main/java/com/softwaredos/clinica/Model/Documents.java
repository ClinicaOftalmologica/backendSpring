package com.softwaredos.clinica.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "document")
public class Documents {
    @Id
    private String id;
    private String descripcion;
    private String url;
    private char type_file;

    @DBRef
    private Story_detail story_detail;

}


