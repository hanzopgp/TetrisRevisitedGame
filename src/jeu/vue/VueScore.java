package jeu.vue;

import javax.swing.*;

import jeu.Main;

import jeu.model.Board;

public class VueScore extends JPanel{

    private JLabel movesRemaining;
    private JLabel currentScore;

    public VueScore(){
        this.makeCptMovesRemaining();
        this.makeScore();
    }

    public void makeCptMovesRemaining(){
        this.movesRemaining = new JLabel();
        this.movesRemaining.setText("Nombre de coups restants : 0/"+ Main.NB_MOVE_MAX_GUI);
        this.movesRemaining.setFont(this.movesRemaining.getFont().deriveFont(20f));
        this.add(this.movesRemaining);
    }

    public void makeScore(){
        this.currentScore = new JLabel();
        this.currentScore.setText("Votre score actuelle : 0");
        this.currentScore.setFont(this.currentScore.getFont().deriveFont(20f));
        this.add(this.currentScore);
    }

    public JLabel getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(JLabel currentScore) {
        this.currentScore = currentScore;
    }

    public JLabel getMovesRemaning() {
        return movesRemaining;
    }

    public void setMovesRemaning(JLabel movesRemaining) {
        this.movesRemaining = movesRemaining;
    }
    
}
