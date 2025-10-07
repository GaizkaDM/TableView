package com.gaizkaFrost.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gaizkaFrost.modelos.Person;
import com.gaizkaFrost.DAO.PersonDAO;
import javafx.application.Platform;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador principal de la ventana JavaFX que gestiona la interacción
 * entre la interfaz gráfica y la base de datos MariaDB de forma asíncrona.
 *
 * <p>Permite realizar operaciones CRUD (crear, eliminar y consultar personas)
 * ejecutando las tareas en hilos secundarios mediante {@link Task}, evitando
 * bloqueos en la interfaz y manteniendo una experiencia fluida para el usuario.</p>
 *
 * @author Gaizka
 * @version 2.0 (asíncrono)
 * @see com.gaizkaFrost.modelos.Person
 * @see com.gaizkaFrost.DAO.PersonDAO
 */
public class ControladorVentana {

    /** Registro de eventos y errores del controlador. */
    private static final Logger logger = LoggerFactory.getLogger(ControladorVentana.class);

    // Elementos del menú
    @FXML private CustomMenuItem menuAbout;
    @FXML private CustomMenuItem menuClose;

    // Campos de entrada de datos
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private DatePicker datePicker;

    // Componentes de la tabla
    @FXML private TableView<Person> tableView;
    @FXML private TableColumn<Person, Integer> colId;
    @FXML private TableColumn<Person, String> colFirstName;
    @FXML private TableColumn<Person, String> colLastName;
    @FXML private TableColumn<Person, LocalDate> colBirthDate;

    /** Lista observable que se sincroniza automáticamente con la tabla y la base de datos. */
    private final ObservableList<Person> personList = FXCollections.observableArrayList();

    /**
     * Inicializa la tabla y lanza la carga inicial de datos desde la base de datos.
     * <p>Este método es llamado automáticamente por JavaFX al cargar el archivo FXML.</p>
     */
    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        tableView.setItems(personList);
        tableView.setRowFactory(tv -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Person selected = row.getItem();
                    openEditDialog(selected);
                }
            });
            return row;
        });
        refreshTableAsync();
    }

    /**
     * Añade una nueva persona a la base de datos de forma asíncrona.
     * <p>Valida los campos de entrada antes de la inserción y actualiza la tabla
     * una vez completada la operación correctamente.</p>
     */
    @FXML
    private void handleAdd() {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        LocalDate birthDate = datePicker.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || birthDate == null) {
            showAlert(Alert.AlertType.WARNING, "Por favor rellena todos los campos!");
            return;
        }

        try {
            Person p = new Person(0, firstName, lastName, birthDate);

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    PersonDAO.insert(p);
                    return null;
                }
            };

            task.setOnSucceeded(e -> {
                logger.info("Persona añadida a BD: {} {}", p.getFirstName(), p.getLastName());
                txtFirstName.clear();
                txtLastName.clear();
                datePicker.setValue(null);
                refreshTableAsync();
            });

            task.setOnFailed(e -> {
                showAlert(Alert.AlertType.ERROR, "Error al añadir: " + task.getException().getMessage());
                logger.error("Error al añadir persona", task.getException());
            });

            new Thread(task).start();

        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "Error en los datos: " + ex.getMessage());
        }
    }

    /**
     * Elimina de la base de datos la persona seleccionada en la tabla.
     * <p>Si no hay ninguna selección activa, se muestra un aviso al usuario.</p>
     */
    @FXML
    private void handleDelete() {
        Person selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selecciona una persona para eliminar.");
            return;
        }

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                PersonDAO.delete(selected.getId());
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            logger.info("Persona eliminada de BD: {} {}", selected.getFirstName(), selected.getLastName());
            personList.remove(selected);
        });

        task.setOnFailed(e -> {
            showAlert(Alert.AlertType.ERROR, "Error al eliminar: " + task.getException().getMessage());
            logger.error("Error al eliminar persona", task.getException());
        });

        new Thread(task).start();
    }

    /**
     * Restaura la tabla recargando los datos desde la base de datos.
     * <p>No modifica los registros, únicamente actualiza la vista.</p>
     */
    @FXML
    private void handleRestore() {
        refreshTableAsync();
        logger.info("Tabla restaurada desde BD");
    }

    /**
     * Recarga de forma asíncrona los registros de la base de datos y actualiza la tabla.
     * <p>Usa {@link PersonDAO#getAll()} para obtener todos los registros actuales.</p>
     */
    private void refreshTableAsync() {
        Task<List<Person>> task = new Task<>() {
            @Override
            protected List<Person> call() throws Exception {
                return PersonDAO.getAll();
            }
        };

        task.setOnSucceeded(e -> {
            personList.setAll(task.getValue());
            logger.info("Datos cargados correctamente desde BD ({} registros).", task.getValue().size());
        });

        task.setOnFailed(e -> {
            showAlert(Alert.AlertType.ERROR, "Error al cargar datos: " + task.getException().getMessage());
            logger.error("Error cargando datos", task.getException());
        });

        new Thread(task).start();
    }

    /**
     * Muestra un cuadro de diálogo de alerta en la interfaz gráfica.
     *
     * @param type Tipo de alerta (información, advertencia o error)
     * @param msg  Mensaje que se mostrará al usuario
     */
    private void showAlert(Alert.AlertType type, String msg) {
        Platform.runLater(() -> new Alert(type, msg).showAndWait());
    }

    /**
     * Cierra la ventana principal y finaliza la aplicación.
     */
    @FXML
    private void handleClose() {
        ((Stage) tableView.getScene().getWindow()).close();
    }

    /**
     * Muestra un cuadro informativo con los datos del autor y la versión actual del programa.
     */
    @FXML
    private void handleAbout() {
        new Alert(Alert.AlertType.INFORMATION,
                "Autor: Gaizka Rodríguez\nVersión: 2.0 (Asíncrona)").showAndWait();
    }
    @FXML
    private void openEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gaizkaFrost/fxml/editPerson.fxml"));

            Parent root = loader.load();

            EditPersonController controller = loader.getController();
            controller.setPerson(person);

            Stage stage = new Stage();
            stage.setTitle("Editar Persona");
            stage.initOwner(tableView.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (controller.isSaved()) {
                refreshTableAsync();
            }

        } catch (IOException e) {
            logger.error("Error abriendo ventana de edición", e);
            new Alert(Alert.AlertType.ERROR, "Error al abrir la ventana de edición.").showAndWait();
        }
    }
}
