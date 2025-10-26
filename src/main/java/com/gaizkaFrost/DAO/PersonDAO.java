package com.gaizkaFrost.DAO;

import com.gaizkaFrost.modelos.Person;
import com.gaizkaFrost.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase {@code PersonDAO} que implementa las operaciones CRUD (Create, Read,
 * Update, Delete) para la entidad {@link Person} en la base de datos.
 * <p>
 * Esta clase utiliza la conexión proporcionada por {@link ConexionBD} y
 * ejecuta consultas SQL sobre la tabla {@code persons}.
 * </p>
 *
 * @author Gaizka
 * @version 1.0
 * @see Person
 * @see ConexionBD
 */
public class PersonDAO {

    /**
     * Obtiene todos los registros de la tabla {@code persons}.
     *
     * @return una lista de objetos {@link Person} con todos los registros encontrados;
     *         la lista puede estar vacía si no existen registros
     */
    public static List<Person> getAll() {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM person";
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                persons.add(new Person(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("birth_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    /**
     * Inserta un nuevo registro en la tabla {@code persons}.
     *
     * @param p el objeto {@link Person} que se desea insertar
     */
    public static void insert(Person p) throws SQLException {
        String sql = "INSERT INTO person(first_name,last_name,birth_date) VALUES(?,?,?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getLastName());
            ps.setDate(3, Date.valueOf(p.getBirthDate()));
            ps.executeUpdate();
        } catch (SQLException e) {
        throw new SQLException("Error insertando persona: " + e.getMessage(), e);
        }
    }

    /**
     * Actualiza un registro existente en la tabla {@code persons}.
     *
     * @param p el objeto {@link Person} con los nuevos valores a actualizar,
     *          identificado por su {@code id}
     */
    public static void update(Person p) {
        String sql = "UPDATE person SET first_name=?, last_name=?, birth_date=? WHERE id=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getLastName());
            ps.setDate(3, Date.valueOf(p.getBirthDate()));
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un registro de la tabla {@code persons} según su identificador.
     *
     * @param id el identificador único del registro a eliminar
     */
    public static void delete(int id) {
        String sql = "DELETE FROM person WHERE id=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
