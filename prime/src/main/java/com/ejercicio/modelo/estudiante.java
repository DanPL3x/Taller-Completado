package com.ejercicio.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class estudiante {
    private int id;
    private int edad;
    private String nombre;
    private String correo;
    public estudiante() {
    }

    public estudiante(int id, String nombre, String correo, int edad) {
        this.id = id; 
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
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

    //insertar estudiante

    public static void InsertarEstudiante(estudiante estudiante) {
    String sql = "INSERT INTO estudiante (ID, NOMBRE, CORREO, EDAD) VALUES (?, ?, ?, ?)";
    
    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, estudiante.getId());
        stmt.setString(2, estudiante.getNombre());
        stmt.setString(3, estudiante.getCorreo());
        stmt.setInt(4, estudiante.getEdad());
        stmt.executeUpdate();
    }

    catch (Exception ex) {
       ex.printStackTrace();
    }


    // buscar estudiante por id

}
    public static estudiante ObtenerEstudiantePorId(int id) {
        String sql = "SELECT ID, NOMBRE, CORREO, EDAD FROM estudiante WHERE ID = ?";
        Connection conn = null;
        try {
            conn = Database.getConnection();
            if (conn == null) {
                System.out.println("getConnection devolvió null");
                return null;
            }
            System.out.println("Usando conexión: " + conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        estudiante e = new estudiante();
                        e.setId(rs.getInt("ID"));
                        e.setNombre(rs.getString("NOMBRE"));
                        e.setCorreo(rs.getString("CORREO"));
                        e.setEdad(rs.getInt("EDAD"));
                        return e;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error en ObtenerEstudiantePorId: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    // actualizar estudiante
    public static boolean ActualizarEstudiante(estudiante estudiante) {
        String sql = "UPDATE estudiante SET NOMBRE = ?, CORREO = ?, EDAD = ? WHERE ID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getCorreo());
            ps.setInt(3, estudiante.getEdad());
            ps.setInt(4, estudiante.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // eliminar estudiante
    public static boolean EliminarEstudiantePorId(int id) {
        String sql = "DELETE FROM estudiante WHERE ID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // listar todos
    public static java.util.List<estudiante> listarTodos() {
        java.util.List<estudiante> lista = new java.util.ArrayList<>();
        String sql = "SELECT ID, NOMBRE, CORREO, EDAD FROM estudiante";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                estudiante e = new estudiante();
                e.setId(rs.getInt("ID"));
                e.setNombre(rs.getString("NOMBRE"));
                e.setCorreo(rs.getString("CORREO"));
                e.setEdad(rs.getInt("EDAD"));
                lista.add(e);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

}