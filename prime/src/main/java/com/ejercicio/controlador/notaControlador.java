package com.ejercicio.controlador;

import com.ejercicio.modelo.nota;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public class notaControlador {

    @FXML
    private TextField txtIdNota;

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

    // Handlers públicos llamados desde FXML (delegan a la lógica ya existente)

    @FXML
    public void onCreateNota() {
        agregarNota();
    }

    @FXML
    public void onListNotas() {
        cargarNotas();
        mostrarAlerta("Éxito", "Listado actualizado.");
    }

    @FXML
    public void onClearNota() {
        limpiarCampos();
    }

    @FXML
    public void onDeleteNota() {
        eliminarNota();
    }

    @FXML
    public void onUpdateNota() {
        actualizarNota();
    }

    @FXML
    public void onReadNota() {
        // Intentar leer por el id ingresado, si no hay id usar la selección de la tabla
        String idText = (txtIdNota != null) ? txtIdNota.getText() : "";
        if (idText != null && !idText.isBlank()) {
            try {
                int id = Integer.parseInt(idText);
                if (listaNotas == null) cargarNotas();
                Optional<nota> opt = listaNotas.stream().filter(n -> n.getIdNota() == id).findFirst();
                if (opt.isPresent()) {
                    nota n = opt.get();
                    // poblar campos
                    txtIdEstudiante.setText(String.valueOf(n.getIdEstudiante()));
                    txtIdCurso.setText(String.valueOf(n.getIdCurso()));
                    txtValor.setText(String.valueOf(n.getValor()));
                    dpFecha.setValue(n.getFecha() != null ? n.getFecha().toLocalDate() : LocalDate.now());
                    mostrarAlerta("Éxito", "Nota cargada.");
                } else {
                    mostrarAlerta("Info", "No se encontró la nota con id " + id);
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Error", "ID inválido: " + ex.getMessage());
            }
        } else {
            // usar selección de la tabla
            nota seleccionada = tablaNotas.getSelectionModel().getSelectedItem();
            if (seleccionada != null) {
                txtIdNota.setText(String.valueOf(seleccionada.getIdNota()));
                txtIdEstudiante.setText(String.valueOf(seleccionada.getIdEstudiante()));
                txtIdCurso.setText(String.valueOf(seleccionada.getIdCurso()));
                txtValor.setText(String.valueOf(seleccionada.getValor()));
                dpFecha.setValue(seleccionada.getFecha() != null ? seleccionada.getFecha().toLocalDate() : LocalDate.now());
            } else {
                mostrarAlerta("Advertencia", "Ingrese un ID o seleccione una nota en la tabla.");
            }
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
            mostrarAlerta("Éxito", "Nota agregada correctamente.");
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
                mostrarAlerta("Éxito", "Nota eliminada correctamente.");
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
                mostrarAlerta("Éxito", "Nota actualizada correctamente.");
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo actualizar la nota: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione una nota para actualizar.");
        }
    }

    private void limpiarCampos() {
        txtIdNota.clear();
        txtIdEstudiante.clear();
        txtIdCurso.clear();
        txtValor.clear();
        dpFecha.setValue(null);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert.AlertType type = "Error".equalsIgnoreCase(titulo) ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION;
        Alert alerta = new Alert(type);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}


