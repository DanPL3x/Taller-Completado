package com.ejercicio.modelo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Statement;


public class nota {
    private int idNota;
    private int idEstudiante;
    private int idCurso;
    private double valor;
    private Date fecha;

    public nota() {}

    public nota(int idNota, int idEstudiante, int idCurso, double valor, Date fecha) {
        this.idNota = idNota;
        this.idEstudiante = idEstudiante;
        this.idCurso = idCurso;
        this.valor = valor;
        this.fecha = fecha;
    }

    // Getters y setters
    public int getIdNota() { return idNota; }
    public void setIdNota(int idNota) { this.idNota = idNota; }

    public int getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(int idEstudiante) { this.idEstudiante = idEstudiante; }

    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }


    public void agregarNota(nota nota) throws SQLException {
        String sql = "INSERT INTO nota (id_estudiante, id_curso, valor, fecha) VALUES (?, ?, ?, ?)";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nota.getIdEstudiante());
            ps.setInt(2, nota.getIdCurso());
            ps.setDouble(3, nota.getValor());
            ps.setDate(4, nota.getFecha());

            ps.executeUpdate();
        }
    }

    public List<nota> listarNotas() throws SQLException {
        List<nota> lista = new ArrayList<>();
        String sql = "SELECT * FROM nota";
        try (Connection con = Database.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                nota n = new nota();
                n.setIdNota(rs.getInt("id_nota"));
                n.setIdEstudiante(rs.getInt("id_estudiante"));
                n.setIdCurso(rs.getInt("id_curso"));
                n.setValor(rs.getDouble("valor"));
                n.setFecha(rs.getDate("fecha"));
                lista.add(n);
            }
        }
        return lista;
    }

    public void actualizarNota(nota nota) throws SQLException {
        String sql = "UPDATE nota SET id_estudiante=?, id_curso=?, valor=?, fecha=? WHERE id_nota=?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nota.getIdEstudiante());
            ps.setInt(2, nota.getIdCurso());
            ps.setDouble(3, nota.getValor());
            ps.setDate(4, nota.getFecha());
            ps.setInt(5, nota.getIdNota());

            ps.executeUpdate();
        }
    }

    public void eliminarNota(int idNota) throws SQLException {
        String sql = "DELETE FROM nota WHERE id_nota=?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idNota);
            ps.executeUpdate();
        }
    }
}