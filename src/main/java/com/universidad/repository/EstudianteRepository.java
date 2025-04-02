package com.universidad.repository; // Define el paquete al que pertenece esta clase

import com.universidad.model.Estudiante; // Importa la clase Estudiante del paquete model
import org.springframework.stereotype.Repository; // Importa la anotación Repository de Spring

import java.time.LocalDate; // Importa la clase LocalDate para manejar fechas
import java.util.ArrayList; // Importa la clase ArrayList para manejar listas
import java.util.List; // Importa la interfaz List para manejar listas
import java.util.Map; // Importa la interfaz Map para manejar pares clave-valor
import java.util.Optional; // Importa Optional para manejar valores que pueden ser nulos
import java.util.concurrent.ConcurrentHashMap; // Importa ConcurrentHashMap para almacenamiento seguro en entornos concurrentes
import java.util.concurrent.atomic.AtomicLong; // Importa AtomicLong para manejar IDs de forma segura en concurrencia

@Repository // Anotación que indica que esta clase es un repositorio de Spring
public class EstudianteRepository {
    private final Map<Long, Estudiante> estudiantes = new ConcurrentHashMap<>(); // Mapa concurrente para almacenar estudiantes con su ID como clave
    private final AtomicLong idContador = new AtomicLong(1); // Contador atómico para generar IDs únicos
    
    public Estudiante save(Estudiante estudiante) { // Método para guardar un estudiante en el repositorio
        if (estudiante.getId() == null) { // Si el estudiante no tiene ID asignado
            estudiante.setId(idContador.getAndIncrement()); // Se le asigna un ID único
        }
        estudiantes.put(estudiante.getId(), estudiante); // Se almacena el estudiante en el mapa
        return estudiante; // Retorna el estudiante guardado
    }
    
    public List<Estudiante> findAll() { // Método para obtener todos los estudiantes
        return new ArrayList<>(estudiantes.values()); // Retorna una lista con todos los valores almacenados en el mapa
    }
    
    public void deleteById(Long id) { // Método para eliminar un estudiante por su ID
        estudiantes.remove(id); // Se elimina el estudiante del mapa
    }
    
    public Optional<Estudiante> findById(Long id) { // Método para buscar un estudiante por ID
        return Optional.ofNullable(estudiantes.get(id)); // Retorna el estudiante envuelto en un Optional, o vacío si no existe
    }

    public boolean existsById(Long id) { // Método para verificar si un estudiante existe en el repositorio por su ID
        return estudiantes.containsKey(id); // Retorna true si el ID está en el mapa, false si no
    }

    public void init() { // Método para inicializar datos de prueba
        Estudiante estudiante1 = Estudiante.builder() // Crea un objeto Estudiante con el patrón Builder
                .nombre("Juan") // Asigna el nombre
                .apellido("Pérez") // Asigna el apellido
                .email("juan.perez@example.com") // Asigna el correo electrónico
                .fechaNacimiento(LocalDate.of(2000, 5, 15)) // Asigna la fecha de nacimiento
                .numeroInscripcion("S001") // Asigna el número de inscripción
                .build(); // Construye el objeto
                
        Estudiante estudiante2 = Estudiante.builder() // Crea otro estudiante de la misma forma
                .nombre("María") 
                .apellido("González")
                .email("maria.gonzalez@example.com") 
                .fechaNacimiento(LocalDate.of(2001, 8, 22)) 
                .numeroInscripcion("S002")
                .build(); 
                
        save(estudiante1); // Guarda el primer estudiante en el repositorio
        save(estudiante2); // Guarda el segundo estudiante en el repositorio
    }
}