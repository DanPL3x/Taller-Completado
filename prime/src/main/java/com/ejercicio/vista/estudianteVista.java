package com.ejercicio.vista;
import com.ejercicio.modelo.estudiante;

import java.util.List;

public class estudianteVista {
    public  void mostrarInformacionEsudiantes (List<estudiante> estudiantes) {

        estudiantes.forEach(estudiante -> {
            System.out.println("Nombre: " + estudiante.getNombre());
            System.out.println("Correo: " + estudiante.getCorreo());
            System.out.println("ID: " + estudiante.getId());
            System.out.println("Edad: " + estudiante.getEdad());
    
    });
 
    }

}