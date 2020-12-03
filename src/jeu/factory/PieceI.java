package jeu.factory;

import jeu.model.Point;

public class PieceI extends AbstractPiece {

    public PieceI(Point centralPiece, String filling, int height, int width, int currentState) {
        super(centralPiece, filling, width, height, currentState, "I");
        this.constructAllStates();
        this.setCurrentState(currentState);
        this.changeState(this.getCurrentState());
    }

    public void constructAllStates(){
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                this.getState1().add(new Point(this.getCentralPiece().getX() + i, this.getCentralPiece().getY()));
                this.getState2().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() + i));
                this.getState3().add(new Point(this.getCentralPiece().getX() - i, this.getCentralPiece().getY()));
                this.getState4().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() - i));
            }
            this.getState1().add(new Point(this.getCentralPiece().getX()-1, this.getCentralPiece().getY()));
            this.getState2().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY()-1));
            this.getState3().add(new Point(this.getCentralPiece().getX()+1, this.getCentralPiece().getY()));
            this.getState4().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY()+1));
        }
    }

}