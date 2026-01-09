package org.example.simpleart.model;

import javafx.scene.image.Image;

public class Opera {
    private final Image img;
    private final String titolo;
    private final String autore;
    private final Boolean visibilita;
    private int id;

    public Opera(Image img, String titolo, String autore, Boolean visibilita){
        this.img = img;
        this.titolo = titolo;
        this.autore = autore;
        this.visibilita = visibilita;
    }

    public Opera(Image img, String titolo, String autore, Boolean visibilita, int id){
        this.img = img;
        this.titolo = titolo;
        this.autore = autore;
        this.visibilita = visibilita;
        this.id = id;
    }

    public String getAutore() {
        return this.autore;
    }

    public String getTitolo() {
        return this.titolo;
    }

    public Image getImg() {
        return this.img;
    }

    public boolean getVisibilita() {
        return this.visibilita;
    }

    public int getId(){return this.id;}
}
