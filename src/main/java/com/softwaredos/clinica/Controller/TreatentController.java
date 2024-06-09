package com.softwaredos.clinica.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.softwaredos.clinica.Model.Person;
import com.softwaredos.clinica.Model.Treatment;
import com.softwaredos.clinica.Repository.PersonRepository;
import com.softwaredos.clinica.Repository.TreatmentRepository;
import com.softwaredos.clinica.Request.TreatmentRequest;
import com.softwaredos.clinica.config.Auth;

@RestController
public class TreatentController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TreatmentRepository treatmentRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private Auth auth;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    // Como doctor quiero poder realizar receta de tratamiento
    // salida tratamiento funcion storeTratamiento(entrada : tratamientoRequest)
    @Secured("ROLE_DOCTOR")
    @MutationMapping
    public Treatment storeTratamieto(@Argument TreatmentRequest request) {
        try {
            Person paciente = personRepository.findById(request.getPaciente_id())
                    .orElseThrow(() -> new RuntimeException("Story detail not found"));

            Treatment tratamiento = Treatment.builder()
                    .detail(request.getDetail())
                    .title(request.getTitle())
                    .recipe(request.getRecipe())
                    .doctor(auth.persona())
                    .paciente(paciente)
                    .build();
            Treatment treatment = treatmentRepository.save(tratamiento);        

            try {
                // Datos para enviar en el POST
                Map<String, String> postData = new HashMap<>();
                postData.put("tratamiento_id", treatment.getId()); // Suponiendo
                //que el ID es Long
                postData.put("detail", treatment.getDetail());
                postData.put("title", treatment.getTitle());
                postData.put("recipe", treatment.getRecipe());

                // Realizar el POST a la API externa
                String apiUrl = "http://localhost:3000/api/storeTreatment";
                restTemplate.postForObject(apiUrl, postData, String.class);
            } catch (Exception e) {
                LOGGER.info("Error: ", e);
            }
            return treatment;
        } catch (Exception e) {
            return null;
        }
    }

    // Como doctor quiero poder eliminar tratamiento de mi paciente
    // salida bolean funcion deleteTratamiento(entada: tratamiento_id)
    @Secured("ROLE_DOCTOR")
    @MutationMapping
    public boolean deleteTratamiento(@Argument String id) {
        try {
            Treatment tratamiento = treatmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Treatment not found"));
            treatmentRepository.delete(tratamiento);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Como doctor quiero poder ver modificar tratamiento
    // salida tratamiento funcion updateTratamiento(entrada: tratamientoRequets)
    @Secured("ROLE_DOCTOR")
    @MutationMapping
    public Treatment updateTratamiento(@Argument TreatmentRequest request) {
        try {
            Treatment tratamiento = treatmentRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Treatment not found"));

            tratamiento.setDetail(request.getDetail());
            tratamiento.setTitle(request.getTitle());
            tratamiento.setRecipe(request.getRecipe());
            // Aquí podrías actualizar otros campos si es necesario

            return treatmentRepository.save(tratamiento);
        } catch (Exception e) {
            return null;
        }
    }

    // como paciente y doctor quiero ver el detalle de mis tratamiento
    // salida tratamiento fucnion showTratamiento(entrada: id)
    @Secured({ "ROLE_PACIENTE", "ROLE_DOCTOR" })
    @QueryMapping
    public Treatment showTratamiento(@Argument String id) {
        return treatmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treatment not found"));
    }

    // Como docotor quiero poder ver la lista tratamiento de mi cliente
    // salida [tratamiento] funcion getTratamientobyIdPaciente(entrada: paciente_id)
    @Secured("ROLE_DOCTOR")
    @QueryMapping
    public List<Treatment> getTratamientoByIdPaciente(@Argument String paciente_id) {
        try {
            Person paciente = personRepository.findById(paciente_id)
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            return treatmentRepository.findByPaciente(paciente);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Como paciente quiero poder ver mis tratamiento
    // salida [tratamiento] funcion getTratamiento() * se verfica mi auth para savar
    // mi tratamiento
    @Secured("ROLE_PACIENTE")
    @QueryMapping
    public List<Treatment> getTratamiento() {
        try {
            Person paciente = auth.persona(); // Suponiendo que `auth.persona()` retorna el paciente autenticado
            return treatmentRepository.findByPaciente(paciente);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
