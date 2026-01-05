package org.example.simpleart.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Memento {
    private ArrayList<ArrayList<Point>> collezioneDiInsiemi = null;
    private final double canvasHeight;
    private final double canvasWidth;
    private final Color currentColorBackground;


    public Memento(ArrayList<ArrayList<Point>> cdi, double canvasHeight, double canvasWidth, Color currentColorBackground){
        this.collezioneDiInsiemi = new ArrayList<>();
        for(int i = 0; i < cdi.size(); i++) {
            ArrayList<Point> temp = new ArrayList<>();
            for(int j = 0; j < cdi.get(i).size(); j++){
                Point p = new Point(cdi.get(i).get(j).getFirstX(),
                        cdi.get(i).get(j).getFirstY(),
                        cdi.get(i).get(j).getLastX(),
                        cdi.get(i).get(j).getLastY(),
                        cdi.get(i).get(j).getSize(),
                        cdi.get(i).get(j).getColor());
                temp.add(p);
            }
            this.collezioneDiInsiemi.add(temp);
        }
        //Tutto il casino sopra serve a fare la copia DEEP dell' Array. Altrimenti avremmo salvato i puntatori in memoria.

        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.currentColorBackground = currentColorBackground;
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

    public Color getCurrentColorBackground() {
        return currentColorBackground;
    }
}
