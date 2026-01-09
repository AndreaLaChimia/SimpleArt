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
import javafx.stage.FileChooser;
import org.example.simpleart.model.Query;
import org.example.simpleart.model.SceneHandler;
import org.example.simpleart.model.currentUser;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static org.example.simpleart.model.Print.print;

public class ModificaProfiloController{

    @FXML
    private TextField barraDiRicerca;

    @FXML
    private Label canvasButton;

    @FXML
    private TextArea descrizioneField;

    @FXML
    private ImageView doorImgButton;

    @FXML
    private Button exitButton, annullaButton;

    @FXML
    private ImageView fotoProfilo;

    @FXML
    private Label homeButton;

    @FXML
    private ImageView homeImgButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField nicknameField;

    @FXML
    private BorderPane root;

    @FXML
    private Button salvaButton;

    @FXML
    private Button sfogliaButton;

    @FXML
    private TextField surnameField;

    @FXML
    private BorderPane stage;

    public void initialize(){
        if(currentUser.getFoto() != null)
            fotoProfilo.setImage(currentUser.getFoto());
        nameField.setText(currentUser.getNome());
        surnameField.setText(currentUser.getCognome());
        nicknameField.setText(currentUser.getNickname());
        if(currentUser.getDescrizione() != null)
            descrizioneField.setText(currentUser.getDescrizione());
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

    @FXML
    void save(MouseEvent event) throws SQLException, IOException {
        if(nameField.getText().equals(currentUser.getNome()) && surnameField.getText().equals(currentUser.getCognome())
        && nicknameField.getText().equals(currentUser.getNickname()) && fotoProfilo.getImage().equals(currentUser.getFoto())
        && descrizioneField.getText().equals(currentUser.getDescrizione())){
            print("Tutto uguale, no aggiornamenti.");
            SceneHandler.getInstance().sceneLoader("ProfiloPage.fxml", root.getWidth(), root.getHeight());
        }
        if(!nameField.getText().isEmpty() && !surnameField.getText().isEmpty() && !nicknameField.getText().isEmpty()){
            String nome = nameField.getText();
            String cognome = surnameField.getText();
            String nickname = nicknameField.getText();
            Image foto = fotoProfilo.getImage();
            String descrizione = descrizioneField.getText();
            if(Query.modificaDatiUtente(nome, cognome, nickname, foto, descrizione) == 1){
                currentUser.setNome(nome);
                currentUser.setCognome(cognome);
                currentUser.setNickname(nickname);
                currentUser.setFoto(foto);
                currentUser.setDescrizione(descrizione);
                SceneHandler.getInstance().sceneLoader("ProfiloPage.fxml", root.getWidth(), root.getHeight());
            }
        }
    }

    @FXML
    void annulla(MouseEvent event) throws IOException {
        SceneHandler.getInstance().sceneLoader("ProfiloPage.fxml", root.getWidth(), root.getHeight());
    }

    @FXML
    void openFolder(MouseEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Sfoglia");

        File file = fc.showOpenDialog(null);

        if(file != null){
            print("not null la foto");
            Image foto = new Image(file.toURI().toString());
            fotoProfilo.setImage(foto);
        }
    }

    @FXML
    public void backToProfile(MouseEvent e) throws IOException {
        annulla(e);
    }
}
