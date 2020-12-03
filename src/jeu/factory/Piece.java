package jeu.factory;

import jeu.model.Point;

import java.util.ArrayList;

public interface Piece {

    ArrayList<Point> getCurrentPiece();

    void rotate(boolean direction);

    Piece translation(int direction);

    String getFilling();

    Piece clone();

    Point getCentralPiece();

    void setCentralPiece(Point centralPiece);

}
