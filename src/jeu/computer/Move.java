package jeu.computer;

import jeu.factory.Piece;

public class Move {

    private Piece piece;
    private String typeMove;

    /**
     * Object Move contenant une piece et le type de move associe
     * @param piece object piece
     * @param typeMove string type de move
     */
    public Move(Piece piece, String typeMove) {
        this.piece = piece;
        this.typeMove = typeMove;
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public Piece getPiece() { return piece; }

    public void setPiece(Piece piece) { this.piece = piece; }

    public String getTypeMove() { return typeMove; }

    public void setTypeMove(String typeMove) { this.typeMove = typeMove; }

}
