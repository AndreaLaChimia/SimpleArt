package org.example.simpleart.model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.simpleart.model.Print.print;

public class Query {

    public static void inserisciUtente(String name, String surname, String nickname, String email, String password) throws SQLException {
        Connection cn = Database.getConnection();
        String query = "INSERT INTO utente (nome, cognome, nickname, email, password) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = cn.prepareStatement(query);

        ps.setString(1, name);
        ps.setString(2, surname);
        ps.setString(3, nickname);
        ps.setString(4, email);
        ps.setString(5, password);

        int riuscito = ps.executeUpdate();
        ps.close();
        cn.close();
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

        if(temp != null) {
            ps.close();
            cn.close();
            return BCrypt.checkpw(password, temp);
        }

        ps.close();
        cn.close();
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
        ps.close();
        cn.close();
    }

    public static int modificaDatiUtente(String nome, String cognome, String nickname, Image foto, String descrizione) throws SQLException, IOException {
        Connection cn = Database.getConnection();
        String query = "UPDATE utente SET nome = ?, cognome = ?, nickname = ?, foto = ?, descrizione = ? where email = ?";
        PreparedStatement ps = cn.prepareStatement(query);
        ps.setString(1, nome);
        ps.setString(2, cognome);
        ps.setString(3, nickname);
        byte[] array = imageToBytes(foto);
        ps.setBytes(4, array);
        ps.setString(5, descrizione);
        ps.setString(6, currentUser.getEmail());

        return ps.executeUpdate();

    }

    public static boolean isEmailAddressFree(String email) throws SQLException {
        Connection cn = Database.getConnection();
        String query = "SELECT email FROM utente WHERE email = ?";
        PreparedStatement ps = cn.prepareStatement(query);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            ps.close();
            cn.close();
            return false;
        }

        ps.close();
        cn.close();
        return true;
    }

    public static void addArt(Opera opera) throws SQLException, IOException {
        String query = "INSERT INTO opera (titolo, autore, visibilita, dati) VALUES (?, ?, ?, ?)";

        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {

            int vis = opera.getVisibilita() ? 1 : 0;

            ps.setString(1, opera.getTitolo());
            ps.setString(2, currentUser.getEmail());
            ps.setInt(3, vis);

            byte[] array = imageToBytes(opera.getImg());
            ps.setBytes(4, array);

            ps.executeUpdate();
        }
    }


    public static ArrayList<Opera> getAllArtOfAnArtist(String mail) throws SQLException {
        String query = "SELECT titolo, visibilita, dati, id FROM opera WHERE autore = ?";
        ArrayList<Opera> arrayOpere = new ArrayList<>();

        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {

            ps.setString(1, mail);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String titolo = rs.getString("titolo");
                    boolean visibilita = rs.getInt("visibilita") == 1;

                    byte[] bytes = rs.getBytes("dati");
                    Image image = null;

                    if (bytes != null) {
                        image = new Image(new ByteArrayInputStream(bytes));
                    }

                    int id = rs.getInt("id");

                    Opera temp = new Opera(image, titolo, mail, visibilita, id);
                    arrayOpere.add(temp);
                }
            }
        }

        return arrayOpere;
    }

    public static int contaLike(int id) {
        String query = "SELECT COUNT(*) FROM `like` WHERE opera = ?";

        try (Connection cn = Database.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verificaSeLikeGiaPresente(int id){
        String query = "SELECT EXISTS(SELECT 1 FROM `like` WHERE utente = ? AND opera = ?)";
        try(
                Connection cn = Database.getConnection();
                PreparedStatement ps = cn.prepareStatement(query);
                ){
            ps.setString(1, currentUser.getEmail());
            ps.setInt(2, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next())
                return rs.getInt(1) == 1;

        }catch (SQLException s){
            print("Errore controllo se like già presente o meno.");
        }
        return false;
    }

    public static void mettiLike(int id) throws SQLException {
        String query = "INSERT INTO `like` (utente, opera) VALUES (?, ?)";

        try(    Connection cn = Database.getConnection();
                PreparedStatement ps = cn.prepareStatement(query);)
        {
            ps.setString(1, currentUser.getEmail());
            ps.setInt(2, id);
            ps.executeUpdate();
            print("like messo.");

        }catch (SQLException s){ s.printStackTrace();
        print("Errore inserimento like.");}
    }

    public static void togliLike(int id){
        String query = "DELETE FROM `like` WHERE utente = ? and opera = ?";

        try(
                Connection cn = Database.getConnection();
                PreparedStatement ps = cn.prepareStatement(query);
                ){
            ps.setString(1, currentUser.getEmail());
            ps.setInt(2, id);

            ps.executeUpdate();

        }catch (SQLException s){
            print("Errore nel togliere il like.");
        }
    }

    public static List<Opera> getOpereCasuali() {
        String query = "SELECT id, titolo, autore, visibilita, dati FROM opera ORDER BY RANDOM() LIMIT 4";
        List<Opera> opere = new ArrayList<>();

        try (
                Connection cn = Database.getConnection();
                PreparedStatement ps = cn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String titolo = rs.getString("titolo");
                String autore = rs.getString("autore");
                boolean visibilita = rs.getBoolean("visibilita");

                byte[] blob = rs.getBytes("dati");
                Image img = new Image(new ByteArrayInputStream(blob));

                Opera opera = new Opera(img, titolo, autore, visibilita, id);
                opere.add(opera);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return opere;
    }

    public static String contaFollower(String email) {
        String query = "SELECT COUNT(*) FROM nelCuore WHERE utente2 = ?";

        try (
                Connection cn = Database.getConnection();
                PreparedStatement ps = cn.prepareStatement(query)
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Integer.toString(rs.getInt(1));
            }

        } catch (SQLException e) {
            print("Errore nel contare i follower: " + e.getMessage());
        }

        return "0";
    }

    public static List<Utente> getFollower(String email) {
        String query = "SELECT u.nome, u.cognome, u.nickname, u.email, u.foto, u.descrizione FROM nelCuore as nc JOIN utente as u ON nc.utente2 = u.email WHERE nc.utente1 = ?";

        List<Utente> utenti = new ArrayList<>();

        try (
                Connection cn = Database.getConnection();
                PreparedStatement ps = cn.prepareStatement(query)
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String nickname = rs.getString("nickname");
                String mail = rs.getString("email");
                print("Aggiunto " + mail);
                String descrizione = rs.getString("descrizione");

                byte[] blob = rs.getBytes("foto");
                Image foto;

                if (blob != null) {
                    foto = new Image(new ByteArrayInputStream(blob));
                } else {
                    foto = new Image(Objects.requireNonNull(
                            Query.class.getResource("/icone/artist.png")
                    ).toExternalForm());
                }

                Utente temp = new Utente(nome, cognome, nickname, mail, foto, descrizione);
                utenti.add(temp);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return utenti;
    }



    public static byte[] imageToBytes(Image image) throws IOException {

        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", baos);
        return baos.toByteArray();
    }


}
