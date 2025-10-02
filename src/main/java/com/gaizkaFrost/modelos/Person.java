package com.gaizkaFrost.modelos;

import java.time.LocalDate;
import javafx.scene.control.Alert;

/**
 * Representa a una persona con atributos básicos como identificador, nombre, apellido y fecha de nacimiento.
 * <p>
 * Esta clase incluye validaciones que, en caso de error, muestran un mensaje emergente mediante {@link Alert}
 * en lugar de lanzar excepciones, de forma que el usuario final pueda entender qué ocurrió.
 * </p>
 *
 * @author Gaizka
 * @version 1.0
 * @see Alert
 */
public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    /**
     * Crea una nueva instancia de {@code Person} con los datos proporcionados.
     * Cada valor pasa por validaciones internas en sus respectivos métodos {@code set}.
     *
     * @param id identificador único de la persona (no puede ser negativo)
     * @param firstName nombre de la persona (no puede estar vacío ni contener caracteres no alfabéticos)
     * @param lastName apellido de la persona (no puede estar vacío ni contener caracteres no alfabéticos)
     * @param birthDate fecha de nacimiento de la persona (no puede ser nula, futura ni anterior a 1900)
     */
    public Person(int id, String firstName, String lastName, LocalDate birthDate) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
    }

    // ------------------------
    // Getters
    // ------------------------

    /**
     * Obtiene el identificador único de la persona.
     *
     * @return el id de la persona
     */
    public int getId() { return id; }

    /**
     * Obtiene el nombre de la persona.
     *
     * @return el nombre de la persona
     */
    public String getFirstName() { return firstName; }

    /**
     * Obtiene el apellido de la persona.
     *
     * @return el apellido de la persona
     */
    public String getLastName() { return lastName; }

    /**
     * Obtiene la fecha de nacimiento de la persona.
     *
     * @return la fecha de nacimiento
     */
    public LocalDate getBirthDate() { return birthDate; }

    // ------------------------
    // Setters con validación
    // ------------------------

    /**
     * Establece el identificador de la persona.
     *
     * @param id identificador único (no puede ser negativo)
     * @throws IllegalArgumentException si el valor es negativo
     */
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El id no puede ser negativo");
        }
        this.id = id;
    }

    /**
     * Establece el nombre de la persona.
     *
     * @param firstName nombre de la persona (solo letras)
     * @throws IllegalArgumentException si el nombre es nulo, vacío o contiene caracteres no alfabéticos
     */
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (!firstName.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ]+")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras.");
        }
        this.firstName = firstName.trim();
    }

    /**
     * Establece el apellido de la persona.
     *
     * @param lastName apellido de la persona (solo letras)
     * @throws IllegalArgumentException si el apellido es nulo, vacío o contiene caracteres no alfabéticos
     */
    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        if (!lastName.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ]+")) {
            throw new IllegalArgumentException("El apellido solo puede contener letras.");
        }
        this.lastName = lastName.trim();
    }

    /**
     * Establece la fecha de nacimiento de la persona.
     *
     * @param birthDate fecha de nacimiento (no nula, no futura y posterior a 1900)
     * @throws IllegalArgumentException si la fecha no cumple las condiciones
     */
    public void setBirthDate(LocalDate birthDate) {
        IsValidBirthDate(birthDate);
    }

    /**
     * Valida y asigna la fecha de nacimiento.
     *
     * @param birthDate fecha de nacimiento a validar
     * @throws IllegalArgumentException si la fecha es nula, futura o anterior a 1900
     */
    private void IsValidBirthDate(LocalDate birthDate){
        if (birthDate == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía.");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar en el futuro.");
        }
        if (birthDate.isBefore(LocalDate.of(1900, 1, 1))) {
            throw new IllegalArgumentException("La fecha de nacimiento debe ser posterior a 1900.");
        }
        this.birthDate = birthDate;
    }
}




