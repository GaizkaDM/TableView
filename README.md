# 📌 IDENTIFICACIÓN DE LA ACTIVIDAD: TableView

---

## 📖 Descripción

Aplicación JavaFX que gestiona datos de personas en una tabla con conexión a base de datos.

---

## 📂 Estructura

### 1. Código fuente


📁 /src/main/java
    ✅ App.java → Clase principal, inicializa JavaFX y carga la ventana.
    ✅ Lanzador.java → Encargado de ejecutar la aplicación (entrypoint alternativo).
    ✅ Config.java → Maneja la configuración cargada desde `config.properties`.
    ✅ ConexionBD.java → Gestiona la conexión a la base de datos (MySQL/MariaDB).
    ✅ ControladorVentana.java → Controlador del FXML, gestiona eventos de la interfaz.
    ✅ DAO/PersonDAO.java → Acceso a datos, CRUD sobre la entidad Person.
    ✅ modelos/Person.java → Modelo de datos, representa a una persona.

📁 /src/main/resources
    ✅ tableView.fxml → Vista en FXML con TableView para mostrar personas.
    ✅ css/styles.css → Estilos CSS para la interfaz.
    ✅ logback.xml → Configuración de logging.


### 2. Bibliotecas adicionales


✅ JavaFX → Interfaz gráfica.
✅ JDBC → Conexión con base de datos.
✅ SLF4J + Logback → Logging.


---

## ⚠️ Solución de problemas


✅ Error de conexión a BD → Revisar `config.properties` (usuario, contraseña, URL).
✅ NullPointer en TableView → Verificar que FXML y Controlador estén vinculados correctamente.
✅ Revisar que el contenedor de mariaDb este en funcionamiento antes de ejecutar el jar.
---

## ⚙️ Requisitos de ejecución


✅ Lenguaje: Java 17+ (recomendado)
✅ IDE: IntelliJ IDEA / Eclipse / NetBeans
✅ Dependencias: Maven (JavaFX, JDBC, SLF4J)
✅ Base de datos: MySQL/MariaDB (estructura definida en PersonDAO)


---

## 🚀 Instalación y ejecución

✅ Paso 1: Configurar base de datos y credenciales creando el archivo config.properties en la carpeta resources.
✅ Paso 2: Lanzar docker con el contenedor mariaDb.
✅ Paso 3: Usar el script de la carpeta resources/SQL/init.sql para crear la base de datos.
✅ Paso 4: Usar mvn package para generar el jar y hacer dobleClick sobre èl.

---

## ✨ Autor/a


👤 Gaizka

