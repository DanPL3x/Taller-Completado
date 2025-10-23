package com.ejercicio.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.ejercicio.modelo.estudiante;
import com.ejercicio.modelo.Database;
import java.sql.*;
import java.util.*;

public class estudianteFXController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtId;
    @FXML private TextField txtEdad;
    @FXML private TextArea txtSalida;

    @FXML
    private void agregarEstudiante() {
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        int id = Integer.parseInt(txtId.getText());
        int edad = Integer.parseInt(txtEdad.getText());

        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO estudiantes (id, nombre, correo, edad) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setString(3, correo);
            ps.setInt(4, edad);
            ps.executeUpdate();
            txtSalida.setText("Estudiante agregado correctamente");
        } catch (Exception e) {
            txtSalida.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void leerEstudiantes() {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM estudiantes";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            StringBuilder sb = new StringBuilder();

            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id"))
                  .append(", Nombre: ").append(rs.getString("nombre"))
                  .append(", Correo: ").append(rs.getString("correo"))
                  .append(", Edad: ").append(rs.getInt("edad"))
                  .append("\n");
            }

            txtSalida.setText(sb.toString());
        } catch (Exception e) {
            txtSalida.setText("Error: " + e.getMessage());
        }
    }
}
