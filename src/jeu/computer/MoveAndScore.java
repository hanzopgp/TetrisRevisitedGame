package jeu.computer;

/**
 * Classe contenant un object Move ainsi que le score associe
 */
public class MoveAndScore extends Move implements Cloneable{

    private int score;
    private final Move move;

    /**
     * Object MoveAndScore contenant un object Move en plus d'un int score
     * @param move object Move
     * @param score int contenant un score
     */
    public MoveAndScore(Move move, int score) {
        super(move.getPiece(), move.getTypeMove());
        this.move = move;
        this.score = score;
    }

    /**
     * Permet de copier l'object
     * @return o l'object copie
     */
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

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public Move getMove(){ return this.move; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

}
