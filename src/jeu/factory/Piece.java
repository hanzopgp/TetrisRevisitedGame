package jeu.factory;

import jeu.model.Point;

import java.util.ArrayList;

/**
 * Interface contenant les methodes
 * les plus essentielles pour toutes les pieces
 */
public interface Piece {

    /**
     * Methode retournant la piece dans son etat actuelle
     * @return piece actuelle
     */
    ArrayList<Point> getCurrentPiece();

    /**
     * Methode permettant d'effectuer une rotation sur la piece actuelle
     * selon un sens
     * @param direction - Sens de direction horaire ou anti-horaire
     */
    void rotate(boolean direction);

    /**
     * Methode permettant de bouger la piece vers une direction
     * @param direction - Direction souhaitee (haut, bas, droite, gauche)
     * @return Piece bougee
     */
    Piece translation(int direction);

    /**
     * Methode permettant de recuperer le caractere representant la piece
     * actuelle
     * @return Caractere
     */
    String getFilling();

    /**
     * Methode permettant d'effectuer une copie de la piece actuelle
     * @return Clone de la piece
     */
    Piece clone();

    /**
     * Methode permettant de retourner le point referent d'une piece globale
     * @return Point referent
     */
    Point getCentralPiece();

    /**
     * Methode permettant de definir le point referent d'une piece globale
     * @param centralPiece - Point referent
     */
    void setCentralPiece(Point centralPiece);

}
