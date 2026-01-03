package org.example.simpleart.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneHandler {
    private static SceneHandler sceneHandler = new SceneHandler();
    private static Stage stage = null;

    private SceneHandler(){ }


    public static SceneHandler getInstance(){
        return sceneHandler;
    }

    public void sceneLoader(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/simpleart/" + url));
        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.show();

    }

    public static void init(Stage stage){
        SceneHandler.stage = stage;
    }
}