package com.universidad.service; // Define el paquete al que pertenece esta interfaz.

import com.universidad.dto.EstudianteDTO; // Importa la clase DTO para los estudiantes.

import java.util.List; // Importa la interfaz List para manejar listas.
import java.util.Optional; // Importa Optional para manejar valores que pueden ser nulos.

public interface IEstudianteService { // Declara la interfaz del servicio de estudiantes.

    List<EstudianteDTO> obtenerTodosLosEstudiantes(); 
    // Método para obtener una lista con todos los estudiantes en formato DTO.

    Optional<EstudianteDTO> obtenerEstudiantePorId(Long id);
    // Método que busca un estudiante por su ID y devuelve un Optional, que puede estar vacío si el estudiante no existe.

    EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO estudianteDTO);
    // Método para actualizar un estudiante existente a partir de su ID y nuevos datos.

    EstudianteDTO crearEstudiante(EstudianteDTO estudianteDTO);
    // Método para crear un nuevo estudiante en el sistema y devolverlo en formato DTO.

    void eliminarEstudiante(Long id);
    // Método para eliminar un estudiante por su ID, sin devolver un valor.
}
