package jeu.vue;

import javax.swing.*;

import jeu.model.Piece;


public class Case extends JLabel{

    private String currentPiece = null;
    
    public Case(){

    }
    
    public Case(String currentPiece){
        this.currentPiece = currentPiece;
    }

    public String getCurrentPiece() {
        return currentPiece;
    }

}
