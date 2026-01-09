package org.example.simpleart.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.example.simpleart.model.Opera;
import org.example.simpleart.model.Query;

import java.sql.SQLException;
import java.util.Objects;

import static org.example.simpleart.model.Print.print;

public class CardOperaController{

    @FXML
    private ImageView autoreFoto, imgLike, fotoCard, cuoreIcona;

    @FXML
    private Label autoreStringa, titoloOpera;

    @FXML
    private Button numeroLike;

    private Image like = new Image(Objects.requireNonNull(getClass().getResource("/icone/like.png")).toExternalForm());
    private Image likeEmpty = new Image(Objects.requireNonNull(getClass().getResource("/icone/like empty.png")).toExternalForm());
    private Image cuore = new Image(Objects.requireNonNull(getClass().getResource("/icone/Heart full.png")).toExternalForm());
    private Image cuoreVuoto = new Image(Objects.requireNonNull(getClass().getResource("/icone/Heart empty.png")).toExternalForm());

    int idOpera;


    public void initialize(){
    }

    public void setOpera(Opera opera){
        fotoCard.setImage(opera.getImg());
        titoloOpera.setText(opera.getTitolo());
        autoreStringa.setText(opera.getAutore());
        if(Query.verificaSeLikeGiaPresente(idOpera)){
            imgLike.setImage(likeEmpty);
        }
        else imgLike.setImage(like);
        idOpera = opera.getId();
        numeroLike.setText(String.valueOf(Query.contaLike(idOpera)));

    }

    public void cliccatoMiPiace(MouseEvent e) throws SQLException {
        if(!Query.verificaSeLikeGiaPresente(idOpera)) {
            Query.mettiLike(idOpera);
            imgLike.setImage(like);
        }
        else {
            Query.togliLike(idOpera);
            imgLike.setImage(likeEmpty);
        }

        numeroLike.setText(String.valueOf(Query.contaLike(idOpera)));
    }

    public void cliccatoNome(MouseEvent e){
        print("Hai cliccato il nome dell'utente " + autoreStringa.getText());
    }

    public void cliccatoCuore(MouseEvent e){
    }
}
