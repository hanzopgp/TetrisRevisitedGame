package jeu.factory;

import jeu.model.Point;

import java.util.ArrayList;

public interface PieceInterface {

    ArrayList<Point> getCurrentPiece();

    void rotate(boolean direction);

    PieceInterface translation(int direction);

    String getFilling();

    PieceInterface getCopy();
}
