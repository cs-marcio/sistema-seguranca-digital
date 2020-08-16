package br.com.squadra.ssd.controller;

import br.com.squadra.ssd.model.Sistema;
import br.com.squadra.ssd.repository.SistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class SistemaController {

    @Autowired
    SistemaRepository repository;

    @GetMapping("sistemas")
    public List<Sistema> getSistemas() {
        return repository.findAll();
    }

}
