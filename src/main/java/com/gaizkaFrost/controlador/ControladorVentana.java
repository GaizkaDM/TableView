package com.gaizkaFrost.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.gaizkaFrost.modelos.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * Controlador principal de la ventana de la aplicación JavaFX que gestiona
 * la interacción entre la interfaz gráfica (campos de texto, tabla y botones)
 * y la lógica de negocio relacionada con la clase {@link Person}.
 *
 * <p>Este controlador permite:</p>
 * <ul>
 *   <li>Inicializar la tabla con una lista de personas predefinidas.</li>
 *   <li>Añadir nuevas personas a la lista.</li>
 *   <li>Eliminar personas seleccionadas.</li>
 *   <li>Restaurar la lista original desde una copia de seguridad.</li>
 * </ul>
 *
 * <p>Los métodos marcados con {@code @FXML} están enlazados con eventos o componentes
 * definidos en el archivo FXML correspondiente.</p>
 */
public class ControladorVentana {

    private static final Logger logger = LoggerFactory.getLogger(ControladorVentana.class);

    /** Campo de texto para introducir el nombre de la persona. */
    @FXML private TextField txtFirstName;

    /** Campo de texto para introducir el apellido de la persona. */
    @FXML private TextField txtLastName;

    /** Selector de fecha para introducir la fecha de nacimiento. */
    @FXML private DatePicker datePicker;

    /** Tabla principal donde se muestran las personas registradas. */
    @FXML private TableView<Person> tableView;

    /** Columna de la tabla que muestra el identificador de la persona. */
    @FXML private TableColumn<Person, Integer> colId;

    /** Columna de la tabla que muestra el nombre de la persona. */
    @FXML private TableColumn<Person, String> colFirstName;

    /** Columna de la tabla que muestra el apellido de la persona. */
    @FXML private TableColumn<Person, String> colLastName;

    /** Columna de la tabla que muestra la fecha de nacimiento de la persona. */
    @FXML private TableColumn<Person, LocalDate> colBirthDate;

    // ------------------------
    // Atributos internos
    // ------------------------

    /** Lista observable principal que contiene las personas actualmente en la tabla. */
    private ObservableList<Person> personList;

    /** Copia de seguridad de la lista inicial de personas. */
    private ObservableList<Person> backupList;


    /**
     * Metodo invocado automáticamente al inicializar la interfaz FXML.
     * Configura la tabla con datos iniciales, define los mapeos de columnas
     * y carga la lista de personas en la tabla.
     */
    @FXML
    public void initialize() {
        personList = FXCollections.observableArrayList(
                new Person(1, "Ashwin", "Sharan", LocalDate.of(2012, 10, 11)),
                new Person(2, "Advik", "Sharan", LocalDate.of(2012, 10, 11)),
                new Person(3, "Layne", "Estes", LocalDate.of(2011, 12, 16)),
                new Person(4, "Mason", "Boyd", LocalDate.of(2003, 4, 20)),
                new Person(5, "Babalu", "Sharan", LocalDate.of(1980, 1, 10))
        );
        backupList = FXCollections.observableArrayList(personList);

        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        colFirstName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFirstName()));
        colLastName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLastName()));
        colBirthDate.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getBirthDate()));

        tableView.setItems(personList);
    }

    /**
     * Maneja el evento de añadir una nueva persona.
     * <ul>
     *   <li>Obtiene los valores introducidos en los campos de texto y el selector de fecha.</li>
     *   <li>Valida que los campos no estén vacíos.</li>
     *   <li>Si la validación es correcta, crea una nueva {@link Person} y la añade a la lista.</li>
     *   <li>Si falla la validación, muestra un {@link Alert} de advertencia.</li>
     * </ul>
     */
    @FXML
    private void handleAdd() {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        LocalDate birthDate = datePicker.getValue();

        // Validación básica de campos vacíos
        if (firstName.isEmpty() || lastName.isEmpty() || birthDate == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor rellena todos los campos!");
            alert.showAndWait();
            logger.warn("Intento de añadir persona con campos incompletos.");
            return; // No se añade la persona
        }

        Person p;
        try {
            int newId = getNextId();
            // Intentamos crear la persona (aquí se validan nombre, apellido y fecha)
            p = new Person(newId, firstName, lastName, birthDate);
        } catch (IllegalArgumentException ex) {
            // Si falla la validación de la clase Person, mostramos Alert y no añadimos
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            alert.showAndWait();

            logger.warn("Intento de añadir persona inválida: {}", ex.getMessage());
            return; // Salimos del metodo, no se añade a la lista
        }

        // Si está bien, añadimos a la lista y limpiamos campos
        personList.add(p);
        txtFirstName.clear();
        txtLastName.clear();
        datePicker.setValue(null);

        logger.info("Persona añadida: {} {}", p.getFirstName(), p.getLastName());
    }


    /**
     * Maneja el evento de eliminar una persona seleccionada en la tabla.
     * <ul>
     *   <li>Obtiene la persona seleccionada en la tabla.</li>
     *   <li>Si existe selección, elimina la persona de la lista.</li>
     * </ul>
     */
    @FXML
    private void handleDelete() {
        Person selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            personList.remove(selected);
            logger.info("Persona eliminada: {} {}", selected.getFirstName(), selected.getLastName());
        }
        else {
            logger.info("Intento borrar a una persona sin seleccionar ninguna");
        }
    }

    /**
     * Maneja el evento de restaurar la lista original de personas.
     * <p>
     * Sustituye la lista actual por la copia de seguridad inicial.
     * </p>
     */
    @FXML
    private void handleRestore() {
        personList.setAll(backupList);
        logger.info("Se ha restaurado la copia de seguridad de la lista");
    }

    /**
     * Calcula el siguiente identificador (ID) disponible para una nueva {@link Person}.
     * <p>
     * Este metodo se asegura de que cada persona tenga un ID único y consecutivo,
     * incluso si se eliminan o restauran personas en la lista.
     * </p>
     *
     * <p>Funciona de la siguiente manera:</p>
     * <ol>
     *   <li>Convierte la lista de personas {@code personList} en un stream.</li>
     *   <li>Extrae solo los IDs de cada persona usando {@link Person#getId()}.</li>
     *   <li>Busca el valor máximo entre los IDs existentes.</li>
     *   <li>Si la lista está vacía, considera que el máximo es 0.</li>
     *   <li>Devuelve el máximo encontrado más 1 como el siguiente ID disponible.</li>
     * </ol>
     *
     * <p>Ejemplos:</p>
     * <ul>
     *   <li>Si los IDs existentes son [1, 2, 5], este metodo devolverá 6.</li>
     *   <li>Si la lista está vacía, devolverá 1.</li>
     * </ul>
     *
     * @return el siguiente ID disponible que se puede asignar a una nueva persona
     */
    private int getNextId() {
        return personList.stream()
                .mapToInt(Person::getId)
                .max()
                .orElse(0) + 1;
    }
}





