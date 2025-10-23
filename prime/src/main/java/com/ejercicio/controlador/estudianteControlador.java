package com.ejercicio.controlador;

import java.util.List;
import java .util.ArrayList;

import com.ejercicio.modelo.Database;
import com.ejercicio.modelo.estudiante;
import com.ejercicio.vista.estudianteVista;


public class estudianteControlador {
    private List<estudiante> estudiantes;
    private estudianteVista vista;

    public estudianteControlador(estudianteVista vista) {
        this.estudiantes = new ArrayList<>();
        this.vista = vista;
    }

    public void actulizarVista() {
        vista.mostrarInformacionEsudiantes(estudiantes);
    }

    public void agregarEstudiante(estudiante Nuevoestudiante) {
       estudiante.InsertarEstudiante(Nuevoestudiante);
       System.out.println("El estudiante fue agregado exitosamente.");
        estudiantes.add(Nuevoestudiante);
    }

    public estudiante obtenerEstudiantePorId(int id) {
        return estudiante.ObtenerEstudiantePorId(id);
    }

    public boolean actualizarEstudiante(estudiante e) {
        return estudiante.ActualizarEstudiante(e);
    }

    public boolean eliminarEstudiante(int id) {
        return estudiante.EliminarEstudiantePorId(id);
    }

    // wrapper to list estudiantes from DB
    public java.util.List<estudiante> listar() {
        return estudiante.listarTodos();
    }
}


