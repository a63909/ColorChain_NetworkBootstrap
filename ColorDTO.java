package com.example.tamarashadow.core;

public class ColorDTO {
    private int red;
    private int green;
    private int blue;
    private int x;
    private int y;

    public ColorDTO(int red, int green, int blue, int x, int y) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.x = x;
        this.y = y;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toSimpleString() {
        return red + "," + green + "," + blue + "," + x + "," + y;
    }

    public int getColor() {
        return android.graphics.Color.rgb(red, green, blue);
    }

    @Override
    public String toString() {
        return "ColorDTO{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
