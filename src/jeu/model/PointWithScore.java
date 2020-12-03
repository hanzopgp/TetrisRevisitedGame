package jeu.model;

import java.io.Serializable;

public class PointWithScore extends Point implements Serializable {

    private Point point;
    private int score;

    public PointWithScore(Point point, int score) {
        super(point.getX(), point.getY());
        this.score = score;
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
