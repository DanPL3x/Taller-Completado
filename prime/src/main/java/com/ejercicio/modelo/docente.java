package com.ejercicio.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class docente {
    private int id;
    private String nombre;
    private String correo;
    private String Especialidad;

    public docente(int id, String nombre, String correo, String Especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.Especialidad = Especialidad;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getEspecialidad() {
        return Especialidad;
    }   

    public void setEspecialidad(String especialidad) {
        this.Especialidad = especialidad;
    }

    
    public static void InsertarDocente(docente docente) {
    String sql = "INSERT INTO docente (ID, NOMBRE, ESPECIALIDAD, CORREO) VALUES (?, ?, ?, ?)";
    
    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, docente.getId());
        stmt.setString(2, docente.getNombre());
        stmt.setString(3, docente.getEspecialidad());
        stmt.setString(4, docente.getCorreo());

        stmt.executeUpdate();
    }

    catch (Exception ex) {
       ex.printStackTrace();
    }

    }

    public static docente ObtenerDocentePorId(int id) {
        String sql = "SELECT ID, NOMBRE, ESPECIALIDAD, CORREO FROM docente WHERE ID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    docente d = new docente(rs.getInt("ID"), rs.getString("NOMBRE"), rs.getString("ESPECIALIDAD"), rs.getString("CORREO"));
                    return d;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // actualizar docente (incluye especialidad)
    public static boolean ActualizarDocente(docente d) {
        String sql = "UPDATE docente SET NOMBRE = ?, ESPECIALIDAD = ?, CORREO = ? WHERE ID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getEspecialidad());
            ps.setString(3, d.getCorreo());
            ps.setInt(4, d.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // eliminar docente
    public static boolean EliminarDocentePorId(int id) {
        String sql = "DELETE FROM docente WHERE ID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    // listar todos los docentes
    public static java.util.List<docente> listarTodos() {
        java.util.List<docente> lista = new java.util.ArrayList<>();
        String sql = "SELECT ID, NOMBRE, ESPECIALIDAD, CORREO FROM docente";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                docente d = new docente(rs.getInt("ID"), rs.getString("NOMBRE"), rs.getString("ESPECIALIDAD"), rs.getString("CORREO"));
                lista.add(d);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

}