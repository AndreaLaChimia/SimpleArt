package org.example.simpleart.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.example.simpleart.model.SceneHandler;
import org.example.simpleart.model.currentUser;

import java.io.IOException;
import java.util.Objects;

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
    private ImageView fotoProfilo;

    @FXML
    private Label emailLabel;

    @FXML
    private Button modificaProfiloButton, exitButton;

    @FXML
    private Label nicknameLabel;

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

    public void initialize(){
        if(currentUser.getFoto() != null)
            fotoProfilo.setImage(currentUser.getFoto());
        nicknameLabel.setText(currentUser.getNickname());
        nomeCognomeLabel.setText(currentUser.getNome() + " " + currentUser.getCognome());
        emailLabel.setText(currentUser.getEmail());
        if(currentUser.getDescrizione() != null)
            descrizioneBox.setText(currentUser.getDescrizione());

    }

    @FXML
    void canvasClicked(MouseEvent event) throws IOException {
        SceneHandler.getInstance().sceneLoader("Lavagna.fxml", root.getWidth(), root.getHeight());
    }

    @FXML
    void cercaArtista(ActionEvent event) {
        print("Hai cercato l'artista col nome di " + barraDiRicerca.getText());
    }

    @FXML
    void vaiAModificaProfilo(MouseEvent event) throws IOException {
        SceneHandler.getInstance().sceneLoader("ModificaProfilo.fxml", root.getWidth(), root.getHeight());

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
