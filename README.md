# 📌 IDENTIFICACIÓN DE LA ACTIVIDAD: TableView

---

## 📖 Descripción

Aplicación JavaFX que gestiona datos de personas en una tabla con conexión a base de datos.

---

## 📂 Estructura

### 1. Código fuente

```
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
```

### 2. Bibliotecas adicionales

```
✅ JavaFX → Interfaz gráfica.
✅ JDBC → Conexión con base de datos.
✅ SLF4J + Logback → Logging.
```

---

## ⚠️ Solución de problemas

```
✅ Error de conexión a BD → Revisar `config.properties` (usuario, contraseña, URL).
✅ NullPointer en TableView → Verificar que FXML y Controlador estén vinculados correctamente.
✅ Revisar que el contenedor de mariaDb este en funcionamiento antes de ejecutar el jar.
```

---

## ⚙️ Requisitos de ejecución

```
✅ Lenguaje: Java 17+ (recomendado)
✅ IDE: IntelliJ IDEA / Eclipse / NetBeans
✅ Dependencias: Maven (JavaFX, JDBC, SLF4J)
✅ Base de datos: MySQL/MariaDB (estructura definida en PersonDAO)
```

---
## ⚙️ Configuración del archivo config.properties

Para que la aplicación pueda conectarse a la base de datos MariaDB, es necesario crear un archivo llamado config.properties en la raíz del proyecto (junto al pom.xml).

⚠️ Este archivo no se incluye en el repositorio por motivos de seguridad, ya que contiene credenciales de acceso a la base de datos.

🪜 Pasos para crearlo

1️⃣ Abre tu editor de texto (Notepad, VSCode, IntelliJ, etc.).
2️⃣ Crea un archivo nuevo y guárdalo con el nombre:

config.properties


En la carpeta resources de tu proyecto (C:\Users\TuUsuario\IdeaProjects\TableView\main\resources\config.properties).

3️⃣ Copia dentro el siguiente contenido:

# Configuración de conexión a la base de datos MariaDB

# Url con la conexion en mariaDb
db.url=jdbc:mariadb://localhost:3306/tableview_db

# Configura tu usuario aqui
db.user=USUARIO_AQUI

# Configura tu contraseña
db.password=CONTRASEÑA_AQUI

4️⃣ Guarda los cambios.

🧱 Comprobación

Puedes verificar que el archivo está correctamente creado si en el explorador de tu proyecto ves algo como:
```
TableView/
├─ pom.xml
├─ src/
├─ target/
├─ config.properties   ✅
└─ README.md
```
🧩 Nota importante

Este archivo no se sube a GitHub porque ya está incluido en él .gitignore.

Si otra persona quiere ejecutar el proyecto, deberá crear su propio config.properties siguiendo los pasos anteriores.

Si no existe, la aplicación mostrará un aviso indicando que no se ha encontrado el archivo de configuración.
---

## 🚀 Instalación y ejecución
```
✅ Paso 1: Configurar base de datos y credenciales en el archivo config.properties mencionado anteriormente.
✅ Paso 2: Lanzar docker con el contenedor mariaDb.
✅ Paso 3: Copiar y pegar el script de la carpeta resources/SQL/init.sql para crear la base de datos en el gestor que utilices.
✅ Paso 4: Usar mvn package para generar el jar y hacer dobleClick sobre èl.
```
---

## ✨ Autor/a


👤 Gaizka

