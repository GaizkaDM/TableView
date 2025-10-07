package com.gaizkaFrost.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.gaizkaFrost.modelos.Person;
import com.gaizkaFrost.DAO.PersonDAO;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador principal de la ventana JavaFX que gestiona la interacción
 * entre la interfaz gráfica y la capa de acceso a datos ({@link PersonDAO}).
 * <p>
 * Se encarga de inicializar la tabla, manejar eventos de botones y
 * sincronizar la vista con la base de datos MariaDB.
 * </p>
 *
 * @author Gaizka
 * @version 1.0
 * @see Person
 * @see PersonDAO
 */
public class ControladorVentana {

    private static final Logger logger = LoggerFactory.getLogger(ControladorVentana.class);
    public CustomMenuItem menuAbout;
    public CustomMenuItem menuClose;

    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private DatePicker datePicker;

    @FXML private TableView<Person> tableView;
    @FXML private TableColumn<Person, Integer> colId;
    @FXML private TableColumn<Person, String> colFirstName;
    @FXML private TableColumn<Person, String> colLastName;
    @FXML private TableColumn<Person, LocalDate> colBirthDate;
    // Lista observable que se refresca desde la base de datos
    private ObservableList<Person> personList = FXCollections.observableArrayList();

    /**
     * Inicializa la tabla de personas y carga los datos desde la base de datos.
     * <p>Este metodo es invocado automáticamente por JavaFX al cargar la vista FXML.</p>
     */
    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

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


        refreshTable();
    }

    /**
     * Maneja la acción de añadir una nueva persona a la base de datos.
     * <p>Valida los campos de entrada antes de insertar.</p>
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
            Person p = new Person(0, firstName, lastName, birthDate); // id = 0 → lo genera la BD
            PersonDAO.insert(p);
            refreshTable();

            txtFirstName.clear();
            txtLastName.clear();
            datePicker.setValue(null);

            logger.info("Persona añadida a BD: {} {}", p.getFirstName(), p.getLastName());
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "Error al añadir: " + ex.getMessage());
            logger.error("Error al añadir persona");
        }
        catch (SQLException e){
            showAlert(Alert.AlertType.ERROR, "Error al añadir: " + e.getMessage()+ "a la base de datos");
            logger.error("Error al añadir persona a la base de datos");
        }
        catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error inesperado: " + ex.getMessage());
            logger.error("Error inesperado en handleAdd()", ex);
        }
    }

    /**
     * Maneja la acción de eliminar una persona seleccionada en la tabla.
     * <p>Si no hay ninguna persona seleccionada, muestra una advertencia.</p>
     */
    @FXML
    private void handleDelete() {
        Person selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selecciona una persona para eliminar.");
            return;
        }

        try {
            PersonDAO.delete(selected.getId());
            refreshTable();
            logger.info("Persona eliminada de BD: {} {}", selected.getFirstName(), selected.getLastName());
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error al eliminar: " + ex.getMessage());
            logger.error("Error al eliminar persona");
        }
    }

    /**
     * Maneja la acción de restaurar la tabla recargando los datos desde la base de datos.
     */
    @FXML
    private void handleRestore() {
        refreshTable();
        logger.info("Tabla restaurada desde BD");
    }

    /**
     * Refresca los datos de la tabla cargándolos desde la base de datos.
     * <p>Este metodo sincroniza la lista observable {@code personList} con la vista.</p>
     */
    private void refreshTable() {
        try {
            List<Person> persons = PersonDAO.getAll(); // ⚠️ antes usabas findAll(), lo cambié a getAll() que es el que tienes implementado
            personList.setAll(persons);
            tableView.setItems(personList);
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error al cargar datos: " + ex.getMessage());
            logger.error("Error cargando datos", ex);
        }
    }

    /**
     * Muestra un cuadro de diálogo de alerta con un mensaje específico.
     *
     * @param type el tipo de alerta (información, advertencia, error)
     * @param msg  el mensaje que se mostrará en la alerta
     */
    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type, msg);
        alert.showAndWait();
    }

    /**
     * Cierra la ventana actual de la aplicación.
     * <p>
     * Este metodo obtiene la ventana activa a partir del componente
     * {@code tableView} y la cierra utilizando la clase {@code Stage}.
     * </p>
     *
     * @see javafx.stage.Stage
     */
    @FXML
    private void handleClose() {
        ((Stage) tableView.getScene().getWindow()).close();
    }

    /**
     * Muestra una ventana de información con detalles sobre la aplicación.
     * <p>
     * El cuadro de diálogo incluye el nombre del autor y la versión de la aplicación.
     * </p>
     *
     * @see javafx.scene.control.Alert
     */
    @FXML
    private void handleAbout() {
        new Alert(Alert.AlertType.INFORMATION,
                "Autor: Gaizka Rodríguez\nVersión: 1.0").showAndWait();
    }

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
                refreshTable();
            }

        } catch (IOException e) {
            logger.error("Error abriendo ventana de edición", e);
            new Alert(Alert.AlertType.ERROR, "Error al abrir la ventana de edición.").showAndWait();
        }
    }

}







