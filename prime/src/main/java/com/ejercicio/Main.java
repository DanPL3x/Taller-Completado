package com.ejercicio;

import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.ejercicio.controlador.*;
import com.ejercicio.vista.*;
import com.ejercicio.modelo.*;

public class Main extends Application {

    public static void main(String[] args) {
        // If user passed 'cli' as first argument, run CLI mode
        if (args != null && args.length > 0 && "cli".equalsIgnoreCase(args[0])) {
            runCLI();
            return;
        }

        // otherwise, launch JavaFX
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML view (appView.fxml) from resources
        Parent root = FXMLLoader.load(getClass().getResource("/vista/appView.fxml"));
        primaryStage.setTitle("Aplicación - CRUD Estudiantes/Docentes/Cursos");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    // Keep the original CLI menu as a reusable method
    public static void runCLI() {
        // Connection is established inside model methods when needed via Database.getConnection()

        Scanner sc = new Scanner(System.in);
        estudianteVista vista = new estudianteVista();
        estudianteControlador controlador = new estudianteControlador(vista);

        boolean salir = false;
        while (!salir) {
            System.out.println("Seleccione rol: 1=Estudiante, 2=Docente, 3=Curso, 0=Salir");
            int rol = sc.nextInt();
            switch (rol) {
                // ESTUDIANTE -------------------------------------
                case 1:
                    boolean volverE = false;
                    while (!volverE) {
                        System.out.println("Estudiante: 1=Crear, 2=Leer por id, 3=Actualizar, 4=Eliminar, 9=Volver");
                        int op = sc.nextInt();
                        switch (op) {
                            case 1:
                                System.out.println("Ingrese el id del estudiante: ");
                                int id = sc.nextInt();
                                sc.nextLine();
                                System.out.println("Ingrese el nombre del estudiante: ");
                                String nombre = sc.nextLine();
                                System.out.println("Ingrese el correo del estudiante: ");
                                String correo = sc.next();
                                System.out.println("Ingrese la edad del estudiante: ");
                                int edad = sc.nextInt();
                                estudiante nuevoEstudiante = new estudiante(id, nombre, correo, edad);
                                controlador.agregarEstudiante(nuevoEstudiante);
                                controlador.actulizarVista();
                                break;
                            case 2:
                                try {
                                    System.out.println("Ingrese el id a leer: ");
                                    int qid = sc.nextInt();
                                    estudiante encontrado = controlador.obtenerEstudiantePorId(qid);
                                    if (encontrado == null) System.out.println("No encontrado");
                                    else {
                                        System.out.println("Id: " + encontrado.getId());
                                        System.out.println("Nombre: " + encontrado.getNombre());
                                        System.out.println("Correo: " + encontrado.getCorreo());
                                        System.out.println("Edad: " + encontrado.getEdad());
                                    }
                                } catch (Exception ex) {
                                    System.out.println("Error leyendo estudiante: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                                break;
                            case 3:
                                System.out.println("Ingrese id del estudiante a actualizar: ");
                                int aid = sc.nextInt();
                                estudiante exist = controlador.obtenerEstudiantePorId(aid);
                                if (exist == null) { System.out.println("No existe"); break; }
                                System.out.println("Nuevo nombre:"); exist.setNombre(sc.next());
                                System.out.println("Nuevo correo:"); exist.setCorreo(sc.next());
                                System.out.println("Nueva edad:"); exist.setEdad(sc.nextInt());
                                if (controlador.actualizarEstudiante(exist)) System.out.println("Actualizado"); else System.out.println("No actualizado");
                                break;
                            case 4:
                                System.out.println("Ingrese id del estudiante a eliminar:");
                                int eid = sc.nextInt();
                                if (controlador.eliminarEstudiante(eid)) System.out.println("Eliminado"); else System.out.println("No eliminado");
                                break;
                            case 9:
                                volverE = true; break;
                            default:
                                System.out.println("Opción no válida");
                        }
                    }
                    break;
                // DOCENTE -------------------------------------
                case 2:
                    docenteVista dVista = new docenteVista();
                    docenteControlador dCtrl = new docenteControlador(dVista);
                    boolean volverD = false;
                    while (!volverD) {
                        System.out.println("Docente: 1=Crear, 2=Leer por id, 3=Actualizar, 4=Eliminar, 9=Volver");
                        int op = sc.nextInt();
                        switch (op) {
                            case 1:
                                System.out.println("Ingrese id docente:");
                                int did = sc.nextInt();
                                sc.nextLine();
                                System.out.println("Ingrese nombre docente:");
                                String dnombre = sc.nextLine();
                                System.out.println("Ingrese correo docente:");
                                String dcorreo = sc.next();
                                System.out.println("Ingrese la especialidad:");
                                String despecialidad = sc.next();
                                com.ejercicio.modelo.docente nd = new com.ejercicio.modelo.docente(did, dnombre, dcorreo, despecialidad);
                                dCtrl.agregarDocentes(nd);
                                dCtrl.actulizarVista();
                                break;
                            case 2:
                                try {
                                    System.out.println("Ingrese id docente a leer:");
                                    int qdid = sc.nextInt();
                                    com.ejercicio.modelo.docente doc = dCtrl.obtenerDocentePorId(qdid);
                                    if (doc == null) System.out.println("No encontrado");
                                    else System.out.println("Docente: " + doc.getId() + " - " + doc.getNombre() + " - " + doc.getCorreo() + " - " + doc.getEspecialidad());
                                } catch (Exception ex) {
                                    System.out.println("Error leyendo docente: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                                break;
                            case 3:
                                System.out.println("Ingrese id docente a actualizar:");
                                int adid = sc.nextInt();
                                com.ejercicio.modelo.docente dExist = dCtrl.obtenerDocentePorId(adid);
                                if (dExist == null) { System.out.println("No existe"); break; }
                                System.out.println("Nuevo nombre:"); dExist.setNombre(sc.next());
                                System.out.println("Nueva especialidad:"); dExist.setEspecialidad(sc.next());
                                System.out.println("Nuevo correo:"); dExist.setCorreo(sc.next());
                                if (dCtrl.actualizarDocente(dExist)) System.out.println("Actualizado"); else System.out.println("No actualizado");
                                break;
                            case 4:
                                System.out.println("Ingrese id docente a eliminar:");
                                int ddid = sc.nextInt();
                                if (dCtrl.eliminarDocente(ddid)) System.out.println("Eliminado"); else System.out.println("No eliminado");
                                break;
                            case 9:
                                volverD = true; break;
                            default:
                                System.out.println("Opción no válida");
                        }
                    }
                    break;
                // CURSO -------------------------------------
                case 3:
                    cursoVista cVista = new cursoVista();
                    cursoControlador cCtrl = new cursoControlador(cVista);
                    boolean volverC = false;
                    while (!volverC) {
                        System.out.println("Curso: 1=Crear, 2=Leer por id, 3=Actualizar, 4=Eliminar, 9=Volver");
                        int op = sc.nextInt();
                        switch (op) {
                            case 1:
                                System.out.println("Ingrese id curso:");
                                int cid = sc.nextInt();
                                sc.nextLine();
                                System.out.println("Ingrese nombre curso:");
                                String cnombre = sc.nextLine();
                                System.out.println("Ingrese descripcion curso:");
                                String cdesc = sc.nextLine();
                                System.out.println("Ingrese creitos curso:");
                                int ccreditos = sc.nextInt();
                                System.out.println("Ingrese id docente asignado:");
                                int cid_docente = sc.nextInt();
                                com.ejercicio.modelo.curso nc = new com.ejercicio.modelo.curso(cid, cnombre, cdesc, ccreditos, cid_docente);
                                cCtrl.agregaCursos(nc);
                                cCtrl.actulizarVista();
                                break;
                            case 2:
                                try {
                                    System.out.println("Ingrese id curso a leer:");
                                    int qcid = sc.nextInt();
                                    com.ejercicio.modelo.curso curso = cCtrl.obtenerCursoPorId(qcid);
                                    if (curso == null) System.out.println("No encontrado");
                                    else System.out.println("Curso: " + curso.getId() + " - " + curso.getNombre() + " - " + curso.getDescripcion() + " - " + curso.getCreditos() + " - " + curso.getId_docente());
                                } catch (Exception ex) {
                                    System.out.println("Error leyendo curso: " + ex.getMessage());
                                    ex.printStackTrace();
                                }
                                break;
                            case 3:
                                System.out.println("Ingrese id curso a actualizar:");
                                int acid = sc.nextInt();
                                sc.nextLine();
                                com.ejercicio.modelo.curso cExist = cCtrl.obtenerCursoPorId(acid);
                                if (cExist == null) { System.out.println("No existe"); break; }
                                System.out.println("Nuevo nombre:"); cExist.setNombre(sc.nextLine());
                                System.out.println("Nueva descripcion:"); cExist.setDescripcion(sc.nextLine());
                                System.out.println("Nuevos creditos:"); cExist.setCreditos(sc.nextInt());
                                System.out.println("Nuevo id docente:"); cExist.setId_docente(sc.nextInt());
                                if (cCtrl.actualizarCurso(cExist)) System.out.println("Actualizado"); else System.out.println("No actualizado");
                                break;
                            case 4:
                                System.out.println("Ingrese id curso a eliminar:");
                                int dcid = sc.nextInt();
                                if (cCtrl.eliminarCurso(dcid)) System.out.println("Eliminado"); else System.out.println("No eliminado");
                                break;
                            case 9:
                                volverC = true; break;
                            default:
                                System.out.println("Opción no válida");
                        }
                    }
                    break;
                case 0:
                    salir = true; break;
                default:
                    System.out.println("Rol no válido");
            }
        }
        sc.close();
    }
}