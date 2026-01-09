package org.example.simpleart.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.example.simpleart.model.Opera;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class CardOperaPropriaController{

    @FXML
    private Button downloadButton;

    @FXML
    private ImageView downloadImg;

    @FXML
    private ImageView fotoCard;

    @FXML
    private Button nascondiButton;

    @FXML
    private Label numeroLike;

    @FXML
    private ImageView occhioImg, buttonLikeImg;

    @FXML
    private Label titoloOpera;

    @FXML
    private VBox root;

    private Image like = new Image(Objects.requireNonNull(getClass().getResource("/icone/like.png")).toExternalForm());
    private Image likeEmpty = new Image(Objects.requireNonNull(getClass().getResource("/icone/like empty.png")).toExternalForm());

    private int idOpera;

    @FXML
    void hide(MouseEvent event) {

    }

    @FXML
    public void mettiLike(MouseEvent event) throws SQLException {
        if(!Query.verificaSeLikeGiaPresente(idOpera)) {
            Query.mettiLike(idOpera);
            buttonLikeImg.setImage(like);
        }
        else {
            Query.togliLike(idOpera);
            buttonLikeImg.setImage(likeEmpty);
        }

        numeroLike.setText(String.valueOf(Query.contaLike(idOpera)));
    }

    @FXML
    void scarica(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salva immagine");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        fileChooser.setInitialFileName("opera.png");

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                Image imageToSave = fotoCard.getImage();

                WritableImage writableImage = new WritableImage(
                        imageToSave.getPixelReader(),
                        (int) imageToSave.getWidth(),
                        (int) imageToSave.getHeight()
                );

                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);

                System.out.println("Immagine salvata in: " + file.getAbsolutePath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void setOpera(Opera opera){
        fotoCard.setImage(opera.getImg());
        titoloOpera.setText(opera.getTitolo());
        if(!opera.getVisibilita()) {
            occhioImg.setOpacity(0.5);
            nascondiButton.setText("Mostra opera");
        }
        numeroLike.setText(String.valueOf(Query.contaLike(opera.getId())));
        idOpera = opera.getId();
        if(!Query.verificaSeLikeGiaPresente(idOpera)){
            buttonLikeImg.setImage(likeEmpty);
        }
    }

}
