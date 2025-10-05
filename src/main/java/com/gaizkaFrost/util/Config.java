package com.gaizkaFrost.util;

import java.util.Properties;
import java.io.InputStream;

/**
 * Clase {@code Config} encargada de gestionar la configuración de la aplicación
 * a partir de un archivo externo {@code config.properties}.
 * <p>
 * Los valores de configuración se cargan en memoria de manera estática al
 * inicializar la clase, permitiendo acceder a ellos en cualquier parte del
 * programa mediante el metodo {@link #get(String)}.
 * </p>
 *
 * @author Gaizka
 * @version 1.0
 */
public class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = Config.class.getResourceAsStream("/config.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró config.properties en resources");
            }
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo de configuración: config.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}


