package jeu.vue;

import javax.swing.*;


public class Case extends JLabel {

    private String currentPiece = null;

    public Case() {

    }

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
