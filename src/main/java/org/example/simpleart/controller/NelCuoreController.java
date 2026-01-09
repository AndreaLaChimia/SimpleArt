package org.example.simpleart.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import org.example.simpleart.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.example.simpleart.model.Print.print;

public class NelCuoreController{

    @FXML
    private ImageView backButton;

    @FXML
    private TextField barraDiRicerca;

    @FXML
    private Label canvasButton;

    @FXML
    private ImageView doorImgButton;

    @FXML
    private Button exitButton;

    @FXML
    private FlowPane flowOpere;

    @FXML
    private Label homeButton;

    @FXML
    private ImageView homeImgButton;

    @FXML
    private Label nelCuoreLabel;

    @FXML
    private BorderPane root;

    @FXML
    private ScrollPane scrollOpere;

    public void initialize() throws SQLException, IOException {
        print("nell'initialize di nelcuorecontroller.");
        caricaUtenti();
    }

    @FXML
    public void backToProfile(MouseEvent e) throws IOException {
        SceneHandler.getInstance().sceneLoader("ProfiloPage.fxml", root.getWidth(), root.getHeight());
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
    public void logOut(MouseEvent e) throws IOException {
        currentUser.clean();
        SceneHandler.getInstance().sceneLoader("LoginPage.fxml", root.getWidth(), root.getHeight());
    }

    public void caricaUtenti() throws SQLException, IOException {
        flowOpere.getChildren().clear();
        List<Utente> utenti = Query.getFollower(currentUser.getEmail());
        if (utenti.isEmpty())
            print("Lista vuota.");
        for(Utente i : utenti) {
            print("Entrati nel caricaOpere di utenti nel cuore.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/simpleart/CardUtente.fxml"));
            Parent card = loader.load();

            CardUtenteController controller = loader.getController();

            controller.setUtente(i);
            flowOpere.getChildren().add(card);
        }
    }

}
