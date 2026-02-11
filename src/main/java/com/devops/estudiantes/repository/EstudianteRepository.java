package com.devops.estudiantes.repository;
import com.devops.estudiantes.model.Estudiante;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

//Repository class that handles data storage and retrieval for Estudiante entities.
@Repository
public class EstudianteRepository {
    private final ConcurrentHashMap<String, Estudiante> estudiantes = new ConcurrentHashMap<>();

    // Saves a student to the repository.
    // If a student with the same ID already exists, it will be replaced.
    public Estudiante save(Estudiante estudiante) {
        estudiantes.put(estudiante.getId(), estudiante);
        return estudiante;
    }

    // Retrieves all students currently stored in the repository
    public List<Estudiante> findAll() {
        // convert the map values (all Estudiante objects) into a List
        // we don't want external code to modify the internal map structure
        // the ArrayList avoid that giving them a safe copy to work with
        return new ArrayList<>(estudiantes.values());
    }

    // checks if a student with the given ID already exists in the repository (returns true if student's id exist)
    public boolean existsById(String id) {
        // containsKey is a fast operation in HashMap (O(1) complexity)
        return estudiantes.containsKey(id);
    }
}
