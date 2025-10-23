package com.ejercicio.vista;
import java.util.List;

import com.ejercicio.modelo.curso;

public class cursoVista {

    public void mostrarInformacionCurso (List<curso> cursos) {
        cursos.forEach(Curso -> {

         System.out.println("-----------SE INGRESO----------------");
            System.out.println("ID: " + Curso.getId());
            System.out.println("Nombre: " + Curso.getNombre());
            System.out.println("Descripcion: " + Curso.getDescripcion());
            System.out.println("Creditos: " + Curso.getCreditos());
            System.out.println("ID Docente: " + Curso.getId_docente() );
        System.out.println("-----------SE INGRESO----------------");
    
    });
 
    }

}