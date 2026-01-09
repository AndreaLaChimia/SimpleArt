package org.example.simpleart.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.example.simpleart.model.*;

import java.io.IOException;
import java.util.List;

import static org.example.simpleart.model.Print.print;

public class HomepageController{

    @FXML
    private ImageView balloon, img1, img2, img3, img4;
    @FXML
    private Label canvasButton;
    @FXML
    private ImageView logo;
    @FXML
    private Label userButton, stringaCiao;
    @FXML
    private HBox galleria;
    @FXML
    private TextField barraDiRicerca;
    @FXML
    private BorderPane root;



    public void initialize() throws IOException {
        SceneHandler.getInstance().getStage().setTitle("SimpleArt-Homepage");
        stringaCiao.setText("Ciao " + currentUser.getNickname());

        List<Opera> opereCasuali = Query.getOpereCasuali();
        for (Opera opera : opereCasuali) {
            print("Id dell'opera caricata in homepage: " + String.valueOf(opera.getId()));
            riempiGalleria(opera);
        }
    }

    @FXML
    void canvasClicked(MouseEvent event) throws IOException {
        SceneHandler.getInstance().sceneLoader("Lavagna.fxml", root.getWidth(), root.getHeight());
    }

    @FXML
    void cercaArtista(ActionEvent event) {
        print("Stai cercando l'artista " + barraDiRicerca.getText());
    }

    @FXML
    void userClicked(MouseEvent event) throws IOException {
        SceneHandler.getInstance().sceneLoader("ProfiloPage.fxml", root.getScene().getWidth(), root.getHeight());
    }

    @FXML
    void refreshClicked(MouseEvent event) {
        print("Hai cliccato refresh ma non è successo niente.");
    }

    public void riempiGalleria(Opera opera) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/simpleart/CardOpera.fxml"));
            Parent card = loader.load();

            CardOperaController controller = loader.getController();

            controller.setOpera(opera);

            galleria.getChildren().add(card);

        }catch (IOException a){
            print("Errore" + a);
        }
    }

    @FXML
    public void logOut(MouseEvent e) throws IOException {
        currentUser.clean();
        SceneHandler.getInstance().sceneLoader("LoginPage.fxml", root.getWidth(), root.getHeight());
    }

}
