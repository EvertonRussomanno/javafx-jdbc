module com.evertonmartins.javafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.evertonmartins.javafxjdbc.model.entities;
    opens com.evertonmartins.javafxjdbc.model.entities to java.base;

    exports com.evertonmartins.javafxjdbc.application;
    opens com.evertonmartins.javafxjdbc.application to javafx.fxml;

}