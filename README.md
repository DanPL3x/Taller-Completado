## TRABAJO PARCIAL 2

### Autores

- Daniel Campo
- Jeferson Beltrán

---

## Resumen del trabajo realizado

Este repositorio contiene una aplicación Java (Maven) con soporte para CRUD (Crear, Leer, Actualizar, Eliminar) sobre tres entidades: Estudiante, Docente y Curso. La aplicación usa JDBC/MySQL para persistencia y ofrece dos superficies de interacción:

- Interfaz de consola (CLI) via `Main.java`.
- Interfaz gráfica JavaFX (FXML) via `MainFX` y `src/main/resources/vista/appView.fxml`.

Principales cambios y archivos modificados/creados:

- Modelos (DAO-like, usan `Database.getConnection()`):
	- `src/main/java/com/ejercicio/modelo/estudiante.java` — métodos: InsertarEstudiante, ObtenerEstudiantePorId, ActualizarEstudiante, EliminarEstudiantePorId, listarTodos
	- `src/main/java/com/ejercicio/modelo/docente.java` — métodos: InsertarDocente, ObtenerDocentePorId, ActualizarDocente, EliminarDocentePorId, listarTodos
	- `src/main/java/com/ejercicio/modelo/curso.java` — métodos: InsertarCurso (retorna boolean), ObtenerCursoPorId, ActualizarCurso, EliminarCursoPorId, listarTodos
	- `src/main/java/com/ejercicio/modelo/Database.java` — proveedor de conexión JDBC; busca `prime/config.properties` y `config.properties`, imprime diagnósticos y recrea la conexión si está cerrada.

- Controladores (wrappers entre vista y modelo):
	- `src/main/java/com/ejercicio/controlador/estudianteControlador.java` — agregar/leer/actualizar/eliminar + `listar()` wrapper
	- `src/main/java/com/ejercicio/controlador/docenteControlador.java` — agregar/leer/actualizar/eliminar + `listar()` wrapper
	- `src/main/java/com/ejercicio/controlador/cursoControlador.java` — agregar/leer/actualizar/eliminar + `listar()` wrapper (inserta en DB antes de añadir a lista en memoria)

- Vistas y UI:
	- `src/main/java/com/ejercicio/Main.java` — CLI con selector de rol (Estudiante, Docente, Curso) y submenús CRUD.
	- `src/main/resources/vista/appView.fxml` — JavaFX FXML con pestañas para Estudiante/Docente/Curso (formularios CRUD).
	- `src/main/java/com/ejercicio/controlador/AppFXController.java` — controlador JavaFX que delega en los controladores de dominio.

---

## Requisitos

- Java 17 (el proyecto actualmente compila con target 17 en `pom.xml`).
- Maven 3.x
- MySQL (o compatible) corriendo localmente o accesible remotamente.
- Dependencias (maven se encarga de descargarlas): MySQL Connector/J, JavaFX (17.0.2).

## Configuración de la base de datos

El archivo de configuración buscado por `Database.getConnection()` es `prime/config.properties` (en el root del módulo) o `config.properties` en el working directory o en classpath. El formato esperado es:

```
URL=jdbc:mysql://localhost:3306/parcial2
USERNAME=root
PASSWORD=123456789
```

- Ajusta la URL/usuario/contraseña de acuerdo con tu entorno.
- Asegúrate de que la base de datos `parcial2` y las tablas `estudiante`, `docente` y `curso` existan con las columnas consumidas por los métodos. Ejemplo SQL (simplificado):

```sql
CREATE TABLE estudiante (
	ID INT PRIMARY KEY,
	NOMBRE VARCHAR(255),
	CORREO VARCHAR(255),
	EDAD INT
);

CREATE TABLE docente (
	ID INT PRIMARY KEY,
	NOMBRE VARCHAR(255),
	CORREO VARCHAR(255),
	ESPECIALIDAD VARCHAR(255)
);

CREATE TABLE curso (
	ID INT PRIMARY KEY,
	NOMBRE VARCHAR(255),
	DESCRIPCION TEXT,
	CREDITOS INT,
	ID_DOCENTE INT
);
```

Nota sobre integridad: no hay constraints automáticas añadidas por el código; puedes agregar foreign keys entre `curso.ID_DOCENTE` y `docente.ID` según prefieras.

## Cómo compilar

Desde PowerShell (en Windows) en la carpeta del módulo `prime`:

```powershell
cd c:\Users\Daniel\Documents\Programacion\proyecto-MC\prime
mvn -DskipTests package
```

## Cómo ejecutar

- Ejecutar la versión consola (CLI):

```powershell
cd c:\Users\Daniel\Documents\Programacion\proyecto-MC\prime
mvn -DskipTests exec:java -Dexec.mainClass="com.ejercicio.Main"
```

- Ejecutar la versión JavaFX (interfaz gráfica):

```powershell
cd c:\Users\Daniel\Documents\Programacion\proyecto-MC\prime
mvn -DskipTests javafx:run
```

El plugin JavaFX en el `pom.xml` está configurado con `MainFX` como `mainClass`.

## Diagnóstico y troubleshooting

- Si ves errores relacionados con versiones de clases (class file has wrong version) durante compilación, asegúrate de que la versión de Java que usas para compilar sea Java 17 (javac -version). El proyecto está configurado para target Java 17.
- Si las operaciones CRUD no persisten o fallan en tiempo de ejecución, revisa:
	- El contenido de `prime/config.properties` (URL, USERNAME, PASSWORD).
	- Que el servidor MySQL esté en ejecución y que el usuario tenga permisos sobre la base `parcial2`.
	- Mensajes y stacktraces en la consola: `Database.getConnection()` imprime diagnósticos que ayudan a localizar problemas de conexión. Las inserciones devuelven booleanos y los errores se imprimen en stacktrace.
- En particular, para el problema reportado: "la creación de curso no guarda en BD", el método `curso.InsertarCurso` devuelve booleano y el controlador `cursoControlador.agregaCursos` comprueba ese booleano; revisa la consola para ver el motivo exacto (por ejemplo clave primaria duplicada, credenciales incorrectas o conexión nula).


