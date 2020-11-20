package jeu.model;

import javax.swing.*;

public class Point extends JPanel {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
