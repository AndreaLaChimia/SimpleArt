package org.example.simpleart.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.example.simpleart.model.SceneHandler;

import javax.swing.border.Border;
import java.io.IOException;

import static org.example.simpleart.model.Print.print;

public class ProfiloPageController{

    @FXML
    private BorderPane root;

    @FXML
    private TextField barraDiRicerca;

    @FXML
    private Label canvasButton;

    @FXML
    private TextArea descrizioneBox;

    @FXML
    private ImageView fotoProfilo1;

    @FXML
    private Label indirizzoLabel;

    @FXML
    private Button modificaProfiloButton, exitButton;

    @FXML
    private Label nicknameLabel1;

    @FXML
    private Label nomeCognomeLabel;

    @FXML
    private ImageView homeImgButton;

    @FXML
    private Button pulsanteGalleria;

    @FXML
    private Button pulsanteNelCuore;

    @FXML
    private Label homeButton;

    @FXML
    void canvasClicked(MouseEvent event) throws IOException {
        SceneHandler.getInstance().sceneLoader("Lavagna.fxml", root.getWidth(), root.getHeight());
    }

    @FXML
    void cercaArtista(ActionEvent event) {
        print("Hai cercato l'artista col nome di " + barraDiRicerca.getText());
    }

    @FXML
    void vaiAModificaProfilo(MouseEvent event) {
        print("Pulsante modifica profilo schiacciato.");
    }

    @FXML
    void goToHome(MouseEvent event) throws IOException {
        SceneHandler.getInstance().sceneLoader("Homepage.fxml", root.getWidth(), root.getHeight());
    }

    @FXML
    void goToGallery(MouseEvent event) {
        print("Pulsante galleria schiacciato.");

    }

    @FXML
    void goToNelCuore(MouseEvent event) {
        print("Pulsante lista 'Nel cuore' schiacciato.");
    }

    @FXML
    public void logOut(MouseEvent e){
        print("Tentativo di logout.");
    }

}
