package jeu.factory;

import jeu.model.Point;

/**
 * Classe representant une piece de forme "T",
 * qui herite de la classe mere AbstractPiece
 */
public class PieceT extends AbstractPiece {

    /**
     * Constructeur
     * @param centralPiece - Piece de reference de la piece globale
     * @param filling - Caractere representant la piece pour le terminale
     * @param height - Hauteur de la piece
     * @param width - Largeur de la piece
     * @param currentState - Etat dans lequel doit etre instancie la piece (Haut, bas, gauche, droite)
     */
    public PieceT(Point centralPiece, String filling, int height, int width, int currentState) {
        super(centralPiece, filling, width, height, currentState, "T");
        this.constructAllStates();
        this.setCurrentState(currentState);
        this.changeState(this.getCurrentState());
    }

    /**
     * Methode permettant de construire tout les etats possibles de la piece
     * (haut, bas, gauche, droite) avec un ensemble de "Point" qui construisent
     * la piece visible sur terminal ou sur interface graphique.
     *
     * De ce fait, chaque rotation a donc un etat attribue, ce qui rend plus simple
     * la gestion de celles-ci.
     */
    public void constructAllStates() {
        if(this.getWidth()%2 != 0) {
            for (int i = 1; i < this.getWidth()-1; i++) {
                this.getState1().add(new Point(this.getCentralPiece().getX() + i, this.getCentralPiece().getY()));
                this.getState2().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() + i));
                this.getState3().add(new Point(this.getCentralPiece().getX() + i, this.getCentralPiece().getY()));
                this.getState4().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() + i));

                this.getState1().add(new Point(this.getCentralPiece().getX() - i, this.getCentralPiece().getY()));
                this.getState2().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() - i));
                this.getState3().add(new Point(this.getCentralPiece().getX() - i, this.getCentralPiece().getY()));
                this.getState4().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() - i));

            }
        }else{
            for (int i = 1; i < this.getWidth(); i++) {
                this.getState1().add(new Point(this.getCentralPiece().getX() + i, this.getCentralPiece().getY()));
                this.getState2().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() + i));
                this.getState3().add(new Point(this.getCentralPiece().getX() + i, this.getCentralPiece().getY()));
                this.getState4().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() + i));

                this.getState1().add(new Point(this.getCentralPiece().getX() - i, this.getCentralPiece().getY()));
                this.getState2().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() - i));
                this.getState3().add(new Point(this.getCentralPiece().getX() - i, this.getCentralPiece().getY()));
                this.getState4().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() - i));

            }
        }
        for(int i=0;i<this.getHeight();i++){
            this.getState1().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY()+i));
            this.getState2().add(new Point(this.getCentralPiece().getX()-i, this.getCentralPiece().getY()));
            this.getState3().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY()-i));
            this.getState4().add(new Point(this.getCentralPiece().getX()+i, this.getCentralPiece().getY()));
        }
    }
}
