package com.ejercicio.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



public class Database {

    private static Connection connection;

  public static Connection getConnection() {

    System.out.println("Intentando conectar a la base de datos...");

    try {
      if (connection == null || connection.isClosed()) {
        connection = null; // force recreation
      }
    } catch (SQLException e) {
      // if we can't check, force recreation
      connection = null;
    }

    if (connection == null) {

      Properties properties = new Properties();

      try {
        // Cargar el driver JDBC
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Intentar cargar config desde varias rutas
        File f1 = new File("prime/config.properties");
        File f2 = new File("config.properties");
        if (f1.exists()) {
          System.out.println("Cargando configuración desde: " + f1.getAbsolutePath());
          properties.load(new FileInputStream(f1));
        } else if (f2.exists()) {
          System.out.println("Cargando configuración desde: " + f2.getAbsolutePath());
          properties.load(new FileInputStream(f2));
        } else {
          System.out.println("No se encontró config en disco; intentando classpath '/config.properties'");
          java.io.InputStream is = Database.class.getClassLoader().getResourceAsStream("config.properties");
          if (is != null) {
            properties.load(is);
          } else {
            throw new FileNotFoundException("No se halló prime/config.properties ni config.properties ni /config.properties en classpath");
          }
        }

        // Configurar los parámetros de conexión
        String url = properties.get("URL").toString();
        String username = properties.get("USERNAME").toString();
        String password = properties.get("PASSWORD").toString();

        System.out.println("Intentando conectar a: " + url + " con usuario " + username);

        // Establecer la conexión
        connection = DriverManager.getConnection(url, username, password);

        if (connection != null && !connection.isClosed()) {
          System.out.println("Conexión exitosa a la base de datos.");
        } else {
          System.out.println("Driver devolvió conexión nula o cerrada.");
        }

      } catch (ClassNotFoundException e) {
        System.out.println("Error al cargar el driver JDBC: " + e.getMessage());
        e.printStackTrace();
      } catch (SQLException e) {
        System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        e.printStackTrace();
      } catch (FileNotFoundException e) {
        System.out.println("Archivo de configuración no encontrado: " + e.getMessage());
        e.printStackTrace();
      } catch (IOException e) {
        System.out.println("Error leyendo archivo de configuración: " + e.getMessage());
        e.printStackTrace();
      }
    }
    return connection;
  }

  public static void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
        System.out.println("Conexión cerrada.");
      } catch (SQLException e) {
        System.out.println("Error al cerrar la conexión: " + e.getMessage());
      }
    }
  }
}
  // Crear estudiante en la base de datos

//   public boolean crearEstudiante(estudiante estudiante) {
//     String sql = "INSERT INTO estudiantes (ID, NOMBRE, CORREO, EDAD) VALUES (?, ?, ?)";
    
//     try (Connection conn = getConnection();
//          PreparedStatement stmt = conn.prepareStatement(sql)) {
        
//         stmt.setInt(1, estudiante.getId());
//         stmt.setString(2, estudiante.getNombre());
//         stmt.setInt(3, estudiante.getEdad());
//         stmt.setString(4, estudiante.getCorreo());

        
//         int filas = stmt.executeUpdate();
//         return filas > 0;
        
//     } catch (SQLException e) {
//         System.out.println("Error al insertar estudiante: " + e.getMessage());
//         return false;
//     }
// }

// }
