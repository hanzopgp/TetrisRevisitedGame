package jeu.factory;

import jeu.model.Point;

import java.io.Serializable;
import java.util.ArrayList;

public class PieceO implements PieceInterface, Serializable, Cloneable {

    private Point centralPiece;
    private final String filling;
    private final String baseFilling;
    private final int width;
    private final int height;
    private ArrayList<Point> state1;
    private ArrayList<Point> state2;
    private ArrayList<Point> state3;
    private ArrayList<Point> state4;
    private int currentState;
    private ArrayList<Point> currentPiece;
    private ArrayList<ArrayList<Point>> listState;

    public PieceO(Point centralPiece, String filling, int height, int width, int currentState) {
        this.filling = "[" + filling + "]";
        this.baseFilling = filling;
        this.centralPiece = centralPiece;
        ArrayList<Point> state1 = new ArrayList<>();
        ArrayList<Point> state2 = new ArrayList<>();
        ArrayList<Point> state3 = new ArrayList<>();
        ArrayList<Point> state4 = new ArrayList<>();
        ArrayList<ArrayList<Point>> listState = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                state1.add(new Point(centralPiece.getX() + i, centralPiece.getY() + j));
                state2.add(new Point(centralPiece.getX() - j, centralPiece.getY() + i));
                state3.add(new Point(centralPiece.getX() - i, centralPiece.getY() - j));
                state4.add(new Point(centralPiece.getX() + j, centralPiece.getY() - i));
            }
        }

        listState.add(state1);
        listState.add(state2);
        listState.add(state3);
        listState.add(state4);

        this.state1 = state1;
        this.state2 = state2;
        this.state3 = state3;
        this.state4 = state4;
        this.listState = listState;

        this.width = width;
        this.height = height;
        this.currentState = currentState;
        this.changeState(this.currentState);
    }

    @Override
    public PieceInterface clone(){
        Object o = null;
        try{
            o = super.clone();
        }catch(CloneNotSupportedException cnse){
            cnse.printStackTrace(System.err);
        }
        return (PieceInterface)o;
    }

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

    @Override
    public void rotate(boolean direction) {

    }

    @Override
    public PieceInterface translation(int direction) {
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
                throw new IllegalStateException("Unexpected value : " + direction);
        }
    }

    public PieceInterface move(int x, int y) {
        PieceO newPiece = new PieceO(new Point(this.centralPiece.getX() + x, this.centralPiece.getY() + y), this.getBaseFilling(), this.getHeight(), this.getWidth(), this.getCurrentState());
        this.setCentralPiece(newPiece.getPieceCentral());
        this.setCurrentPiece(newPiece.getCurrentPiece());
        this.setState1(newPiece.getState1());
        this.setState2(newPiece.getState2());
        this.setState3(newPiece.getState3());
        this.setState4(newPiece.getState4());
        this.setListState(newPiece.getListEtat());
        return this;
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public ArrayList<ArrayList<Point>> getListEtat() {
        return this.listState;
    }

    public Point getPieceCentral() {
        return this.centralPiece;
    }

    public ArrayList<Point> getCurrentPiece() {
        return this.currentPiece;
    }

    public ArrayList<Point> getState1() {
        return this.state1;
    }

    public ArrayList<Point> getState2() {
        return this.state2;
    }

    public ArrayList<Point> getState3() {
        return this.state3;
    }

    public ArrayList<Point> getState4() {
        return this.state4;
    }

    public int getCurrentState() {
        return this.currentState;
    }

    public String getFilling() {
        return this.filling;
    }

    public String getBaseFilling() {
        return this.baseFilling;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setCentralPiece(Point centralPiece) {
        this.centralPiece = centralPiece;
    }

    public void setCurrentPiece(ArrayList<Point> piece) {
        this.currentPiece = piece;
    }

    public void setCurrentState(int state) {
        this.currentState = state;
    }

    public void setState1(ArrayList<Point> state) {
        this.state1 = state;
    }

    public void setState2(ArrayList<Point> state) {
        this.state2 = state;
    }

    public void setState3(ArrayList<Point> state) {
        this.state3 = state;
    }

    public void setState4(ArrayList<Point> state) {
        this.state4 = state;
    }

    public void setListState(ArrayList<ArrayList<Point>> listState) {
        this.listState = listState;
    }

}
