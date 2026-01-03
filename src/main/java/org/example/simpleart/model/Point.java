package org.example.simpleart.model;
import javafx.scene.paint.*;

public class Point {
    private double x;
    private double y;
    private double X;
    private double Y;
    private Double size;
    private Color color;

    public Point(double x, double y, double X, double Y, Double size, Color color){
        this.x = x;
        this.y = y;
        this.X = X;
        this.Y = Y;
        this.size = size;
        this.color = color;
    }

    public double getFirtX(){return this.x;}

    public double getFirstY(){return this.y;}

    public double getLastX(){return this.X;}

    public double getLastY(){return this.Y;}

    public Double getSize(){return this.size;}

    public Color getColor(){return this.color;}



}
