package org.example.simpleart.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.layout.StackPane;

import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import javafx.stage.FileChooser;
import org.example.simpleart.model.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static org.example.simpleart.model.Print.print;

public class LavagnaController {
    @FXML
    private BorderPane root;

    @FXML
    private ImageView bidone, currentToolImg, gomma, livello, pennello, profiloButton, secchiello, matitaImg, undoButton;

    @FXML
    private Canvas canvas;

    @FXML
    private Circle currentBrushStatus;

    @FXML
    private Button homeButton, pubblicaButton, exitButton;

    @FXML
    private Slider scrollSize;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField sizeBoxNumber, nameImgField, altezzaBox, larghezzaBox, barraDiRicerca;

    private double firstX, firstY, lastX, lastY;

    Color currentColorBackground = Color.WHITE;

    Tool currentTool;

    Color currentColor;

    GraphicsContext gc;

    ArrayList<Point> insiemeDiPunti;

    ArrayList<ArrayList<Point>> collezioneDiInsiemi;

    @FXML
    StackPane stackPane;

    Double sizeBrush;

    Boolean piazzatoPuntoA = false;

    double tolleranza = 10.0; //La tolleranza è in pratica la punta della gomma.

    double tempX;
    double tempY;

    Originator originator;
    CareTaker careTaker;
    String nomeOpera = null;
    Boolean disegnoInCorso = false;

    public enum Tool{
        Brush,
        Eraser,
        Bucket,
        Pencil,
        Line;
    }

    public void initialize(){

        SceneHandler.getInstance().getStage().setTitle("SimpleArt - Canvas");
        settings(); //Imposto diversi aspetti legati all'interfaccia e altro.

        settingsCanvas(); //Imposto i valori di default della lavagna.

        clickOnCanvas(); //Qui è dove si decide cosa avviene al tocco della lavagna.

        gestisciResizeScene();

    }

    public void clean(){
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(currentColorBackground);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


    @FXML
    void matitaSelected(MouseEvent event) {
        currentTool = Tool.Pencil;
        currentToolImg.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/pencil.png"))));
        scrollSize.setValue(1);
        currentBrushStatus.setFill(currentColor);
        currentBrushStatus.setRadius(1);
        sizeBoxNumber.setText("1");
    }

    @FXML
    void binSelected(MouseEvent event) {
        createSnapshot();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        currentColorBackground = Color.WHITE;
        collezioneDiInsiemi.clear();
    }

    @FXML
    void brushSelected(MouseEvent event) {
        currentTool = Tool.Brush;
        currentToolImg.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/pennello.png"))));
        currentBrushStatus.setFill(currentColor);
        scrollSize.setValue((int) sizeBrush.longValue());
        currentBrushStatus.setRadius(scrollSize.getValue()/2);
        sizeBoxNumber.setText(String.valueOf((int)sizeBrush.longValue()));
    }

    @FXML
    void bucketSelected(MouseEvent event) {
        currentTool = Tool.Bucket;
        currentToolImg.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/secchiello.png"))));
    }

    @FXML
    void changeSize(MouseEvent event) {
        switch (currentTool){
            case Tool.Brush, Tool.Line -> {
                gc.setLineWidth(scrollSize.getValue());
                sizeBrush = scrollSize.getValue();

            }
            case Tool.Eraser -> {
                tolleranza = scrollSize.getValue();
            }
            case Tool.Pencil -> {
                scrollSize.setValue(1);
            }

        }
        currentBrushStatus.setRadius(scrollSize.getValue()/2);
        sizeBoxNumber.setText(String.valueOf((int)scrollSize.getValue()));

    }

    @FXML
    void insertedNumber(ActionEvent event) {
        try {
            double value = Double.parseDouble(sizeBoxNumber.getText());

            value = Math.max(1, Math.min(value, 65));
            sizeBrush = value;

            currentBrushStatus.setRadius(sizeBrush / 2);

        } catch (NumberFormatException e) {
            System.out.println("Input non valido: " + sizeBoxNumber.getText());
        }
    }

    @FXML
    void eraserSelected(MouseEvent event) {
        currentTool = Tool.Eraser;
        currentToolImg.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/gomma.png"))));
        scrollSize.setValue(tolleranza);
        currentBrushStatus.setFill(Color.GRAY);
        currentBrushStatus.setRadius(tolleranza/2);
        sizeBoxNumber.setText(String.valueOf((int)tolleranza));
    }

    @FXML
    void goToHome(MouseEvent event) throws IOException {
        SceneHandler.getInstance().sceneLoader("Homepage.fxml", root.getWidth(), root.getHeight());
    }

    @FXML
    public void cercaArtista(ActionEvent event) {
        print("Hai provato a cercare il profilo di " + barraDiRicerca.getText());
    }

    @FXML
    void goToProfile(MouseEvent event) throws IOException {
        print(String.valueOf(disegnoInCorso));
        if(disegnoInCorso){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione!");
            alert.setHeaderText("Stai lasciando la lavagna senza avere pubblicato l'opera." +
                    "\nL'opera andrà persa, sei sicuro di volere uscire?");
            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            ButtonType bottoneSchiacciato = alert.showAndWait().orElse(ButtonType.CANCEL);

            if(bottoneSchiacciato == ButtonType.OK)
                SceneHandler.getInstance().sceneLoader("ProfiloPage.fxml", root.getWidth(), root.getHeight());
            else
                return;
        }
        else
            SceneHandler.getInstance().sceneLoader("ProfiloPage.fxml", root.getWidth(), root.getHeight());
    }

    @FXML
    void makeLine(MouseEvent event) {
        currentTool = Tool.Line;
        currentToolImg.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/aToB.png"))));
        scrollSize.setValue(sizeBrush);
        currentBrushStatus.setFill(currentColor);
        currentBrushStatus.setRadius(sizeBrush/2);
        sizeBoxNumber.setText(String.valueOf((int)sizeBrush.longValue()));
    }

    @FXML
    void postInGallery(MouseEvent a) throws SQLException, IOException {
        if(!nameImgField.getText().isEmpty()) {
            Image img = canvas.snapshot(null, null);
            Opera temp = new Opera(img, nameImgField.getText(), currentUser.getEmail(), true);
            Query.addArt(temp);
            print("Opera inserita.");
            disegnoInCorso = false;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Pubblicazione");
            alert.setHeaderText("L'opera è stata pubblicata con successo!\nPuoi trovarla nella tua galleria.");
            alert.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Errore");
            alert.setHeaderText("Non puoi salvare un'opera senza dargli un titolo.");
            alert.showAndWait();
        }

    }

    @FXML
    void download(MouseEvent event) throws IOException {
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, writableImage);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salva immagine");
        fileChooser.setInitialFileName("Opera");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File file = fileChooser.showSaveDialog(null);

        if(file != null) {
            try {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(bufferedImage, "png", file);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Download");
                alert.setHeaderText("L'opera è stata scaricata con successo!");
                alert.show();

            } catch (IOException a) {
                print("Errore avvenuto durante il salvataggio: " + a);
            }
        }
    }

    @FXML
    void undo(MouseEvent event) {
        if(careTaker.hasUndo()){
            System.out.println("Prima dell'undo " + careTaker.hasUndo());
            originator.restore(careTaker.undo());
            collezioneDiInsiemi = originator.getCollezioneDiInsiemi();
            canvas.setHeight(originator.getCanvasHeight());
            canvas.setWidth(originator.getCanvasWidth());
            currentColorBackground = originator.getBackgroundColor();
            System.out.println("Dopo undo " + careTaker.hasUndo());
            redrawOnCanvas();
        }

        if(!careTaker.hasUndo())
            undoButton.setOpacity(0.5);
    }

    @FXML
    void colorSelected(ActionEvent event) {
        currentColor = colorPicker.getValue();
        currentBrushStatus.setFill(currentColor);
    }

    public void settings(){
        SceneHandler.getInstance().getStage().setTitle("SimpleArt - Canvas");
        collezioneDiInsiemi = new ArrayList<>();

        undoButton.setOpacity(0.5);

        colorPicker.setValue(Color.BLACK);
        currentTool = Tool.Brush;
        sizeBrush = 6.0;
        scrollSize.setValue(sizeBrush);
        currentBrushStatus.setRadius(sizeBrush/2);
        currentToolImg.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/pennello.png"))));
        //Qui mi sto impostando i valori minimi e massimi della "scrollSize"
        scrollSize.setMin(1);
        scrollSize.setMax(65);
        sizeBoxNumber.setText(String.valueOf((int)scrollSize.getValue()));

        originator = new Originator();
        careTaker = new CareTaker();

        /*originator.setState(collezioneDiInsiemi, 350, 350, currentColorBackground);
        careTaker.save(originator.createMemento());*/
    }

    public void settingsCanvas(){
        canvas.setHeight(350);
        canvas.setWidth(350);
        altezzaBox.setText(String.valueOf((int)canvas.getHeight()));
        larghezzaBox.setText(String.valueOf((int)canvas.getWidth()));

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.BLACK);
        currentColor = Color.BLACK;

        gc.setLineWidth(sizeBrush);

        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineJoin(StrokeLineJoin.ROUND);

    }

    public void clickOnCanvas(){
        print("Hai cliccato sul canvas");
        canvas.setOnMousePressed(e-> {
            disegnoInCorso = true;
            createSnapshot();
            print("Aggiunto un memento.");
            switch (currentTool) {
                case Brush -> {
                    insiemeDiPunti = new ArrayList<>();
                    firstX = e.getX();
                    firstY = e.getY();
                    Point p = new Point(firstX, firstY, firstX, firstY, sizeBrush, currentColor);
                    insiemeDiPunti.add(p);
                    for (Point i : insiemeDiPunti) {
                        gc.setLineWidth(i.getSize());
                        gc.setStroke(i.getColor());
                        gc.strokeLine(i.getFirstX(), i.getFirstY(), i.getLastX(), i.getLastY());
                    }
                }
                case Eraser -> {
                    usoGomma(e);
                }
                case Bucket -> {
                    gc.setFill(currentColor);
                    currentColorBackground = currentColor;
                    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    redrawOnCanvas();
                }
                case Line ->{
                    if(!piazzatoPuntoA) {
                        careTaker.undo();
                        tempX = e.getX();
                        tempY = e.getY();
                        gc.setStroke(Color.RED);
                        gc.setLineWidth(sizeBrush);
                        print(sizeBrush.toString());
                        gc.strokeLine(tempX, tempY, tempX, tempY);
                        piazzatoPuntoA = true;
                    }
                    else {
                        insiemeDiPunti = new ArrayList<>();
                        gc.setStroke(currentColor);
                        gc.setLineWidth(sizeBrush);
                        gc.strokeLine(tempX, tempY, e.getX(), e.getY());
                        Point p = new Point(tempX, tempY, e.getX(), e.getY(), sizeBrush, currentColor);
                        insiemeDiPunti.add(p);
                        collezioneDiInsiemi.add(insiemeDiPunti);

                        piazzatoPuntoA = false;
                    }
                }
                case Pencil -> {
                    {
                        insiemeDiPunti = new ArrayList<>();
                        firstX = e.getX();
                        firstY = e.getY();
                        Point p = new Point(firstX, firstY, firstX, firstY, 1.0, currentColor);
                        insiemeDiPunti.add(p);
                        for (Point i : insiemeDiPunti) {
                            gc.setLineWidth(1);
                            gc.setStroke(i.getColor());
                            gc.strokeLine(i.getFirstX(), i.getFirstY(), i.getLastX(), i.getLastY());
                        }
                    }
                }
            }
        });

        canvas.setOnMouseDragged(e->{
            switch (currentTool){
                case Brush -> {
                    lastX = e.getX();
                    lastY = e.getY();
                    Point p = new Point(firstX, firstY, e.getX(), e.getY(), sizeBrush, currentColor);
                    insiemeDiPunti.add(p);
                    firstX = lastX;
                    firstY = lastY;
                    for (Point i : insiemeDiPunti) {
                        gc.setLineWidth(i.getSize());
                        gc.setStroke(i.getColor());
                        gc.strokeLine(i.getFirstX(), i.getFirstY(), i.getLastX(), i.getLastY());
                    }
                }
                case Eraser -> {
                    usoGomma(e);
                }
                case Pencil -> {
                    lastX = e.getX();
                    lastY = e.getY();
                    Point p = new Point(firstX, firstY, e.getX(), e.getY(), 1.0, currentColor);
                    insiemeDiPunti.add(p);
                    firstX = lastX;
                    firstY = lastY;
                    for (Point i : insiemeDiPunti) {
                        gc.setLineWidth(1);
                        gc.setStroke(i.getColor());
                        gc.strokeLine(i.getFirstX(), i.getFirstY(), i.getLastX(), i.getLastY());
                    }
                }
            }
        });

        canvas.setOnMouseReleased(e->{
            switch (currentTool) {
                case Brush, Pencil -> {
                    collezioneDiInsiemi.add(insiemeDiPunti);
                }
            }
        });
    }

    public void redrawOnCanvas(){
        clean();
        gc.setFill(currentColorBackground);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(int i = 0; i < collezioneDiInsiemi.size(); i++){
            for(int j = 0; j < collezioneDiInsiemi.get(i).size(); j++){
                gc.setLineWidth(collezioneDiInsiemi.get(i).get(j).getSize());
                gc.setStroke(collezioneDiInsiemi.get(i).get(j).getColor());
                gc.strokeLine(collezioneDiInsiemi.get(i).get(j).getFirstX(),
                        collezioneDiInsiemi.get(i).get(j).getFirstY(),
                        collezioneDiInsiemi.get(i).get(j).getLastX(),
                        collezioneDiInsiemi.get(i).get(j).getLastY());
            }
        }
        print("Tutto ridisegnato.");
    }

    @FXML
    void setCanvasWidthAndHeight(ActionEvent event) {
        try{
            double nuovaLarghezza  = Double.parseDouble(larghezzaBox.getText());
            double nuovaAltezza = Double.parseDouble(altezzaBox.getText());

            /*if (root.getWidth() == 900 && root.getHeight() == 600){
                if(nuovaLarghezza > 600)
                    nuovaLarghezza = 600;
                if(nuovaAltezza > 400)
                    nuovaAltezza = 400;
            }
            createSnapshot();*/
            if(root.getWidth() - nuovaLarghezza < 300)
                nuovaLarghezza = root.getWidth() - 300;

            if(root.getHeight() - nuovaAltezza < 200)
                nuovaAltezza = root.getHeight() - 200;

            stackPane.setPrefWidth(nuovaLarghezza);
            stackPane.setPrefHeight(nuovaAltezza);

            canvas.setWidth(nuovaLarghezza);
            canvas.setHeight(nuovaAltezza);

            redrawOnCanvas();
        } catch (NumberFormatException e) {
            print("Inserisci dei numeri validi in entrambi i box numerici.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Inserisci dei valori validi in px per le dimensioni della tela.");
            alert.showAndWait();
        }

    }

    public void usoGomma(MouseEvent e) {
        double gommaX = e.getX();
        double gommaY = e.getY();

        for (int i = 0; i < collezioneDiInsiemi.size(); i++) {
            var lista = collezioneDiInsiemi.get(i);

            for (int j = 0; j < lista.size(); j++) {
                var s = lista.get(j);

                double d = distanzaPuntoSegmento(
                        gommaX, gommaY,
                        s.getFirstX(), s.getFirstY(),
                        s.getLastX(), s.getLastY()
                );

                if (d <= tolleranza) {
                    lista.remove(j);
                    redrawOnCanvas();
                    return;
                }
            }
        }
    }

    private double distanzaPuntoSegmento(double px, double py, double x1, double y1, double x2, double y2) {

        double dx = x2 - x1;
        double dy = y2 - y1;

        if (dx == 0 && dy == 0) {
            return Math.hypot(px - x1, py - y1);
        }

        double t = ((px - x1) * dx + (py - y1) * dy) / (dx*dx + dy*dy);
        t = Math.max(0, Math.min(1, t));

        double projX = x1 + t * dx;
        double projY = y1 + t * dy;

        return Math.hypot(px - projX, py - projY);
    }

    public void createSnapshot(){
        originator.setState(collezioneDiInsiemi, canvas.getHeight(), canvas.getWidth(), currentColorBackground);
        careTaker.save(originator.createMemento());
        undoButton.setOpacity(1);
    }

    public void gestisciResizeScene(){
        root.sceneProperty().addListener((obsScene, oldScene, newScene) -> {
            if (newScene != null) {

                newScene.widthProperty().addListener((obs, oldW, newW) -> {
                    if((double)newW - canvas.getWidth() < 300){
                        canvas.setWidth((double) newW - 300);
                    }
                });

                newScene.heightProperty().addListener((obs, oldH, newH) -> {
                    if((double)newH - canvas.getHeight() < 200){
                        canvas.setHeight((double) newH - 200);
                    }
                });
            }
        });

    }

    @FXML
    public void logOut(MouseEvent e) throws IOException {
        currentUser.clean();
        SceneHandler.getInstance().sceneLoader("LoginPage.fxml", root.getWidth(), root.getHeight());
    }

}

