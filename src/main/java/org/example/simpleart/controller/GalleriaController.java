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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.example.simpleart.model.Opera;
import org.example.simpleart.model.SceneHandler;
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
    private FlowPane flowOpere;

    public void initialize() throws SQLException, IOException {
        galleriaLabel.setText("La galleria di " + currentUser.getNickname());
        caricaOpere();

    }

    @FXML
    void canvasClicked(MouseEvent event) throws IOException {
        SceneHandler.getInstance().sceneLoader("Lavagna.fxml", root.getWidth(), root.getHeight());
    }

    @FXML
    void cercaArtista(ActionEvent event) {

    }

    @FXML
    void goToHome(MouseEvent event) throws IOException {
        SceneHandler.getInstance().sceneLoader("Homepage.fxml", root.getWidth(), root.getHeight());
    }

    @FXML
    void logOut(MouseEvent event) throws IOException {
        currentUser.clean();
        SceneHandler.getInstance().sceneLoader("LoginPage.fxml", root.getWidth(), root.getHeight());
    }

    public void caricaOpere() throws SQLException, IOException {
        flowOpere.getChildren().clear();
        ArrayList<Opera> opere = Query.getAllArtOfAnArtist(currentUser.getEmail());
        for(Opera i : opere) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/simpleart/CardOperaPropria.fxml"));
            Parent card = loader.load();

            CardOperaPropriaController controller = loader.getController();

            controller.setOpera(i);
            flowOpere.getChildren().add(card);
        }
    }

    @FXML
    public void test(MouseEvent e) throws SQLException, IOException {
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/gomma.png")));
        Opera opera = new Opera(img, "La gomma", currentUser.getNickname(), true);
        Query.addArt(opera);

        Image img2 = new Image(Objects.requireNonNull(getClass().getResource("/opere/Rabbia.png")).toExternalForm());
        Opera opera2 = new Opera(img2, "Rabbia", currentUser.getNickname(), true);
        Query.addArt(opera2);

        Image img3 = new Image(Objects.requireNonNull(getClass().getResource("/opere/FruitsAndVegetable.png")).toExternalForm());
        Opera opera3 = new Opera(img3, "FruttaEVerdura", currentUser.getNickname(), false);
        Query.addArt(opera3);

        caricaOpere();
    }

    @FXML
    public void backToProfile(MouseEvent e) throws IOException {
        SceneHandler.getInstance().sceneLoader("ProfiloPage.fxml", root.getWidth(), root.getHeight());
    }
}
