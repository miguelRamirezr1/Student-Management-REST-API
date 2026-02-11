package com.devops.estudiantes.controller;
import com.devops.estudiantes.model.Estudiante;
import com.devops.estudiantes.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {
    // Spring will automatically inject this when the controller is created.
    @Autowired
    private EstudianteService service;
    // The @Valid annotation triggers validation of the incoming JSON based
    // on the @NotNull and @NotBlank annotations in the Estudiante class
    // if validation fails, Spring automatically return a 400 Bad Request.

    @PostMapping
    public ResponseEntity<Estudiante> createEstudiante(@Valid @RequestBody Estudiante estudiante) {
        // Delegate the logic to the service layer
        // The service will check for duplicates and save the student
        Estudiante savedEstudiante = service.registerStudent(estudiante);

        // return HTTP 201 (Created) with the saved student in the response body
        // ResponseEntity allows us to control both the status code and the body
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEstudiante);
    }

    @GetMapping
    public ResponseEntity<List<Estudiante>> getAllEstudiantes() {
        // get all students from the service
        List<Estudiante> estudiantes = service.getAllStudents();
        return ResponseEntity.ok(estudiantes);
    }
}
