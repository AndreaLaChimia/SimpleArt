package org.example.simpleart.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.example.simpleart.model.Query;
import org.example.simpleart.model.SceneHandler;
import org.example.simpleart.model.currentUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static org.example.simpleart.model.Print.print;

public class LoginPageController{

    @FXML
    private BorderPane root;

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
        if(currentUser.getEmail() == null || currentUser.getEmail().isEmpty())
            emailField.setText("");
        else
            emailField.setText(currentUser.getEmail());
        passwordField.setText("");
        errorLabel.setText("");

        loginButton.setDefaultButton(true);
    }

    @FXML
    void login(MouseEvent event) throws IOException, SQLException {
        errorLabel.setText("");
        if(emailField.getText().isEmpty() || passwordField.getText().isEmpty())
            errorLabel.setText("Non tutti i campi sono compilati.");

        if (Query.verificaPassword(emailField.getText().toLowerCase(), passwordField.getText())){
            Query.getAllFromEmail(emailField.getText().toLowerCase());
            SceneHandler.getInstance().sceneLoader("Homepage.fxml", root.getWidth(), root.getHeight());
            print(currentUser.getNome() + " " + currentUser.getCognome() +  " " + currentUser.getNickname() + " " + currentUser.getEmail());
        }
        else errorLabel.setText("Email o password non validi.");


    }

    @FXML
    void registrati(MouseEvent event) throws IOException {
        SceneHandler.getInstance().sceneLoader("Registrati.fxml", root.getWidth(), root.getHeight());
    }

}
