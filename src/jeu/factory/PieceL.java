package jeu.factory;

import jeu.model.Point;

public class PieceL extends AbstractPiece {

    public PieceL(Point centralPiece, String filling, int height, int width, int currentState) {
        super(centralPiece, filling, width, height, currentState, "L");
        this.constructAllStates();
        this.setCurrentState(currentState);
        this.changeState(this.getCurrentState());
    }

    public void constructAllStates(){
        for (int i = 0; i < this.getHeight() - 1; i++) {
            this.getState1().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() - i));
            this.getState2().add(new Point(this.getCentralPiece().getX() + i, this.getCentralPiece().getY()));
            this.getState3().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() + i));
            this.getState4().add(new Point(this.getCentralPiece().getX() - i, this.getCentralPiece().getY()));
        }
        for (int i = 0; i < this.getWidth(); i++) {
            this.getState1().add(new Point(this.getCentralPiece().getX() + i, this.getCentralPiece().getY() + 1));
            this.getState2().add(new Point(this.getCentralPiece().getX() - 1, this.getCentralPiece().getY() + i));
            this.getState3().add(new Point(this.getCentralPiece().getX() - i, this.getCentralPiece().getY() - 1));
            this.getState4().add(new Point(this.getCentralPiece().getX() + 1, this.getCentralPiece().getY() - i));
        }
    }

}
