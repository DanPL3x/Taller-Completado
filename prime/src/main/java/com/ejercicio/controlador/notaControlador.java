
package com.ejercicio.controlador;

import com.ejercicio.modelo.nota;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Date;
import java.time.LocalDate;

public class notaControlador {

    @FXML
    private TextField txtIdEstudiante;

    @FXML
    private TextField txtIdCurso;

    @FXML
    private TextField txtValor;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TableView<nota> tablaNotas;

    @FXML
    private TableColumn<nota, Integer> colIdNota;

    @FXML
    private TableColumn<nota, Integer> colIdEstudiante;

    @FXML
    private TableColumn<nota, Integer> colIdCurso;

    @FXML
    private TableColumn<nota, Double> colValor;

    @FXML
    private TableColumn<nota, Date> colFecha;

    private ObservableList<nota> listaNotas;
    private nota nota = new nota();

    // 🚀 Se ejecuta al iniciar la vista
    @FXML
    public void initialize() {
        configurarColumnas();
        cargarNotas();
    }

    private void configurarColumnas() {
        colIdNota.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdNota()).asObject());
        colIdEstudiante.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdEstudiante()).asObject());
        colIdCurso.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdCurso()).asObject());
        colValor.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty(data.getValue().getValor()).asObject());
        colFecha.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getFecha()));
    }

    private void cargarNotas() {
        try {
            listaNotas = FXCollections.observableArrayList(nota.listarNotas());
            tablaNotas.setItems(listaNotas);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar las notas: " + e.getMessage());
        }
    }

    @FXML
    private void agregarNota() {
        try {
            nota n = new nota();
            n.setIdEstudiante(Integer.parseInt(txtIdEstudiante.getText()));
            n.setIdCurso(Integer.parseInt(txtIdCurso.getText()));
            n.setValor(Double.parseDouble(txtValor.getText()));
            n.setFecha(Date.valueOf(dpFecha.getValue() != null ? dpFecha.getValue() : LocalDate.now()));

            nota.agregarNota(n);
            limpiarCampos();
            cargarNotas();
            mostrarAlerta("Éxito", "nota agregada correctamente.");
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo agregar la nota: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarNota() {
        nota seleccionada = tablaNotas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            try {
                nota.eliminarNota(seleccionada.getIdNota());
                cargarNotas();
                mostrarAlerta("Éxito", "nota eliminada correctamente.");
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo eliminar la nota: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione una nota para eliminar.");
        }
    }

    @FXML
    private void actualizarNota() {
        nota seleccionada = tablaNotas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            try {
                seleccionada.setIdEstudiante(Integer.parseInt(txtIdEstudiante.getText()));
                seleccionada.setIdCurso(Integer.parseInt(txtIdCurso.getText()));
                seleccionada.setValor(Double.parseDouble(txtValor.getText()));
                seleccionada.setFecha(Date.valueOf(dpFecha.getValue()));

                nota.actualizarNota(seleccionada);
                cargarNotas();
                mostrarAlerta("Éxito", "nota actualizada correctamente.");
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo actualizar la nota: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione una nota para actualizar.");
        }
    }

    private void limpiarCampos() {
        txtIdEstudiante.clear();
        txtIdCurso.clear();
        txtValor.clear();
        dpFecha.setValue(null);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

    
