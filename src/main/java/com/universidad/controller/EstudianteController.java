package com.universidad.controller; // Define el paquete donde se encuentra la clase

import com.universidad.dto.EstudianteDTO; // Importa la clase EstudianteDTO
import com.universidad.service.IEstudianteService; // Importa la interfaz del servicio de estudiantes

import org.springframework.beans.factory.annotation.Autowired; // Permite la inyección de dependencias
import org.springframework.http.HttpStatus; // Define los códigos de estado HTTP
import org.springframework.http.ResponseEntity; // Permite manejar respuestas HTTP
import org.springframework.web.bind.annotation.*; // Importa las anotaciones para la API REST

import java.util.List; // Importa la clase List para manejar listas

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api") // Define la ruta base para los endpoints de este controlador
public class EstudianteController {

    private final IEstudianteService estudianteService; // Servicio de estudiantes para manejar la lógica de negocio

    @Autowired // Inyección de dependencia del servicio de estudiantes
    public EstudianteController(IEstudianteService estudianteService) {
        this.estudianteService = estudianteService; // Asigna la instancia del servicio
    }

    @GetMapping("/estudiantes") // Define un endpoint para obtener todos los estudiantes con método GET
    public ResponseEntity<List<EstudianteDTO>> obtenerTodosLosEstudiantes() {
        List<EstudianteDTO> estudiantes = estudianteService.obtenerTodosLosEstudiantes(); // Llama al servicio para obtener la lista de estudiantes
        return ResponseEntity.ok(estudiantes); // Retorna la lista con código 200 OK
    }

    @GetMapping("/estudiantes/{id}") // Define un endpoint para obtener un estudiante por ID con método GET
    public ResponseEntity<EstudianteDTO> obtenerEstudiantePorId(@PathVariable Long id) { // Extrae el ID desde la URL
        return estudianteService.obtenerEstudiantePorId(id) // Llama al servicio para obtener el estudiante
                .map(ResponseEntity::ok) // Si se encuentra, devuelve 200 OK con el estudiante
                .orElse(ResponseEntity.notFound().build()); // Si no se encuentra, devuelve 404 Not Found
    }

    @PutMapping("/estudiantes/{id}") // Define un endpoint para actualizar un estudiante con método PUT
    public ResponseEntity<EstudianteDTO> actualizarEstudiante(@PathVariable Long id, @RequestBody EstudianteDTO estudianteDTO) { // Recibe el ID y los datos actualizados
        return ResponseEntity.ok(estudianteService.actualizarEstudiante(id, estudianteDTO)); // Llama al servicio, actualiza el estudiante y devuelve 200 OK
    }

    @PostMapping("/estudiantes") // Define un endpoint para crear un estudiante con método POST
    public ResponseEntity<EstudianteDTO> crearEstudiante(@RequestBody EstudianteDTO estudianteDTO) { // Recibe los datos del nuevo estudiante
        EstudianteDTO nuevoEstudiante = estudianteService.crearEstudiante(estudianteDTO); // Llama al servicio para crear el estudiante
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEstudiante); // Retorna el nuevo estudiante con código 201 Created
    }

    @DeleteMapping("/estudiantes/{id}") // Define un endpoint para eliminar un estudiante con método DELETE
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long id) { // Recibe el ID del estudiante
        estudianteService.eliminarEstudiante(id); // Llama al servicio para eliminar el estudiante
        return ResponseEntity.noContent().build(); // Retorna código 204 No Content si la operación es exitosa
    }
}