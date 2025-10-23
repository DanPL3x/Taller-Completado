package com.ejercicio.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

import com.ejercicio.modelo.estudiante;
import com.ejercicio.modelo.docente;
import com.ejercicio.modelo.curso;

public class AppFXController {

    // Estudiante fields
    @FXML private TextField estId;
    @FXML private TextField estNombre;
    @FXML private TextField estCorreo;
    @FXML private TextField estEdad;
    @FXML private TextArea estLog;

    // Docente fields
    @FXML private TextField docId;
    @FXML private TextField docNombre;
    @FXML private TextField docCorreo;
    @FXML private TextField docEspecialidad;
    @FXML private TextArea docLog;

    // Curso fields
    @FXML private TextField curId;
    @FXML private TextField curNombre;
    @FXML private TextField curDescripcion;
    @FXML private TextField curCreditos;
    @FXML private TextField curIdDocente;
    @FXML private TextArea curLog;

    private estudianteControlador estCtrl;
    private docenteControlador docCtrl;
    private cursoControlador curCtrl;

    @FXML
    private void initialize() {
        estCtrl = new estudianteControlador(new com.ejercicio.vista.estudianteVista());
        docCtrl = new docenteControlador(new com.ejercicio.vista.docenteVista());
        curCtrl = new cursoControlador(new com.ejercicio.vista.cursoVista());
    }

    // Helper loggers
    private void log(TextArea area, String msg) {
        area.appendText(msg + "\n");
    }

    // Estudiante handlers
    @FXML private void onCreateEstudiante() {
        try {
            estudiante e = new estudiante(Integer.parseInt(estId.getText()), estNombre.getText(), estCorreo.getText(), Integer.parseInt(estEdad.getText()));
            estCtrl.agregarEstudiante(e);
            log(estLog, "Estudiante creado");
        } catch (Exception ex) { log(estLog, "Error crear: " + ex.getMessage()); }
    }
    @FXML private void onReadEstudiante() {
        try {
            estudiante e = estCtrl.obtenerEstudiantePorId(Integer.parseInt(estId.getText()));
            if (e == null) log(estLog, "No encontrado");
            else {
                estNombre.setText(e.getNombre()); estCorreo.setText(e.getCorreo()); estEdad.setText(String.valueOf(e.getEdad()));
                log(estLog, "Leído");
            }
        } catch (Exception ex) { log(estLog, "Error leer: " + ex.getMessage()); }
    }
    @FXML private void onUpdateEstudiante() {
        try {
            estudiante e = estCtrl.obtenerEstudiantePorId(Integer.parseInt(estId.getText()));
            if (e == null) { log(estLog, "No existe"); return; }
            e.setNombre(estNombre.getText()); e.setCorreo(estCorreo.getText()); e.setEdad(Integer.parseInt(estEdad.getText()));
            if (estCtrl.actualizarEstudiante(e)) log(estLog, "Actualizado"); else log(estLog, "No actualizado");
        } catch (Exception ex) { log(estLog, "Error actualizar: " + ex.getMessage()); }
    }
    @FXML private void onDeleteEstudiante() {
        try {
            if (estCtrl.eliminarEstudiante(Integer.parseInt(estId.getText()))) log(estLog, "Eliminado"); else log(estLog, "No eliminado");
        } catch (Exception ex) { log(estLog, "Error eliminar: " + ex.getMessage()); }
    }
    @FXML private void onListEstudiantes() {
        try {
            List<estudiante> list = estCtrl.listar();
            if (list == null || list.isEmpty()) log(estLog, "Sin estudiantes");
            else list.forEach(s -> log(estLog, s.getId() + " - " + s.getNombre()));
        } catch (Exception ex) { log(estLog, "Error listar: " + ex.getMessage()); }
    }
    @FXML private void onClearEstudiante() { estId.clear(); estNombre.clear(); estCorreo.clear(); estEdad.clear(); }

    // Docente handlers
    @FXML private void onCreateDocente() {
        try {
            docente d = new docente(Integer.parseInt(docId.getText()), docNombre.getText(), docCorreo.getText(), docEspecialidad.getText());
            docCtrl.agregarDocentes(d);
            log(docLog, "Docente creado");
        } catch (Exception ex) { log(docLog, "Error crear: " + ex.getMessage()); }
    }
    @FXML private void onReadDocente() {
        try {
            docente d = docCtrl.obtenerDocentePorId(Integer.parseInt(docId.getText()));
            if (d == null) log(docLog, "No encontrado");
            else { docNombre.setText(d.getNombre()); docCorreo.setText(d.getCorreo()); docEspecialidad.setText(d.getEspecialidad()); log(docLog, "Leído"); }
        } catch (Exception ex) { log(docLog, "Error leer: " + ex.getMessage()); }
    }
    @FXML private void onUpdateDocente() {
        try {
            docente d = docCtrl.obtenerDocentePorId(Integer.parseInt(docId.getText()));
            if (d == null) { log(docLog, "No existe"); return; }
            d.setNombre(docNombre.getText()); d.setCorreo(docCorreo.getText()); d.setEspecialidad(docEspecialidad.getText());
            if (docCtrl.actualizarDocente(d)) log(docLog, "Actualizado"); else log(docLog, "No actualizado");
        } catch (Exception ex) { log(docLog, "Error actualizar: " + ex.getMessage()); }
    }
    @FXML private void onDeleteDocente() {
        try { if (docCtrl.eliminarDocente(Integer.parseInt(docId.getText()))) log(docLog, "Eliminado"); else log(docLog, "No eliminado"); } catch (Exception ex) { log(docLog, "Error eliminar: " + ex.getMessage()); }
    }
    @FXML private void onListDocentes() {
        try { log(docLog, "Listar no implementado en controlador"); } catch (Exception ex) { log(docLog, "Error listar: " + ex.getMessage()); }
    }
    @FXML private void onClearDocente() { docId.clear(); docNombre.clear(); docCorreo.clear(); docEspecialidad.clear(); }

    // Curso handlers
    @FXML private void onCreateCurso() {
        try {
            curso c = new curso(Integer.parseInt(curId.getText()), curNombre.getText(), curDescripcion.getText(), Integer.parseInt(curCreditos.getText()), Integer.parseInt(curIdDocente.getText()));
            curCtrl.agregaCursos(c);
            log(curLog, "Curso creado");
        } catch (Exception ex) { log(curLog, "Error crear: " + ex.getMessage()); }
    }
    @FXML private void onReadCurso() {
        try {
            curso c = curCtrl.obtenerCursoPorId(Integer.parseInt(curId.getText()));
            if (c == null) log(curLog, "No encontrado");
            else { curNombre.setText(c.getNombre()); curDescripcion.setText(c.getDescripcion()); curCreditos.setText(String.valueOf(c.getCreditos())); curIdDocente.setText(String.valueOf(c.getId_docente())); log(curLog, "Leído"); }
        } catch (Exception ex) { log(curLog, "Error leer: " + ex.getMessage()); }
    }
    @FXML private void onUpdateCurso() {
        try {
            curso c = curCtrl.obtenerCursoPorId(Integer.parseInt(curId.getText()));
            if (c == null) { log(curLog, "No existe"); return; }
            c.setNombre(curNombre.getText()); c.setDescripcion(curDescripcion.getText()); c.setCreditos(Integer.parseInt(curCreditos.getText())); c.setId_docente(Integer.parseInt(curIdDocente.getText()));
            if (curCtrl.actualizarCurso(c)) log(curLog, "Actualizado"); else log(curLog, "No actualizado");
        } catch (Exception ex) { log(curLog, "Error actualizar: " + ex.getMessage()); }
    }
    @FXML private void onDeleteCurso() {
        try { if (curCtrl.eliminarCurso(Integer.parseInt(curId.getText()))) log(curLog, "Eliminado"); else log(curLog, "No eliminado"); } catch (Exception ex) { log(curLog, "Error eliminar: " + ex.getMessage()); }
    }
    @FXML private void onListCursos() { log(curLog, "Listar no implementado en controlador"); }
    @FXML private void onClearCurso() { curId.clear(); curNombre.clear(); curDescripcion.clear(); curCreditos.clear(); curIdDocente.clear(); }

}
