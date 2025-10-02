package com.gaizkaFrost;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase {@code ConexionBD} que gestiona la conexión a la base de datos
 * utilizando los parámetros configurados en el archivo {@code config.properties}.
 * <p>
 * Los datos de conexión (URL, usuario y contraseña) se obtienen desde la clase
 * {@link Config}, y el metodo {@link #getConnection()} permite establecer una
 * conexión activa con la base de datos.
 * </p>
 *
 * @author Gaizka
 * @version 1.0
 * @see Config
 */
public class ConexionBD {

    private static final String URL = Config.get("db.url");
    private static final String USER = Config.get("db.user");
    private static final String PASSWORD = Config.get("db.password");

    /**
     * Establece una conexión con la base de datos usando los parámetros
     * configurados en el archivo {@code config.properties}.
     *
     * @return un objeto {@link Connection} activo hacia la base de datos
     * @throws SQLException si ocurre un error al intentar conectar
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
