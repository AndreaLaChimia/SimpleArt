package org.example.simpleart.model;

import javafx.scene.image.Image;

public class Utente {
    private final String nome;
    private final String cognome;
    private final String nickname;
    private final String email;
    private final Image foto;
    private final String descrizione;

    public Utente(String nome, String cognome, String nickname, String email, Image foto, String descrizione) {
        this.nome = nome;
        this.cognome = cognome;
        this.nickname = nickname;
        this.email = email;
        this.foto = foto;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public Image getFoto() {
        return foto;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
