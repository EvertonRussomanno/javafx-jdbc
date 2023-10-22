module com.evertonmartins.javafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.evertonmartins.javafxjdbc to javafx.fxml;
    exports com.evertonmartins.javafxjdbc;
}