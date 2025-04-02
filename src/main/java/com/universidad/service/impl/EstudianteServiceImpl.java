package com.universidad.service.impl; // Define el paquete al que pertenece esta clase

import com.universidad.dto.EstudianteDTO; // Importa la clase EstudianteDTO para transferir datos
import com.universidad.model.Estudiante; // Importa la clase Estudiante que representa la entidad
import com.universidad.repository.EstudianteRepository; // Importa el repositorio de estudiantes
import com.universidad.service.IEstudianteService; // Importa la interfaz del servicio de estudiantes

import jakarta.annotation.PostConstruct; // Importa la anotación PostConstruct para inicializar datos al arrancar la aplicación
import org.springframework.beans.factory.annotation.Autowired; // Importa la anotación Autowired para inyección de dependencias
import org.springframework.stereotype.Service; // Marca esta clase como un servicio de Spring

import java.util.ArrayList; // Importa la clase ArrayList para manejar listas
import java.util.List; // Importa la interfaz List para almacenar listas de datos
import java.util.Optional; // Importa la clase Optional para manejar valores opcionales

@Service // Declara esta clase como un servicio de Spring
public class EstudianteServiceImpl implements IEstudianteService { // Implementa la interfaz de servicio de estudiantes

    private final EstudianteRepository estudianteRepository; // Declara una variable final para el repositorio de estudiantes

    @Autowired // Indica que Spring debe inyectar una instancia del repositorio
    public EstudianteServiceImpl(EstudianteRepository estudianteRepository) { 
        this.estudianteRepository = estudianteRepository; // Inicializa el repositorio en el constructor
    }
    
    @PostConstruct // Ejecuta este método después de la creación del bean
    public void init() {
        estudianteRepository.init(); // Llama al método init del repositorio para cargar datos iniciales
    }

    @Override 
    public List<EstudianteDTO> obtenerTodosLosEstudiantes() { // Método para obtener todos los estudiantes
        List<Estudiante> estudiantes = estudianteRepository.findAll(); // Obtiene la lista de estudiantes desde el repositorio
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>(); // Crea una lista para almacenar los DTOs
        
        for (Estudiante estudiante : estudiantes) { // Itera sobre la lista de estudiantes
            estudiantesDTO.add(convertToDTO(estudiante)); // Convierte cada estudiante a DTO y lo agrega a la lista
        }
        return estudiantesDTO; // Retorna la lista de DTOs
    }

    public EstudianteDTO createStudent(EstudianteDTO estudianteDTO){ // Método para crear un nuevo estudiante
        Estudiante estudiante = convertToEntity(estudianteDTO); // Convierte el DTO en una entidad
        Estudiante savedEstudiante = estudianteRepository.save(estudiante); // Guarda el estudiante en el repositorio
        return convertToDTO(savedEstudiante); // Convierte la entidad guardada en DTO y la retorna
    }

    public List<EstudianteDTO> getAllEstudents(){ // Método para obtener todos los estudiantes en DTO
        List<Estudiante> estudiantes = estudianteRepository.findAll(); // Obtiene la lista de estudiantes
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>(); // Crea una lista de DTOs
        for(Estudiante estudiante : estudiantes){ // Itera sobre la lista de estudiantes
            estudiantesDTO.add(convertToDTO(estudiante)); // Convierte cada estudiante a DTO y lo agrega a la lista
        }
        return estudiantesDTO; // Retorna la lista de DTOs
    }

    private EstudianteDTO convertToDTO(Estudiante estudiante) { // Método privado para convertir una entidad en DTO
        return EstudianteDTO.builder() // Usa el patrón Builder para crear un DTO
                .id(estudiante.getId()) // Asigna el ID
                .nombre(estudiante.getNombre()) // Asigna el nombre
                .apellido(estudiante.getApellido()) // Asigna el apellido
                .email(estudiante.getEmail()) // Asigna el email
                .fechaNacimiento(estudiante.getFechaNacimiento()) // Asigna la fecha de nacimiento
                .numeroInscripcion(estudiante.getNumeroInscripcion()) // Asigna el número de inscripción
                .build(); // Construye el objeto DTO
    }
    
    private Estudiante convertToEntity(EstudianteDTO estudianteDTO) { // Método privado para convertir un DTO en entidad
        return Estudiante.builder() // Usa el patrón Builder para crear una entidad
                .id(estudianteDTO.getId()) // Asigna el ID
                .nombre(estudianteDTO.getNombre()) // Asigna el nombre
                .apellido(estudianteDTO.getApellido()) // Asigna el apellido
                .email(estudianteDTO.getEmail()) // Asigna el email
                .fechaNacimiento(estudianteDTO.getFechaNacimiento()) // Asigna la fecha de nacimiento
                .numeroInscripcion(estudianteDTO.getNumeroInscripcion()) // Asigna el número de inscripción
                .build(); // Construye el objeto Estudiante
    }

    @Override
    public Optional<EstudianteDTO> obtenerEstudiantePorId(Long id) { // Método para obtener un estudiante por ID
        return estudianteRepository.findById(id).map(this::convertToDTO); // Busca el estudiante y lo convierte a DTO si existe
    }

    @Override
    public EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO estudianteDTO) { // Método para actualizar un estudiante
        if (!estudianteRepository.existsById(id)) { // Verifica si el estudiante existe
            throw new RuntimeException("Estudiante no encontrado"); // Lanza una excepción si no se encuentra
        }
        Estudiante estudiante = convertToEntity(estudianteDTO); // Convierte el DTO a entidad
        estudiante.setId(id); // Establece el ID del estudiante
        return convertToDTO(estudianteRepository.save(estudiante)); // Guarda la entidad y la retorna como DTO
    }
    
    @Override
    public EstudianteDTO crearEstudiante(EstudianteDTO estudianteDTO) { // Método para crear un estudiante
        Estudiante estudiante = convertToEntity(estudianteDTO); // Convierte el DTO a entidad
        Estudiante savedEstudiante = estudianteRepository.save(estudiante); // Guarda el estudiante
        return convertToDTO(savedEstudiante); // Retorna el estudiante guardado como DTO
    }
    
    @Override
    public void eliminarEstudiante(Long id) { // Método para eliminar un estudiante
        if (!estudianteRepository.existsById(id)) { // Verifica si el estudiante existe
            throw new RuntimeException("Estudiante no encontrado"); // Lanza una excepción si no se encuentra
        }
        estudianteRepository.deleteById(id); // Elimina el estudiante por ID
    }
}
