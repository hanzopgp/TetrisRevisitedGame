package jeu.factory;

import jeu.model.Point;

public class PieceT extends AbstractPiece {

    public PieceT(Point centralPiece, String filling, int width, int height, int currentState) {
        super(centralPiece, filling, width, height, currentState, "T");
        this.constructAllStates();
        this.setCurrentState(currentState);
        this.changeState(this.getCurrentState());
        //System.out.println(this.getFilling());
    }

    public void constructAllStates() {

        for(int i=0;i<this.getWidth();i++){
            if(i%2==0 || i==this.getCentralPiece().getX()){
                if(i==0){
                    this.getState1().add(new Point(this.getCentralPiece().getX()+1, this.getCentralPiece().getY()));
                    this.getState2().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY()+1));
                    this.getState3().add(new Point(this.getCentralPiece().getX()-1, this.getCentralPiece().getY()));
                    this.getState4().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY()-1));
                }else{
                    this.getState1().add(new Point(this.getCentralPiece().getX()+i, this.getCentralPiece().getY()));
                    this.getState2().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY()+i));
                    this.getState3().add(new Point(this.getCentralPiece().getX()-i, this.getCentralPiece().getY()));
                    this.getState4().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY()-i));
                }
            }else{
                this.getState1().add(new Point(this.getCentralPiece().getX()-i, this.getCentralPiece().getY()));
                this.getState2().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY()-i));
                this.getState3().add(new Point(this.getCentralPiece().getX()+i, this.getCentralPiece().getY()));
                this.getState4().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY()+i));
            }
        }

        for(int i=0;i<this.getHeight();i++){

            this.getState1().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY()+i));
            this.getState2().add(new Point(this.getCentralPiece().getX()-i, this.getCentralPiece().getY()));
            this.getState3().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY()-i));
            this.getState4().add(new Point(this.getCentralPiece().getX()+i, this.getCentralPiece().getY()));
        }
    }

    //HAUT
    public void makeState1() {
        this.getState1().add(new Point(this.getCentralPiece().getX() - 1, this.getCentralPiece().getY()));
        this.getState1().add(new Point(this.getCentralPiece().getX() + 1, this.getCentralPiece().getY()));
        this.getState1().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() + 1));
        this.getState1().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() + 2));
    }

    //DROITE
    public void makeState2() {
        this.getState2().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() - 1));
        this.getState2().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() + 1));
        this.getState2().add(new Point(this.getCentralPiece().getX() - 1, this.getCentralPiece().getY()));
        this.getState2().add(new Point(this.getCentralPiece().getX() - 2, this.getCentralPiece().getY()));
    }

    //BAS
    public void makeState3() {
        this.getState3().add(new Point(this.getCentralPiece().getX() + 1, this.getCentralPiece().getY()));
        this.getState3().add(new Point(this.getCentralPiece().getX() - 1, this.getCentralPiece().getY()));
        this.getState3().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() - 1));
        this.getState3().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() - 2));
    }

    //GAUCHE
    public void makeState4() {
        this.getState4().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() - 1));
        this.getState4().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() + 1));
        this.getState4().add(new Point(this.getCentralPiece().getX() + 1, this.getCentralPiece().getY()));
        this.getState4().add(new Point(this.getCentralPiece().getX() + 2, this.getCentralPiece().getY()));
    }
}
