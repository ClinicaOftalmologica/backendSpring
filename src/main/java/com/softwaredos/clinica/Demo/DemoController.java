package com.softwaredos.clinica.Demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class DemoController {
    
    @PostMapping(value = "demo")
    public String welcome(){
        return "Hola Edilson este es tu empoin protegido ";
    }
}
