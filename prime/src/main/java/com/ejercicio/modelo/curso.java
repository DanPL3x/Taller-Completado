package com.ejercicio.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class curso {

    private int id;
    private String nombre;
    private String descripcion;
    private int creditos;
    private int id_docente;

    public curso(int id, String nombre, String descripcion, int creditos, int id_docente) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
        this.id_docente = id_docente;  
    }

    public int getCreditos() {
        return creditos;
    }   
        public int getId_docente() {
            return id_docente;
        }  
        public void setId_docente(int id_docente) {
            this.id_docente = id_docente;
        }
    public void setCreditos(int creditos) {
        this.creditos = creditos;
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
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public static boolean InsertarCurso(curso curso) {
        String sql = "INSERT INTO curso (ID, NOMBRE, DESCRIPCION, CREDITOS, ID_DOCENTE) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = Database.getConnection();
            if (conn == null) {
                System.out.println("InsertarCurso: Database.getConnection() devolvió null");
                return false;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, curso.getId());
                stmt.setString(2, curso.getNombre());
                stmt.setString(3, curso.getDescripcion());
                stmt.setInt(4, curso.getCreditos());
                stmt.setInt(5, curso.getId_docente());
                int filas = stmt.executeUpdate();
                return filas > 0;
            }
        } catch (Exception ex) {
            System.out.println("Error InsertarCurso: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
    public static curso ObtenerCursoPorId(int id) {
        String sql = "SELECT ID, NOMBRE, DESCRIPCION, CREDITOS, ID_DOCENTE FROM curso WHERE ID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    curso c = new curso(rs.getInt("ID"), rs.getString("NOMBRE"), rs.getString("DESCRIPCION"), rs.getInt("CREDITOS"), rs.getInt("ID_DOCENTE"));
                    return c;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // actualizar curso
    public static boolean ActualizarCurso(curso c) {
        String sql = "UPDATE curso SET NOMBRE = ?, DESCRIPCION = ?, CREDITOS = ?, ID_DOCENTE = ? WHERE ID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setInt(3, c.getCreditos());
            ps.setInt(4, c.getId_docente());
            ps.setInt(5, c.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // eliminar curso
    public static boolean EliminarCursoPorId(int id) {
        String sql = "DELETE FROM curso WHERE ID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // listar todos los cursos
    public static java.util.List<curso> listarTodos() {
        java.util.List<curso> lista = new java.util.ArrayList<>();
        String sql = "SELECT ID, NOMBRE, DESCRIPCION, CREDITOS, ID_DOCENTE FROM curso";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                curso c = new curso(rs.getInt("ID"), rs.getString("NOMBRE"), rs.getString("DESCRIPCION"), rs.getInt("CREDITOS"), rs.getInt("ID_DOCENTE"));
                lista.add(c);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

}