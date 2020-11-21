package jeu.vue;

import jeu.Main;

import javax.swing.*;
import java.awt.*;

public class VueScore extends JPanel {

    private JLabel movesRemaining;
    private JLabel currentScore;
    String playerNameString;

    public VueScore() {
        this.makeCptMovesRemaining();
        this.makeScore();
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(Color.WHITE);
    }

    public void makeCptMovesRemaining() {
        this.movesRemaining = new JLabel("Coups restants :\n 0/" + Main.NB_MOVE_MAX_GUI, SwingConstants.CENTER);
        this.movesRemaining.setFont(this.movesRemaining.getFont().deriveFont(15f));
        this.add(this.movesRemaining);
    }

    public void makeScore() {
        this.currentScore = new JLabel("Score actuel : 0", SwingConstants.CENTER);
        this.currentScore.setFont(this.currentScore.getFont().deriveFont(15f));
        this.add(this.currentScore);
    }

    public void setPlayerNameString(String playerNameString){
        this.playerNameString = playerNameString;
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public JLabel getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(JLabel currentScore) {
        this.currentScore = currentScore;
    }

    public JLabel getMovesRemaning() { return movesRemaining; }

}
