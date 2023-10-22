module com.evertonmartins.javafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    exports com.evertonmartins.javafxjdbc.application;
    opens com.evertonmartins.javafxjdbc.application to javafx.fxml;

    exports com.evertonmartins.javafxjdbc.model.dao.impl;
    opens com.evertonmartins.javafxjdbc.model.dao.impl to javafx.fxml;

    exports com.evertonmartins.javafxjdbc.util;
    opens com.evertonmartins.javafxjdbc.util to javafx.fxml;

}