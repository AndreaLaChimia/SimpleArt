package org.example.simpleart.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.simpleart.model.Opera;
import org.example.simpleart.model.currentUser;

import java.sql.SQLException;
import java.util.ArrayList;

public class GalleriaController{

    @FXML
    private TextField barraDiRicerca;

    @FXML
    private Label canvasButton;

    @FXML
    private ImageView doorImgButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label galleriaLabel;

    @FXML
    private Label homeButton;

    @FXML
    private ImageView homeImgButton;

    @FXML
    private BorderPane root;

    @FXML
    private VBox vBoxOpere;

    public void initialize() throws SQLException {
        ArrayList<Opera> opere = Query.getAllArtOfAnArtist(currentUser.getEmail());
    }

    @FXML
    void canvasClicked(MouseEvent event) {

    }

    @FXML
    void cercaArtista(ActionEvent event) {

    }

    @FXML
    void goToHome(MouseEvent event) {

    }

    @FXML
    void logOut(MouseEvent event) {

    }

}
