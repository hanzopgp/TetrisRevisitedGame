package jeu.factory;

import jeu.model.Point;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe mere de toutes les pieces du jeu
 */
abstract class AbstractPiece implements Piece, Serializable, Cloneable {

    protected Point centralPiece;
    protected final String filling;
    protected final String baseFilling;
    protected final int width;
    protected final int height;
    protected int width2;
    protected ArrayList<Point> state1;
    protected ArrayList<Point> state2;
    protected ArrayList<Point> state3;
    protected ArrayList<Point> state4;
    protected int currentState;
    protected ArrayList<Point> currentPiece;
    protected ArrayList<ArrayList<Point>> listState;
    protected String type;

    /**
     * Constructeur de la piece mere, dans le cas ou la piece finale ne contient qu'une seule largeur.
     * @param centralPiece - Piece de reference de la piece globale
     * @param filling - Caractere representant la piece pour le terminale
     * @param height - Hauteur de la piece
     * @param width - Largeur de la piece
     * @param currentState - Etat dans lequel doit etre instancie la piece (Haut, bas, gauche, droite)
     * @param type - Type de la piece, necessaire pour le traitement des mouvements par la suite
     */
    public AbstractPiece(Point centralPiece, String filling, int width, int height, int currentState, String type) {
        this.filling = "[" + filling + "]";
        this.baseFilling = filling;
        this.width = width;
        this.height = height;
        this.centralPiece = centralPiece;
        this.currentState = currentState;
        this.listState = new ArrayList<>();
        this.setState1(new ArrayList<>());
        this.setState2(new ArrayList<>());
        this.setState3(new ArrayList<>());
        this.setState4(new ArrayList<>());
        this.getState1().add(this.getCentralPiece());
        this.getState2().add(this.getCentralPiece());
        this.getState3().add(this.getCentralPiece());
        this.getState4().add(this.getCentralPiece());
        this.getListState().add(this.getState1());
        this.getListState().add(this.getState2());
        this.getListState().add(this.getState3());
        this.getListState().add(this.getState4());
        this.type = type;
    }

    /**
     * Constructeur de la piece mere, dans le cas ou la piece finale contient deux largeurs.
     * @param centralPiece - Piece de reference de la piece globale
     * @param filling - Caractere representant la piece pour le terminale
     * @param height - Hauteur de la piece
     * @param width - Premiere largeur de la piece
     * @param width2 - Deuxieme largeur de la piece
     * @param currentState - Etat dans lequel doit etre instancie la piece (Haut, bas, gauche, droite)
     * @param type - Type de la piece, necessaire pour le traitement des mouvements par la suite
     */
    public AbstractPiece(Point centralPiece, String filling, int width, int width2, int height, int currentState, String type) {
        this.filling = "[" + filling + "]";
        this.width = width;
        this.width2 = width2;
        this.height = height;
        this.baseFilling = filling;
        this.centralPiece = centralPiece;
        this.currentState = currentState;
        this.listState = new ArrayList<>();
        this.setState1(new ArrayList<>());
        this.setState2(new ArrayList<>());
        this.setState3(new ArrayList<>());
        this.setState4(new ArrayList<>());
        this.getState1().add(this.getCentralPiece());
        this.getState2().add(this.getCentralPiece());
        this.getState3().add(this.getCentralPiece());
        this.getState4().add(this.getCentralPiece());
        this.getListState().add(this.getState1());
        this.getListState().add(this.getState2());
        this.getListState().add(this.getState3());
        this.getListState().add(this.getState4());
        this.type = type;
    }

    @Override
    public ArrayList<Point> getCurrentPiece() {
        return this.currentPiece;
    }

    @Override
    public void rotate(boolean direction) {
        if (direction) {
            if (this.getCurrentState() != 4) {
                this.setCurrentState(this.getCurrentState() + 1);
            } else {
                this.setCurrentState(1);
            }

        } else {
            if (this.getCurrentState() != 1) {
                this.setCurrentState(this.getCurrentState() - 1);
            } else {
                this.setCurrentState(4);
            }
        }
        changeState(this.getCurrentState());
    }

    /**
     * Change les coordonnes de la piece suivant la direction
     * @param direction - Direction souhaitee (haut, bas, droite, gauche)
     * @return la piece que l'on a bouger
     */
    @Override
    public Piece translation(int direction) {
        switch (direction) {
            case (1):
                return this.move(0, -1);
            case (2):
                return this.move(0, +1);
            case (3):
                return this.move(-1, 0);
            case (4):
                return this.move(+1, 0);
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    /**
     * Methode permettant de bouger la piece vers des coordonnees X et Y
     * @param x - Coordonnees X
     * @param y - Coordonnees Y
     * @return Piece bougee
     */
    public Piece move(int x, int y) {
        AbstractPiece newPiece = null;
        switch (this.getType()){
            case "T":
                newPiece = new PieceT(new Point(this.getCentralPiece().getX() + x, this.getCentralPiece().getY() + y),
                        this.getBaseFilling(), this.getHeight(), this.getWidth(), this.getCurrentState());
                break;
            case "O":
                newPiece = new PieceO(new Point(this.getCentralPiece().getX() + x, this.getCentralPiece().getY() + y),
                        this.getBaseFilling(), this.getHeight(), this.getWidth(), this.getCurrentState());
                break;
            case "I":
                newPiece = new PieceI(new Point(this.getCentralPiece().getX() + x, this.getCentralPiece().getY() + y),
                        this.getBaseFilling(), this.getHeight(), this.getWidth(), this.getCurrentState());
                break;
            case "L":
                newPiece = new PieceL(new Point(this.getCentralPiece().getX() + x, this.getCentralPiece().getY() + y),
                        this.getBaseFilling(), this.getHeight(), this.getWidth(), this.getCurrentState());
                break;
            case "S":
                newPiece = new PieceS(new Point(this.getCentralPiece().getX() + x, this.getCentralPiece().getY() + y),
                        this.getBaseFilling(), this.getHeight(), this.getWidth(), this.getWidth2(), this.getCurrentState());
                break;
        }
        assert newPiece != null;
        this.setCentralPiece(newPiece.getCentralPiece());
        this.setCurrentPiece(newPiece.getCurrentPiece());
        this.setState1(newPiece.getState1());
        this.setState2(newPiece.getState2());
        this.setState3(newPiece.getState3());
        this.setState4(newPiece.getState4());
        this.setListState(newPiece.getListState());
        return this;
    }

    /**
     * Methode permettant de changer l'etat courant d'une piece (haut, bas, droite, gauche)
     * @param state - Etat a definir
     */
    public void changeState(int state) {
        switch (state) {
            case 1:
                this.setCurrentPiece(this.getState1());
                break;
            case 2:
                this.setCurrentPiece(this.getState2());
                break;
            case 3:
                this.setCurrentPiece(this.getState3());
                break;
            case 4:
                this.setCurrentPiece(this.getState4());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + state);
        }
    }

    /**
     * Permet de cloner l'object Piece
     * @return un object Piece clone
     */
    @Override
    public Piece clone(){
        Object o = null;
        try{
            o = super.clone();
        }catch(CloneNotSupportedException cnse){
            cnse.printStackTrace(System.err);
        }
        return (Piece)o;
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    @Override
    public String getFilling() {
        return this.filling;
    }

    public Point getCentralPiece() {
        return centralPiece;
    }

    public void setCentralPiece(Point centralPiece) { this.centralPiece = centralPiece; }

    public String getBaseFilling() {
        return baseFilling;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth2() {
        return width2;
    }

    public void setWidth2(int width2) {
        this.width2 = width2;
    }

    public ArrayList<Point> getState1() {
        return state1;
    }

    public void setState1(ArrayList<Point> state1) {
        this.state1 = state1;
    }

    public ArrayList<Point> getState2() {
        return state2;
    }

    public void setState2(ArrayList<Point> state2) {
        this.state2 = state2;
    }

    public ArrayList<Point> getState3() {
        return state3;
    }

    public void setState3(ArrayList<Point> state3) {
        this.state3 = state3;
    }

    public ArrayList<Point> getState4() {
        return state4;
    }

    public void setState4(ArrayList<Point> state4) {
        this.state4 = state4;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public void setCurrentPiece(ArrayList<Point> currentPiece) {
        this.currentPiece = currentPiece;
    }

    public ArrayList<ArrayList<Point>> getListState() {
        return listState;
    }

    public void setListState(ArrayList<ArrayList<Point>> listState) {
        this.listState = listState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}