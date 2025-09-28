package com.gaizkaFrost.modelos;

import java.time.LocalDate;
import javafx.scene.control.Alert;

/**
 * Representa a una persona con atributos básicos como identificador, nombre, apellido y fecha de nacimiento.
 * <p>
 * Esta clase incluye validaciones que, en caso de error, muestran un mensaje emergente mediante {@link Alert}
 * en lugar de lanzar excepciones, de forma que el usuario final pueda entender qué ocurrió.
 * </p>
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

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getBirthDate() { return birthDate; }

    // ------------------------
    // Setters con validación y Alert
    // ------------------------

    public void setId(int id) {
        if (id < 0) {
           throw new IllegalArgumentException("El id no puede ser negativo");
        }
        this.id = id;
    }
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (!firstName.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ]+")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras.");
        }
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        if (!lastName.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ]+")) {
            throw new IllegalArgumentException("El apellido solo puede contener letras.");
        }
        this.lastName = lastName.trim();
    }

    public void setBirthDate(LocalDate birthDate) {
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



