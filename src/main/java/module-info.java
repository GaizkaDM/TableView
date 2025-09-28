module com.gaizkaFrost {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    opens com.gaizkaFrost.controlador to javafx.fxml;
    opens com.gaizkaFrost to javafx.fxml;
    exports com.gaizkaFrost;


}