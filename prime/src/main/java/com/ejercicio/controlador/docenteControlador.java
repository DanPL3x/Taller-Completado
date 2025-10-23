package com.ejercicio.controlador;

import java.util.List;
import java .util.ArrayList;

import com.ejercicio.modelo.docente;
import com.ejercicio.vista.docenteVista;


public class docenteControlador {
    private List<docente> docentes;
    private docenteVista vista;

    public docenteControlador(docenteVista vista) {
        this.docentes = new ArrayList<>();
        this.vista = vista;
    }
    public void actulizarVista() {
        vista.mostrarInformacionDocente(docentes);
    }

    public void agregarDocentes(docente NuevoDocente) {
    docente.InsertarDocente(NuevoDocente);
    System.out.println("El docente fue agregado exitosamente.");
    docentes.add(NuevoDocente);
}

    public docente obtenerDocentePorId(int id) {
        return docente.ObtenerDocentePorId(id);
    }

    public boolean actualizarDocente(docente d) {
        return docente.ActualizarDocente(d);
    }

    public boolean eliminarDocente(int id) {
        return docente.EliminarDocentePorId(id);
    }

    public void mostrarInformacionDocente(docenteControlador dCtrl) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarInformacionDocente'");
    }

    // listar wrapper
    public java.util.List<docente> listar() {
        return docente.listarTodos();
    }

}

