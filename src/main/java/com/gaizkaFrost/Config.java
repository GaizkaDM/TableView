package com.gaizkaFrost;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de configuración: " + CONFIG_FILE, e);
        }
    }

    /**
     * Obtiene el valor asociado a una clave en el archivo de configuración.
     *
     * @param key la clave de la propiedad que se desea recuperar
     * @return el valor correspondiente a la clave, o {@code null} si no existe
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }
}

