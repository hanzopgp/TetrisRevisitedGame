package jeu.model;

import javax.swing.*;

/**
 * Classe contenant un point avec ses coordonnes x et y
 */
public class Point extends JPanel implements Cloneable{

    private int x;
    private int y;

    /**
     * Constructeur de l'object Point
     * @param x coordonnee x
     * @param y coordonne y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Clone l'object Point
     * @return object Point clone
     */
    @Override
    public Point clone(){
        Object o = null;
        try{
            o = super.clone();
        }catch(CloneNotSupportedException cnse){
            cnse.printStackTrace(System.err);
        }
        return (Point)o;
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public int getX() { return this.x; }

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
