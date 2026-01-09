package org.example.simpleart.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.simpleart.model.Query;
import org.example.simpleart.model.Utente;

public class CardUtenteController {

    @FXML
    private Label emailLabel;

    @FXML
    private ImageView followersImg;

    @FXML
    private ImageView fotoCard;

    @FXML
    private Label nameSurnameLabel, followerLabel;

    @FXML
    private VBox root;

    @FXML
    private Label nicknameLabel;

    public void setUtente(Utente utente){
        nicknameLabel.setText(utente.getNickname());
        fotoCard.setImage(utente.getFoto());
        nameSurnameLabel.setText(utente.getNome() + " " + utente.getCognome());
        emailLabel.setText(utente.getEmail());
        followerLabel.setText(Query.contaFollower(utente.getEmail()) + " follower"
        );
    }

}
