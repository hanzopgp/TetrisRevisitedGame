package jeu.computer;

public class MoveWithScore {

    private int indexPiece;
    private int direction;
    private int scoreDiff;

    public MoveWithScore(int indexPiece, int direction, int scoreDiff) {
        this.indexPiece = indexPiece;
        this.direction = direction;
        this.scoreDiff = scoreDiff;
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public int getIndexPiece() {
        return indexPiece;
    }

    public void setIndexPiece(int indexPiece) {
        this.indexPiece = indexPiece;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getScoreDiff() {
        return scoreDiff;
    }

    public void setScoreDiff(int scoreDiff) {
        this.scoreDiff = scoreDiff;
    }
}
