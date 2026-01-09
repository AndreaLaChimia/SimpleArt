package org.example.simpleart.model;

import javafx.scene.image.Image;

public class Opera {
    private final Image img;
    private final String titolo;
    private final String autore;
    private final Boolean visibilita;



    public Opera(Image img, String titolo, String autore, Boolean visibilita){
        this.img = img;
        this.titolo = titolo;
        this.autore = autore;
        this.visibilita = visibilita;
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

    public boolean getVisibilita() {
        return visibilita;
    }
}
