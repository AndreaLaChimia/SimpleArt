package org.example.simpleart.controller;

import javafx.scene.image.Image;
import org.example.simpleart.model.Database;
import org.example.simpleart.model.currentUser;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.simpleart.model.Print.print;

public class Query {

    public static void inserisciUtente(String name, String surname, String nickname, String email, String password) throws SQLException {
        Connection connection = Database.getConnection();
        String query = "INSERT INTO utente (nome, cognome, nickname, email, password) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);

        ps.setString(1, name);
        ps.setString(2, surname);
        ps.setString(3, nickname);
        ps.setString(4, email);
        ps.setString(5, password);

        int riuscito = ps.executeUpdate();
        ps.close();
        if(riuscito == 1) {
            print("Inserimento del nuovo utente avvenuto con successo!");
        }
        else
            print("Qualcosa è andato storto nell'inserimento del nuovo utente.");
    }

    public static boolean verificaPassword(String email, String password) throws SQLException {
        Connection cn = Database.getConnection();
        String query = "SELECT password FROM utente WHERE email = ?";
        PreparedStatement ps = cn.prepareStatement(query);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();
        String temp = null;

        while (rs.next()){
            temp = rs.getString(1);
        }

        if(temp != null)
            return BCrypt.checkpw(password, temp);

        return false;
    }

    public static void getAllFromEmail(String email) throws SQLException {
        Connection cn = Database.getConnection();
        String query = "SELECT nome, cognome, nickname, foto, descrizione FROM utente WHERE email = ?";
        PreparedStatement ps = cn.prepareStatement(query);

        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            print("Resultset non vuoto.");
            currentUser.setNome(rs.getString(1));
            currentUser.setCognome(rs.getString(2));
            currentUser.setNickname(rs.getString(3));
            currentUser.setEmail(email);
            byte[] bytes = rs.getBytes(4);
            if (bytes != null) {
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                Image image = new Image(bis);
                currentUser.setFoto(image);
            }
            currentUser.setDescrizione(rs.getString(5));
        }
    }
}
