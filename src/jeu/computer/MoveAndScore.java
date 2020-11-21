package jeu.computer;

import jeu.factory.PieceInterface;

public class MoveAndScore extends Move{

    private int score;

    public MoveAndScore(PieceInterface piece, String typeMove, int score) {
        super(piece, typeMove);
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
