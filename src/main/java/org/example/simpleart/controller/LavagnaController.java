package org.example.simpleart.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import org.example.simpleart.model.Point;

import java.util.ArrayList;
import java.util.Objects;

public class LavagnaController {

    @FXML
    private ImageView bidone, currentToolImg, gomma, livello, pennello, profiloButton, secchiello, testo, undoButton;

    @FXML
    private Canvas canvas;

    @FXML
    private Circle currentBrushStatus;

    @FXML
    private Button homeButton, pubblicaButton;

    @FXML
    private Slider scrollSize;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField sizeBoxNumber;

    private double firstX, firstY, lastX, lastY;

    String currentColorBackground = "WHITE";

    Tool currentTool;

    Color currentColorBrush;

    GraphicsContext gc;

    ArrayList<Point> insiemeDiPunti;

    ArrayList<ArrayList<Point>> collezioneDiInsiemi;

    Double sizeBrush;

    public enum Tool{
        Brush,
        Eraser,
        Bucket,
        Text
    }

    public void initialize(){
        if(collezioneDiInsiemi == null)
            collezioneDiInsiemi = new ArrayList<>();
        insiemeDiPunti = new ArrayList<>();
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
        //Qui inizializzo la lavagna
        canvas.setHeight(350);
        canvas.setWidth(350);

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.BLACK);
        currentColorBrush = Color.BLACK;

        gc.setLineWidth(sizeBrush);

        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineJoin(StrokeLineJoin.ROUND);

        canvas.setOnMousePressed(e-> {
            switch (currentTool) {
                case Brush -> {
                    insiemeDiPunti = new ArrayList<>();
                    firstX = e.getX();
                    firstY = e.getY();
                    Point p = new Point(firstX, firstY, firstX, firstY, sizeBrush, currentColorBrush);
                    insiemeDiPunti.add(p);
                    for (Point i : insiemeDiPunti) {
                        gc.setLineWidth(i.getSize());
                        gc.setStroke(i.getColor());
                        gc.strokeLine(i.getFirtX(), i.getFirstY(), i.getLastX(), i.getLastY());
                    }
                }

                case Eraser -> {

                }

                case Text -> {

                }

                case Bucket -> {

                }
            }
        });

        canvas.setOnMouseDragged(e->{
            switch (currentTool){
                case Brush -> {
                    lastX = e.getX();
                    lastY = e.getY();
                    Point p = new Point(firstX, firstY, e.getX(), e.getY(), sizeBrush, currentColorBrush);
                    insiemeDiPunti.add(p);
                    firstX = lastX;
                    firstY = lastY;
                    for (Point i : insiemeDiPunti) {
                        gc.setLineWidth(i.getSize());
                        gc.setStroke(i.getColor());
                        gc.strokeLine(i.getFirtX(), i.getFirstY(), i.getLastX(), i.getLastY());
                    }
                }
            }
        });

        canvas.setOnMouseReleased(e->{
            switch (currentTool) {
                case Brush -> {
                    collezioneDiInsiemi.add(insiemeDiPunti);
                }
                case Text -> {
                    
                }
            }
        });


    }

    public void clean(){
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Paint.valueOf(currentColorBackground));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    void addTextSelected(MouseEvent event) {
        currentTool = Tool.Text;
        currentToolImg.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/abc.png"))));
    }

    @FXML
    void binSelected(MouseEvent event) {
        gc.setFill(Paint.valueOf(currentColorBackground));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        collezioneDiInsiemi.clear();
    }

    @FXML
    void brushSelected(MouseEvent event) {
        currentTool = Tool.Brush;
        currentToolImg.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/pennello.png"))));
    }

    @FXML
    void bucketSelected(MouseEvent event) {
        currentTool = Tool.Bucket;
        currentToolImg.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/secchiello.png"))));
    }

    @FXML
    void changeSize(MouseEvent event) {
        gc.setLineWidth(scrollSize.getValue());
        currentBrushStatus.setRadius(scrollSize.getValue()/2);
        sizeBrush = scrollSize.getValue();
        sizeBoxNumber.setText(String.valueOf((int)scrollSize.getValue()));
    }

    @FXML
    void eraserSelected(MouseEvent event) {
        currentTool = Tool.Eraser;
        currentToolImg.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icone/gomma.png"))));
    }

    @FXML
    void goToHome(ActionEvent event) {

    }

    @FXML
    void goToProfile(MouseEvent event) {

    }

    @FXML
    void newLevelSelected(MouseEvent event) {

    }

    @FXML
    void postInGallery(ActionEvent event) {

    }

    @FXML
    void undo(MouseEvent event) {
            if(!collezioneDiInsiemi.isEmpty()){
                collezioneDiInsiemi.removeLast();
                clean();
                for(int i = 0; i < collezioneDiInsiemi.size(); i++){
                    for(int j = 0; j < collezioneDiInsiemi.get(i).size(); j++){
                        gc.setLineWidth(collezioneDiInsiemi.get(i).get(j).getSize());
                        gc.setStroke(collezioneDiInsiemi.get(i).get(j).getColor());
                        gc.strokeLine(collezioneDiInsiemi.get(i).get(j).getFirtX(),
                                collezioneDiInsiemi.get(i).get(j).getFirstY(),
                                collezioneDiInsiemi.get(i).get(j).getLastX(),
                                collezioneDiInsiemi.get(i).get(j).getLastY());
                    }
                }
            }
    }

    @FXML
    void colorSelected(ActionEvent event) {
        currentColorBrush = colorPicker.getValue();
        currentBrushStatus.setFill(currentColorBrush);
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

}

