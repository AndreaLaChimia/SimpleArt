package org.example.simpleart.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.example.simpleart.model.Opera;

import java.util.Objects;

import static org.example.simpleart.model.Print.print;

public class CardOperaController{

    @FXML
    private ImageView autoreFoto, imgLike;

    @FXML
    private Label autoreStringa, titoloOpera;

    @FXML
    private Button bottoneLike;

    @FXML
    private ImageView fotoCard;

    boolean miPiaceMesso = false;

    public void initialize(){
    }

    public void setOpera(Opera opera){
        fotoCard.setImage(opera.getImg());
        titoloOpera.setText(opera.getTitolo());
        autoreStringa.setText(opera.getAutore());
        bottoneLike.setText(String.valueOf(opera.getNumeroLike()));
    }

    public void cliccatoMiPiace(MouseEvent e){
        if(!miPiaceMesso) {
            miPiaceMesso = true;
            imgLike.setImage(new Image(Objects.requireNonNull(getClass().getResource("/icone/Like.png")).toExternalForm()));
            bottoneLike.setText(String.valueOf(Integer.parseInt(bottoneLike.getText()) + 1));
        }
        else {
            miPiaceMesso = false;
            imgLike.setImage(new Image(Objects.requireNonNull(getClass().getResource("/icone/Like empty.png")).toExternalForm()));
            bottoneLike.setText(String.valueOf(Integer.parseInt(bottoneLike.getText()) - 1));
        }
    }
}
