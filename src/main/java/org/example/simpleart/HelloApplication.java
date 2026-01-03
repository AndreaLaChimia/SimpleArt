package org.example.simpleart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.simpleart.model.Database;
import org.example.simpleart.model.SceneHandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("SimpleArt-Login");
        stage.setScene(scene);
        stage.show();
        SceneHandler.init(stage);
        Connection cn = Database.getConnection();
    }

    public static void main(String[] args) {
        launch();
    }
}