package jeu.factory;

import jeu.model.Point;

/**
 * Classe representant une piece de forme "O",
 * qui herite de la classe mere AbstractPiece
 */
public class PieceO extends AbstractPiece {

    /**
     * Constructeur
     * @param centralPiece - Piece de reference de la piece globale
     * @param filling - Caractere representant la piece pour le terminale
     * @param height - Hauteur de la piece
     * @param width - Largeur de la piece
     * @param currentState - Etat dans lequel doit etre instancie la piece (Haut, bas, gauche, droite)
     */
    public PieceO(Point centralPiece, String filling, int height, int width, int currentState) {
        super(centralPiece, filling, width, height, currentState, "O");
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
