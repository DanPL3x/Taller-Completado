package com.ejercicio.vista;
import java.util.List;

import com.ejercicio.modelo.docente;

public class docenteVista {
    public class estudianteVista {
    public  void mostrarInformacionDocente (List<docente> docentes) {

        docentes.forEach(Docente -> {
            System.out.println("Nombre: " + Docente.getNombre());
            System.out.println("Correo: " + Docente.getCorreo());
            System.out.println("ID: " + Docente.getId());
            System.out.println("Especialidad: " + Docente.getEspecialidad());

    
    });
 
    }
    }

    public void mostrarInformacionDocente(List<docente> docentes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarInformacionDocente'");
    }

}
