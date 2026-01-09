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
    private ImageView autoreFoto, imgLike, fotoCard, cuoreIcona;

    @FXML
    private Label autoreStringa, titoloOpera;

    @FXML
    private Button bottoneLike;

    boolean miPiaceMesso = false;
    boolean cuorePieno = false;

    public void initialize(){
    }

    public void setOpera(Opera opera){
        fotoCard.setImage(opera.getImg());
        titoloOpera.setText(opera.getTitolo());
        autoreStringa.setText(opera.getAutore());
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

    public void cliccatoNome(MouseEvent e){
        print("Hai cliccato il nome dell'utente " + autoreStringa.getText());
    }

    public void cliccatoCuore(MouseEvent e){
        print("Hai cliccato il cuore");
        if(cuorePieno){
            cuoreIcona.setImage(new Image(Objects.requireNonNull(getClass().getResource("/icone/Heart empty.png")).toExternalForm()));
            cuorePieno = false;
        }
        else{
            cuoreIcona.setImage(new Image(Objects.requireNonNull(getClass().getResource("/icone/Heart full.png")).toExternalForm()));
            cuorePieno = true;
        }


    }
}
