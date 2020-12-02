//package jeu.factory;
//
//import jeu.model.Point;
//
//import java.util.ArrayList;
//
//public class Piece implements PieceInterface {
//
//    private Point centralPiece;
//    private final String filling;
//    private final String baseFilling;
//    private final int width;
//    private final int height;
//    private int width2;
//    private ArrayList<Point> state1;
//    private ArrayList<Point> state2;
//    private ArrayList<Point> state3;
//    private ArrayList<Point> state4;
//    private int currentState;
//    private ArrayList<Point> currentPiece;
//    private ArrayList<ArrayList<Point>> listState;
//
//    public Piece(Point centralPiece, String filling, int width, int height, int currentState) {
//        this.filling = "["+filling+"]";
//        this.baseFilling = filling;
//        this.width = width;
//        this.height = height;
//    }
//
//    public Piece(Point centralPiece, String filling, int width, int width2, int height, int currentState) {
//        this.filling = "[" + filling + "]";
//        this.centralPiece = centralPiece;
//        this.width = width;
//        this.width2 = width2;
//        this.height = height;
//        this.currentState = currentState;
//        this.baseFilling = filling;
//        this.state1 = new ArrayList<>();
//        this.state2 = new ArrayList<>();
//        this.state3 = new ArrayList<>();
//        this.state4 = new ArrayList<>();
//    }
//
//    @Override
//    public ArrayList<Point> getCurrentPiece() {
//        return this.currentPiece;
//    }
//
//    @Override
//    public void rotate(boolean direction) {
//        if (direction) {
//            if (this.currentState != 4) {
//                this.setCurrentState(this.getCurrentState() + 1);
//            } else {
//                this.setCurrentState(1);
//            }
//
//        } else {
//            if (this.currentState != 1) {
//                this.setCurrentState(this.getCurrentState() - 1);
//            } else {
//                this.setCurrentState(4);
//            }
//        }
//        changeState(this.currentState);
//    }
//
//    @Override
//    public PieceInterface translation(int direction) {
//        switch (direction) {
//            case (1):
//                return this.move(0, -1);
//            case (2):
//                return this.move(0, +1);
//            case (3):
//                return this.move(-1, 0);
//            case (4):
//                return this.move(+1, 0);
//            default:
//                throw new IllegalStateException("Unexpected value: " + direction);
//        }
//    }
//
//    public PieceInterface move(int x, int y) {
//        PieceT newPiece = new PieceT(new Point(this.centralPiece.getX() + x, this.centralPiece.getY() + y), this.getBaseFilling(), this.getHeight(), this.getWidth(), this.getCurrentState());
//        this.setCentralPiece(newPiece.getCentralPiece());
//        this.setCurrentPiece(newPiece.getCurrentPiece());
//        this.setState1(newPiece.getState1());
//        this.setState2(newPiece.getState2());
//        this.setState3(newPiece.getState3());
//        this.setState4(newPiece.getState4());
//        this.setListState(newPiece.getListState());
//        return this;
//    }
//
//    public void changeState(int state) {
//        switch (state) {
//            case 1:
//                this.setCurrentPiece(this.getState1());
//                break;
//            case 2:
//                this.setCurrentPiece(this.getState2());
//                break;
//            case 3:
//                this.setCurrentPiece(this.getState3());
//                break;
//            case 4:
//                this.setCurrentPiece(this.getState4());
//                break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + state);
//        }
//    }
//
//    @Override
//    public String getFilling() {
//        return this.filling;
//    }
//
//    @Override
//    public PieceInterface getCopy() {
//        return new Piece(new Point(this.centralPiece.getX(), this.centralPiece.getY()), this.filling, this.height, this.width, this.currentState);
//    }
//
//    /*===============GETTER & SETTERS===============*/
//
//    public Point getCentralPiece() {
//        return centralPiece;
//    }
//
//    public void setCentralPiece(Point centralPiece) {
//        this.centralPiece = centralPiece;
//    }
//
//    public String getBaseFilling() {
//        return baseFilling;
//    }
//
//    public int getWidth() {
//        return width;
//    }
//
//    public int getHeight() {
//        return height;
//    }
//
//    public int getWidth2() {
//        return width2;
//    }
//
//    public void setWidth2(int width2) {
//        this.width2 = width2;
//    }
//
//    public ArrayList<Point> getState1() {
//        return state1;
//    }
//
//    public void setState1(ArrayList<Point> state1) {
//        this.state1 = state1;
//    }
//
//    public ArrayList<Point> getState2() {
//        return state2;
//    }
//
//    public void setState2(ArrayList<Point> state2) {
//        this.state2 = state2;
//    }
//
//    public ArrayList<Point> getState3() {
//        return state3;
//    }
//
//    public void setState3(ArrayList<Point> state3) {
//        this.state3 = state3;
//    }
//
//    public ArrayList<Point> getState4() {
//        return state4;
//    }
//
//    public void setState4(ArrayList<Point> state4) {
//        this.state4 = state4;
//    }
//
//    public int getCurrentState() {
//        return currentState;
//    }
//
//    public void setCurrentState(int currentState) {
//        this.currentState = currentState;
//    }
//
//    public void setCurrentPiece(ArrayList<Point> currentPiece) {
//        this.currentPiece = currentPiece;
//    }
//
//    public ArrayList<ArrayList<Point>> getListState() {
//        return listState;
//    }
//
//    public void setListState(ArrayList<ArrayList<Point>> listState) {
//        this.listState = listState;
//    }
//}
