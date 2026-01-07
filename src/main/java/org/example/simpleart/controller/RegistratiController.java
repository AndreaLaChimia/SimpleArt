package org.example.simpleart.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.simpleart.model.SceneHandler;

import java.io.IOException;
import java.util.Objects;

public class RegistratiController {

    @FXML
    private TextField cognomeField;

    @FXML
    private TextField emailField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label loginBottone;

    @FXML
    private ImageView logo;

    @FXML
    private TextField nicknameField;

    @FXML
    private TextField nomeField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordField2;

    @FXML
    private Button registratiButton;

    public void initialize(){
        SceneHandler.getInstance().getStage().setTitle("SimpleArt - Registrati");
        logo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/SimpleArt logo.png"))));
        errorLabel.setVisible(false);
    }

    public void login() throws IOException {
        SceneHandler.getInstance().sceneLoader("LoginPage.fxml");
    }
}

