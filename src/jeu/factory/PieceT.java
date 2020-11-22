package jeu.factory;

import jeu.model.Point;

import java.io.Serializable;
import java.util.ArrayList;

public class PieceT implements PieceInterface, Serializable {

    private Point centralPiece;
    private int width;
    private int height;
    private ArrayList<Point> state1;
    private ArrayList<Point> state2;
    private ArrayList<Point> state3;
    private ArrayList<Point> state4;
    private String filling;
    private String baseFilling;
    private int currentState;
    private ArrayList<Point> currentPiece;
    private ArrayList<ArrayList<Point>> listState;

    public PieceT(Point centralPiece, String filling, int height, int width, int currentState) {
        this.filling = "[" + filling + "]";
        this.centralPiece = centralPiece;
        this.state1 = new ArrayList<>();
        this.state2 = new ArrayList<>();
        this.state3 = new ArrayList<>();
        this.state4 = new ArrayList<>();
        this.currentPiece = state1;
        this.state1.add(centralPiece);
        this.state2.add(centralPiece);
        this.state3.add(centralPiece);
        this.state4.add(centralPiece);
        this.constructAllStates();
        this.width = width;
        this.height = height;
        this.currentState = currentState;
        this.changeState(this.currentState);
    }

    @Override
    public PieceInterface getCopy() {
        return new PieceT(new Point(this.centralPiece.getX(), this.centralPiece.getY()), this.filling, this.height, this.width, this.currentState);
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
        if (direction) {
            if (this.currentState != 4) {
                this.setCurrentState(this.getCurrentState() + 1);
            } else {
                this.setCurrentState(1);
            }

        } else {
            if (this.currentState != 1) {
                this.setCurrentState(this.getCurrentState() - 1);
            } else {
                this.setCurrentState(4);
            }
        }
        changeState(this.currentState);
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
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    @Override
    public String getFilling() {
        return this.filling;
    }

    public PieceInterface move(int x, int y) {
        PieceT newPiece = new PieceT(new Point(this.centralPiece.getX() + x, this.centralPiece.getY() + y), this.getBaseFilling(), this.getHeight(), this.getWidth(), this.getCurrentState());
        this.setPieceCentral(newPiece.getCentralPiece());
        this.setCurrentPiece(newPiece.getCurrentPiece());
        this.setState1(newPiece.getState1());
        this.setState2(newPiece.getState2());
        this.setState3(newPiece.getState3());
        this.setState4(newPiece.getState4());
        this.setListState(newPiece.getListState());
        return this;
    }

    public void constructAllStates() {
        this.makeState1();
        this.makeState2();
        this.makeState3();
        this.makeState4();
        this.listState = new ArrayList<>();
        this.listState.add(state1);
        this.listState.add(state2);
        this.listState.add(state3);
        this.listState.add(state4);
    }

    //HAUT
    public void makeState1() {
        this.state1.add(new Point(centralPiece.getX() - 1, centralPiece.getY()));
        this.state1.add(new Point(centralPiece.getX() + 1, centralPiece.getY()));
        this.state1.add(new Point(centralPiece.getX(), centralPiece.getY() + 1));
        this.state1.add(new Point(centralPiece.getX(), centralPiece.getY() + 2));
    }

    //DROITE
    public void makeState2() {
        this.state2.add(new Point(centralPiece.getX(), centralPiece.getY() - 1));
        this.state2.add(new Point(centralPiece.getX(), centralPiece.getY() + 1));
        this.state2.add(new Point(centralPiece.getX() - 1, centralPiece.getY()));
        this.state2.add(new Point(centralPiece.getX() - 2, centralPiece.getY()));
    }

    //BAS
    public void makeState3() {
        this.state3.add(new Point(centralPiece.getX() + 1, centralPiece.getY()));
        this.state3.add(new Point(centralPiece.getX() - 1, centralPiece.getY()));
        this.state3.add(new Point(centralPiece.getX(), centralPiece.getY() - 1));
        this.state3.add(new Point(centralPiece.getX(), centralPiece.getY() - 2));
    }

    //GAUCHE
    public void makeState4() {
        this.state4.add(new Point(centralPiece.getX(), centralPiece.getY() - 1));
        this.state4.add(new Point(centralPiece.getX(), centralPiece.getY() + 1));
        this.state4.add(new Point(centralPiece.getX() + 1, centralPiece.getY()));
        this.state4.add(new Point(centralPiece.getX() + 2, centralPiece.getY()));
    }


    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public Point getCentralPiece() {
        return centralPiece;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Point> getState1() {
        return state1;
    }

    public ArrayList<Point> getState2() {
        return state2;
    }

    public ArrayList<Point> getState3() {
        return state3;
    }

    public ArrayList<Point> getState4() {
        return state4;
    }

    public String getBaseFilling() {
        return baseFilling;
    }

    public int getCurrentState() {
        return currentState;
    }

    @Override
    public ArrayList<Point> getCurrentPiece() {
        return currentPiece;
    }

    public ArrayList<ArrayList<Point>> getListState() {
        return listState;
    }

    public void setPieceCentral(Point centralPiece) {
        this.centralPiece = centralPiece;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setState1(ArrayList<Point> state1) {
        this.state1 = state1;
    }

    public void setState2(ArrayList<Point> state2) {
        this.state2 = state2;
    }

    public void setState3(ArrayList<Point> state3) {
        this.state3 = state3;
    }

    public void setState4(ArrayList<Point> state4) {
        this.state4 = state4;
    }

    public void setFilling(String filling) {
        this.filling = filling;
    }

    public void setBaseFilling(String baseFilling) {
        this.baseFilling = baseFilling;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public void setCurrentPiece(ArrayList<Point> currentPiece) {
        this.currentPiece = currentPiece;
    }

    public void setListState(ArrayList<ArrayList<Point>> listState) {
        this.listState = listState;
    }
}
