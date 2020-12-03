package jeu.factory;

import jeu.model.Point;

public class PieceO extends AbstractPiece {

    public PieceO(Point centralPiece, String filling, int height, int width, int currentState) {
        super(centralPiece, filling, width, height, currentState, "O");
        this.constructAllStates();
        this.setCurrentState(currentState);
        this.changeState(this.getCurrentState());
    }

    public void constructAllStates(){
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                this.getState1().add(new Point(this.getCentralPiece().getX() + i, this.getCentralPiece().getY() + j));
                this.getState2().add(new Point(this.getCentralPiece().getX() - j, this.getCentralPiece().getY() + i));
                this.getState3().add(new Point(this.getCentralPiece().getX() - i, this.getCentralPiece().getY() - j));
                this.getState4().add(new Point(this.getCentralPiece().getX() + j, this.getCentralPiece().getY() - i));
            }
        }
    }
}
