package jeu.computer;

import jeu.factory.PieceInterface;

public class Move {

    private PieceInterface piece;
    private String typeMove;

    public Move(PieceInterface piece, String typeMove) {
        this.piece = piece;
        this.typeMove = typeMove;
    }

    public PieceInterface getPiece() {
        return piece;
    }

    public void setPiece(PieceInterface piece) {
        this.piece = piece;
    }

    public String getTypeMove() {
        return typeMove;
    }

    public void setTypeMove(String typeMove) {
        this.typeMove = typeMove;
    }
}
