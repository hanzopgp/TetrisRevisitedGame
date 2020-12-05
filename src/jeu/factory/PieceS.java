package jeu.factory;

import jeu.model.Point;

/**
 * Classe représentant une pièce de forme "S",
 * qui hérite de la classe mère AbstractPiece
 */
public class PieceS extends AbstractPiece {

    /**
     * Constructeur
     * @param centralPiece - Piece de référence de la piece globale
     * @param filling - Caractère representant la pièce pour le terminale
     * @param height - Hauteur de la piece
     * @param width - Largeur de la piece
     * @param currentState - Etat dans lequel doit etre instancie la piece (Haut, bas, gauche, droite)
     */
    public PieceS(Point centralPiece, String filling, int height, int width, int width2, int currentState) {
        super(centralPiece, filling, width, width2, height, currentState, "S");
        this.constructAllStates();
        this.changeState(this.getCurrentState());

    }

    /**
     * Méthode permettant de construire tout les etats possibles de la piece
     * (haut, bas, gauche, droite) avec un ensemble de "Point" qui construisent
     * la piece visible sur terminal ou sur interface graphique.
     *
     * De ce fait, chaque rotation a donc un etat attribué, ce qui rend plus simple
     * la gestion de celles-ci.
     */
    public void constructAllStates(){
        for (int i = 0; i < this.getHeight() - 1; i++) {
            this.getState1().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() - i));
            this.getState2().add(new Point(this.getCentralPiece().getX() + i, this.getCentralPiece().getY()));
            this.getState3().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() + i));
            this.getState4().add(new Point(this.getCentralPiece().getX() - i, this.getCentralPiece().getY()));
        }
        for (int i = 0; i < this.getWidth(); i++) {
            this.getState1().add(new Point(this.getCentralPiece().getX() + i, this.getCentralPiece().getY() - this.getHeight() + 1));
            this.getState2().add(new Point(this.getCentralPiece().getX() + this.getHeight() - 1, this.getCentralPiece().getY() + i));
            this.getState3().add(new Point(this.getCentralPiece().getX() - i, this.getCentralPiece().getY() + this.getHeight() - 1));
            this.getState4().add(new Point(this.getCentralPiece().getX() - this.getHeight() + 1, this.getCentralPiece().getY() - i));
        }
        for (int i = 0; i < this.getWidth2(); i++) {
            this.getState1().add(new Point(this.getCentralPiece().getX() - i, this.getCentralPiece().getY()));
            this.getState2().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() - i));
            this.getState3().add(new Point(this.getCentralPiece().getX() + i, this.getCentralPiece().getY()));
            this.getState4().add(new Point(this.getCentralPiece().getX(), this.getCentralPiece().getY() + i));
        }
    }

}
