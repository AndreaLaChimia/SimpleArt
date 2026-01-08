package org.example.simpleart.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.example.simpleart.model.SceneHandler;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Objects;

import static org.example.simpleart.model.Print.print;
import static org.example.simpleart.model.regularExpression.*;

public class RegistratiController {

    @FXML
    private BorderPane root;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField nameField, nicknameField;

    @FXML
    private Label textFieldRule;

    @FXML
    private PasswordField passwordField, passwordField2;

    public void initialize(){
        errorLabel.setVisible(false);
        textFieldRule.setVisible(false);
    }

    @FXML
    public void login() throws IOException {
        SceneHandler.getInstance().sceneLoader("LoginPage.fxml", root.getWidth(), root.getHeight());
    }

    @FXML
    public void registrati(){
        print("Tasto registrati premuto.");
        textFieldRule.setVisible(false);
        errorLabel.setVisible(false);
        String nome = nameField.getText();
        String cognome = surnameField.getText();
        String nickname = nicknameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String password2 = passwordField2.getText();

        if(!checkNameOrSurname(nome)) {
            errorLabel.setVisible(true);
            errorLabel.setText("Problemi col nome inserito.");
            return;
        }

        if(!checkNameOrSurname(cognome)){
            errorLabel.setVisible(true);
            errorLabel.setText("Problemi col cognome inserito.");
            return;
        }

        if(!checkNickname(nickname)){
            errorLabel.setVisible(true);
            errorLabel.setText("Problemi col nickname inserito.");
            return;
        }

        if(!checkEmail(email)){
            errorLabel.setVisible(true);
            errorLabel.setText("Problemi con l'email inserita.");
            return;
        }

        if(!checkPassword(password) || !checkPassword(password2)){
            errorLabel.setVisible(true);
            errorLabel.setText("Problemi con la password inserita.");
        }

        if(!password.equals(password2)){
            errorLabel.setVisible(true);
            errorLabel.setText("Le due password non coincidono.");
            return;
        }

        print(nome + " " + cognome + " " + nickname + " " + email + " " + password + " " + password2);



    }

    @FXML
    void questionButtonName(MouseEvent event) {
        textFieldRule.setVisible(true);
        textFieldRule.setText("Non può contenere numeri o caratteri speciali.");
    }

    @FXML
    void questionButtonSurname(MouseEvent event) {
        textFieldRule.setVisible(true);
        textFieldRule.setText("Non può contenere numeri o caratteri speciali.");
    }

    @FXML
    void questionButtonNickname(MouseEvent event) {
        textFieldRule.setVisible(true);
        textFieldRule.setText("Due caratteri minimo, 20 massimo.");
    }

    @FXML
    void questionButtonEmail(MouseEvent event) {
        textFieldRule.setVisible(true);
        textFieldRule.setText("Il formato deve essere quello standard.");
    }

    @FXML
    void questionButtonPassword(MouseEvent event) {
        textFieldRule.setVisible(true);
        textFieldRule.setText("Almeno una maiuscola e un numero.");
    }

}

