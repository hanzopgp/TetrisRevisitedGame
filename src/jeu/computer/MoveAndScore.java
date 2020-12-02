package jeu.computer;

import jeu.model.Board;

public class MoveAndScore extends Move implements Cloneable{

    private int score;
    private Move move;

    public MoveAndScore(Move move, int score) {
        super(move.getPiece(), move.getTypeMove());
        this.move = move;
        this.score = score;
    }

    @Override
    public MoveAndScore clone(){
        Object o = null;
        try{
            o = super.clone();
        }catch(CloneNotSupportedException cnse){
            cnse.printStackTrace(System.err);
        }
        return (MoveAndScore)o;
    }

    public Move getMove(){
        return this.move;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
