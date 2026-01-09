module org.example.simpleart {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;
    requires spring.security.crypto;


    opens org.example.simpleart to javafx.fxml;
    exports org.example.simpleart;
    exports org.example.simpleart.controller;
    exports org.example.simpleart.model;
    opens org.example.simpleart.controller to javafx.fxml;
    opens org.example.simpleart.model to javafx.fxml;
}