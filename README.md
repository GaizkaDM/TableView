# 📌 IDENTIFICACIÓN DEL PROYECTO: Gestión de Personas en JavaFX

---

## 📖 Descripción
Aplicación de escritorio en **JavaFX** que permite gestionar una lista de personas en una tabla interactiva.  
Se pueden **añadir, eliminar y restaurar** personas con validación de datos, registro de acciones en un log y generación de documentación con Javadoc.

---

## 📂 Estructura

src/main/java/com/gaizkaFrost/
App.java
Lanzador.java
controlador/ControladorVentana.java
modelos/Person.java

src/main/resources/com/gaizkaFrost/
fxml/tableView.fxml
css/styles.css

### 2. Bibliotecas adicionales
- `SLF4J` para logging  
- `JavaFX` para la interfaz gráfica  

No se han utilizado más bibliotecas externas.

---

## ⚠️ Solución de problemas

- Problemas con el log que no se creaba: se debe configurar correctamente SLF4J y usar `LoggerFactory`.  
- Para que el Javadoc se genere correctamente en IntelliJ, usar `Tools > Generate JavaDoc` y asegurarse de incluir todas las clases del proyecto.  
- Validaciones de persona (nombre, apellido, fecha) deben capturar errores con `Alert` para que no se agreguen datos inválidos.

---

## ⚙️ Requisitos de ejecución

✅ Lenguaje: Java 17+  
✅ IDE o compilador utilizado: IntelliJ IDEA  
✅ Sistema operativo probado: Windows  
✅ Librerías: JavaFX, SLF4J

---

## 🚀 Instalación y ejecución

## 🚀 Instalación y ejecución

1. Clonar el repositorio:  
git clone <URL_DEL_REPOSITORIO>
cd <NOMBRE_PROYECTO>
Abrir el proyecto en IntelliJ IDEA o tu IDE favorito.

Compilar y ejecutar la clase Lanzador.java o App.java desde el IDE.

O bien, generar un JAR ejecutable usando Maven:

(bash)
mvn clean package
Esto creará un archivo JAR dentro de la carpeta target/.

Se puede ejecutar directamente haciendo doble click sobre el jar

Interactuar con la interfaz:

Añadir personas: se valida que el nombre y apellido solo tengan letras y la fecha sea válida.

Eliminar personas seleccionadas.

Restaurar la lista original.

Para ver el registro de acciones, revisar el archivo app.log generado en la carpeta target/logs.

✨ Autor/a
👤 Gaizka Rodriguez
