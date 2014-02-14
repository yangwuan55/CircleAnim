package com.example.CircleTest;

/**
 * Created by yangmengrong on 14-2-12.
 */
public class Circle {
    private int angle;
    private int radius;
    private int centerX;
    private int centerY;


    public Circle(int radius, int centerX , int centerY) {
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getX() {
        int x = getX(angle);
        return x;
    }

    public int getX(int angle) {
        return (int) (radius*Math.cos(Math.toRadians(angle))+centerX);
    }

    public int getY() {
        int y = getY(angle);
        return y;
    }

    public int getY(int angle) {
        return (int) (radius*Math.sin(Math.toRadians(angle)) + centerY);
    }

}
