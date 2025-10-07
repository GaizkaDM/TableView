package com.gaizkaFrost.controlador;

import com.gaizkaFrost.DAO.PersonDAO;
import com.gaizkaFrost.modelos.Person;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDate;

    /**
     * Controlador para la ventana de edición de una persona.
     */
    public class EditPersonController {

        private static final Logger logger = LoggerFactory.getLogger(EditPersonController.class);

        @FXML private TextField txtFirstName;
        @FXML private TextField txtLastName;
        @FXML private DatePicker datePicker;

        private Person person; // persona actual que se edita
        private boolean saved = false;

        /**
         * Rellena los campos con los datos de la persona seleccionada.
         */
        public void setPerson(Person person) {
            this.person = person;
            txtFirstName.setText(person.getFirstName());
            txtLastName.setText(person.getLastName());
            datePicker.setValue(person.getBirthDate());
        }

        /**
         * Devuelve true si se guardaron los cambios.
         */
        public boolean isSaved() {
            return saved;
        }

        @FXML
        private void handleSave() {
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            LocalDate birthDate = datePicker.getValue();

            if (firstName.isEmpty() || lastName.isEmpty() || birthDate == null) {
                new Alert(Alert.AlertType.WARNING, "Rellena todos los campos.").showAndWait();
                return;
            }

            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setBirthDate(birthDate);

            PersonDAO.update(person);
            saved = true;
            logger.info("Persona actualizada correctamente: {}", person.getId());

            closeWindow();

        }

        @FXML
        private void handleCancel() {
            closeWindow();
        }

        private void closeWindow() {
            Stage stage = (Stage) txtFirstName.getScene().getWindow();
            stage.close();
        }
    }
