package org.example.simpleart.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Originator {
    private ArrayList<ArrayList<Point>> collezioneDiInsiemi;
    private double canvasHeight;
    private double canvasWidth;
    private Color backgroundColor;

    public Originator(){
        this.collezioneDiInsiemi = new ArrayList<>();
    }

    public void setState(ArrayList<ArrayList<Point>> a, double b, double c, Color d){
        this.collezioneDiInsiemi = a;
        this.canvasHeight= b;
        this.canvasWidth = c;
        this.backgroundColor = d;
    }

    public Memento createMemento(){
        return new Memento(this.collezioneDiInsiemi, this.canvasHeight, this.canvasWidth, this.backgroundColor);
    }

    public void restore(Memento m) {
        this.collezioneDiInsiemi = m.getCollezioneDiInsiemi();
        this.canvasHeight = m.getCanvasHeight();
        this.canvasWidth = m.getCanvasWidth();
        this.backgroundColor = m.getCurrentColorBackground();
    }

    public ArrayList<ArrayList<Point>> getCollezioneDiInsiemi() {
        return collezioneDiInsiemi;
    }

    public double getCanvasHeight() {
        return canvasHeight;
    }

    public double getCanvasWidth() {
        return canvasWidth;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }
}
