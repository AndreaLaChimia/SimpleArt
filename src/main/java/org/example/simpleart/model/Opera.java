package org.example.simpleart.model;

import javafx.scene.image.Image;

public class Opera {
    private final Image img;
    private final String titolo;
    private final String autore;
    private final int numeroLike;


    public Opera(Image img, String titolo, String autore, int numeroLike){
        this.img = img;
        this.titolo = titolo;
        this.autore = autore;
        this.numeroLike = numeroLike;
    }

    public int getNumeroLike() {
        return numeroLike;
    }

    public String getAutore() {
        return autore;
    }

    public String getTitolo() {
        return titolo;
    }

    public Image getImg() {
        return img;
    }
}
