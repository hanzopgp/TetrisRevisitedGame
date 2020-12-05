package jeu.vue;

import jeu.Main;

import javax.swing.*;
import java.awt.*;

/**
 * Classe représentant l'affichage du score
 */
public class VueScore extends JPanel {

    private JLabel movesRemaining;
    private JLabel currentScore;

    /**
     * Constructeur de la classe VueScore
     */
    public VueScore() {
        this.makeCptMovesRemaining();
        this.makeScore();
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(Color.WHITE);
    }

    /**
     * Fonction affichant le nombre de coups restant
     */
    public void makeCptMovesRemaining() {
        this.movesRemaining = new JLabel("Coups restants :\n 0/" + Main.NB_MOVE_MAX_GUI, SwingConstants.CENTER);
        this.movesRemaining.setFont(this.movesRemaining.getFont().deriveFont(15f));
        this.add(this.movesRemaining);
    }

    /** 
     * Fonction affichant le score
     * */
    public void makeScore() {
        this.currentScore = new JLabel("Score actuel : 0", SwingConstants.CENTER);
        this.currentScore.setFont(this.currentScore.getFont().deriveFont(15f));
        this.add(this.currentScore);
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
