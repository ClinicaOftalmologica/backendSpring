package com.softwaredos.clinica.Controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;

import com.softwaredos.clinica.Model.Documents;
import com.softwaredos.clinica.Model.Story_detail;
import com.softwaredos.clinica.Repository.DocumentRepository;
import com.softwaredos.clinica.Repository.Story_detailRepository;
import com.softwaredos.clinica.Request.DocumentRequest;

@RestController
public class DocumentController {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private Story_detailRepository story_detailRepository;

    // listar documento id_historia_detalle
    @QueryMapping
    public List<Documents> getDocument(@Argument String historial_id) {
        if (story_detailRepository.existsById(historial_id)) {
           // return documentRepository.findByStory_detail(story_detailRepository.findById(historial_id).get());
           return documentRepository.findAll();
        } else {
            return null;
        }

    }

    // subir documento id_histarial_detallea
    @MutationMapping
    public Documents uploadDocument(@Argument DocumentRequest request) {
        try {
            Story_detail storyDetail = story_detailRepository.findById(request.getStory_detail_id())
                    .orElseThrow(() -> new RuntimeException("Story detail not found"));

            Documents document = Documents.builder()
                    .descripcion(request.getDescripcion())
                    .url(request.getUrl())
                    .type_file(request.getType_file().charAt(0))
                    .story_detail(storyDetail)
                    .build();

            return documentRepository.save(document);
        } catch (Exception e) {
            return null;
        }
    }

    // eliminar documento id_documento
    @MutationMapping
    public Boolean deleteDocument(@Argument String id) {
        try {
            if (documentRepository.existsById(id)) {
                documentRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    // show documento id
    @QueryMapping
    public Documents showDocument(@Argument String id) {
        try {
            if (documentRepository.existsById(id)) {
                
                return documentRepository.findById(id).get();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
