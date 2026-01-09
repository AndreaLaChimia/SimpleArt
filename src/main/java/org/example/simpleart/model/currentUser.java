package org.example.simpleart.model;

import javafx.scene.image.Image;

public class currentUser {
    private static String nome;
    private static String cognome;
    private static String nickname;
    private static String email;
    private static Image foto;
    private static String descrizione;

    public currentUser(){
    }


    public static String getNome() {
        return nome;
    }

    public static String getCognome() {
        return cognome;
    }

    public static String getNickname() {
        return nickname;
    }

    public static String getEmail() {
        return email;
    }

    public static Image getFoto(){
        return foto;
    }

    public static String getDescrizione(){
        return descrizione;
    }

    public static void setNome(String nome) {
        currentUser.nome = nome;
    }

    public static void setCognome(String cognome) {
        currentUser.cognome = cognome;
    }

    public static void setNickname(String nickname) {
        currentUser.nickname = nickname;
    }

    public static void setEmail(String email) {
        currentUser.email = email;
    }

    public static void setFoto(Image image){
        currentUser.foto = image;
    }

    public static void setDescrizione(String descrizione){
        currentUser.descrizione = descrizione;
    }

    public static void clean(){
        currentUser.nome = null;
        currentUser.cognome = null;
        currentUser.nickname = null;
        currentUser.email = null;
        currentUser.foto = null;
        currentUser.descrizione = null;
    }
}
