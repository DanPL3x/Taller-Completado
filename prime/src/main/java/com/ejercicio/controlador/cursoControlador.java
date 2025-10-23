package com.ejercicio.controlador;

import java.util.List;
import java .util.ArrayList;

import com.ejercicio.modelo.curso;
import com.ejercicio.vista.cursoVista;


public class cursoControlador {
    private List <curso> cursos;
    private cursoVista vista;

    public cursoControlador(cursoVista vista) {
        this.cursos = new ArrayList<>();
        this.vista = vista;
    }

    public void actulizarVista() {
        vista.mostrarInformacionCurso (cursos);
    }

    public void agregaCursos (curso NuevoCurso) {
        // Persistir en la base de datos y luego mantener en la lista en memoria
        boolean ok = curso.InsertarCurso(NuevoCurso);
        if (ok) {
            System.out.println("Curso insertado en la base de datos.");
            this.cursos.add(NuevoCurso);
        } else {
            System.out.println("No se pudo insertar el curso en la base de datos.");
        }
    }

    public curso obtenerCursoPorId(int id) {
        return curso.ObtenerCursoPorId(id);
    }

    public boolean actualizarCurso(curso c) {
        return curso.ActualizarCurso(c);
    }

    public boolean eliminarCurso(int id) {
        return curso.EliminarCursoPorId(id);
    }
    
    // listar wrapper
    public java.util.List<curso> listar() {
        return curso.listarTodos();
    }
}
