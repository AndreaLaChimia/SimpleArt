package org.example.simpleart.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.example.simpleart.model.SceneHandler;

import java.io.IOException;
import java.util.Objects;

public class LoginPageController{

    @FXML
    private Label RegistratiButton;

    @FXML
    private TextField emailField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private ImageView logo;

    @FXML
    private PasswordField passwordField;

    public void initialize(){
        logo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/SimpleArt logo.png"))));
        emailField.setText("");
        passwordField.setText("");
        errorLabel.setText("");
    }

    @FXML
    void login(MouseEvent event) throws IOException {
        SceneHandler.getInstance().sceneLoader("Homepage.fxml");
    }

    @FXML
    void registrati(MouseEvent event) throws IOException {
        SceneHandler.getInstance().sceneLoader("Registrati.fxml");
    }

}
