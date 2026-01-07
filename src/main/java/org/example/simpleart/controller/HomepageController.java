package org.example.simpleart.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.example.simpleart.model.Opera;
import org.example.simpleart.model.Print;
import org.example.simpleart.model.SceneHandler;
import org.example.simpleart.model.Print;
import java.io.IOException;
import java.util.Objects;

import static org.example.simpleart.model.Print.print;

public class HomepageController{

    @FXML
    private ImageView balloon, img1, img2, img3, img4;
    @FXML
    private Label canvasButton;
    @FXML
    private ImageView logo;
    @FXML
    private Label userButton;
    @FXML
    private HBox galleria;
    @FXML
    private TextField barraDiRicerca;



    public void initialize() throws IOException {
        SceneHandler.getInstance().getStage().setTitle("SimpleArt-Homepage");

        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/gioconda.png")));
        Opera opera = new Opera(img, "La Gioconda", "Leonardo Da Vinci", 9999);
        riempiGalleria(opera);

        Image img2 = new Image(Objects.requireNonNull(getClass().getResource("/opere/Woman.png")).toExternalForm());
        Opera opera2 = new Opera(img2, "La donna", "Andrea La Chimia", 20);
        riempiGalleria(opera2);

        Image img3 = new Image(Objects.requireNonNull(getClass().getResource("/opere/Rabbia.png")).toExternalForm());
        Opera opera3 = new Opera(img3, "Rabbia", "Andrea La Chimia", 9);
        riempiGalleria(opera3);

        Image img4 = new Image(Objects.requireNonNull(getClass().getResource("/opere/FruitsAndVegetable.png")).toExternalForm());
        Opera opera4 = new Opera(img4, "Frutta e verdura", "Andrea La Chimia", 11);
        riempiGalleria(opera4);



    }

    @FXML
    void canvasClicked(MouseEvent event) throws IOException {
        SceneHandler.getInstance().sceneLoader("Lavagna.fxml");
    }

    @FXML
    void cercaArtista(ActionEvent event) {
        print("Stai cercando l'artista " + barraDiRicerca.getText());
    }

    @FXML
    void userClicked(MouseEvent event) {
        print("Hai cliccato User ma non è successo niente.");
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


}
