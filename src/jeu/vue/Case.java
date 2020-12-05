package jeu.vue;

import javax.swing.*;

/**
 * Classe représentant un morceau de la pièce
 */
public class Case extends JLabel {

    private String currentPiece = null;

    /**
     * Constructeur par default de l'object Case
     */
    public Case() {

    }

    /**
     * Constructeur de l'object Case
     * @param currentPiece la piece courante designe par une chaine de caractere
     */
    public Case(String currentPiece) {
        this.currentPiece = currentPiece;
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public String getCurrentPiece() {
        return currentPiece;
    }

}
