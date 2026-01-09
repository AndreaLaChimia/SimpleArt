package org.example.simpleart.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.simpleart.model.Opera;
import org.example.simpleart.model.currentUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class GalleriaController{

    @FXML
    private TextField barraDiRicerca;

    @FXML
    private Label canvasButton;

    @FXML
    private ImageView doorImgButton;

    @FXML
    private Button exitButton, testButton;

    @FXML
    private ScrollPane scrollOpere;

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

    public void initialize() throws SQLException, IOException {
        galleriaLabel.setText("La galleria di " + currentUser.getNickname());
        caricaOpere();
        System.out.println(getClass().getResource("/css/style.css"));

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

    public void caricaOpere() throws SQLException, IOException {
        ArrayList<Opera> opere = Query.getAllArtOfAnArtist(currentUser.getEmail());
        for(Opera i : opere) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/simpleart/CardOperaPropria.fxml"));
            Parent card = loader.load();

            CardOperaPropriaController controller = loader.getController();

            controller.setOpera(i);
            vBoxOpere.getChildren().add(card);
        }
    }

    @FXML
    public void test(MouseEvent e) throws SQLException, IOException {
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/gioconda.png")));
        Opera opera = new Opera(img, "La Gioconda", currentUser.getNickname(), true);
        Query.addArt(opera);

        Image img2 = new Image(Objects.requireNonNull(getClass().getResource("/opere/Woman.png")).toExternalForm());
        Opera opera2 = new Opera(img2, "La donna", currentUser.getNickname(), true);
        Query.addArt(opera2);

        caricaOpere();
    }

}
