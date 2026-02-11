package com.devops.estudiantes.service;
import com.devops.estudiantes.model.Estudiante;
import com.devops.estudiantes.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// main rule -> don't allow duplicate student IDs, service decide what should happen
// repository just does what it's told.
@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository repository;
    // registers a new student in the system.
    public Estudiante registerStudent(Estudiante estudiante) {
        // check if a student with this ID already exists
        if (repository.existsById(estudiante.getId())) {
            // Throw an exception to signal that this operation cannot proceed
            // The controller will catch this and return an appropriate error response
            throw new IllegalArgumentException("Ya existe un estudiante con el ID: " + estudiante.getId());
        }
        return repository.save(estudiante);
    }

    // pass-through to the repository, not necessary for listing all students
    public List<Estudiante> getAllStudents() {
        return repository.findAll();
    }
}
