package jeu.model;

import java.io.Serializable;

/**
 * Classe contenant un point et un score associe
 */
public class PointWithScore extends Point implements Serializable {

    private int score;

    /**
     * Constructeur de l'object PointWithScore
     * @param point object Point
     * @param score le score associe
     */
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
