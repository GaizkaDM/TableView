module com.gaizkaFrost {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;
    opens com.gaizkaFrost.controlador to javafx.fxml;
    opens com.gaizkaFrost to javafx.fxml;
    exports com.gaizkaFrost;

    opens com.gaizkaFrost.modelos to javafx.base;
    exports com.gaizkaFrost.util;
    opens com.gaizkaFrost.util to javafx.fxml;

}