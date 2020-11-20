package jeu.model;

import java.io.Serializable;

public class ScoreWithVal implements Serializable {

    private int score, x, y;

    public ScoreWithVal(int score, int x, int y) {
        this.score = score;
        this.x = x;
        this.y = y;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
