package jeu.vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

/**
 * Classe affichant tous les boutons dans la fenetre graphique
 */
public class ListButton extends JPanel{

    private boolean isOver = false;
    private boolean pieceAdded = false;
    private boolean isPlaying = false;

    /**
     * Constructeur de l'object ListButton
     * @param window fenetre de l'application
     */
    public ListButton(MainWindow window){
        //margin sur tout les cote de 10
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridLayout(9, 1, 5, 5));
        this.setBackground(Color.WHITE);
        JButton newGame = new JButton("Reset");
        JButton newConfig = new JButton("Nouvelle Configuration");
        JButton load = new JButton("Charger une partie");
        JButton save = new JButton("Sauvegarder");
        JButton deleteSave = new JButton("Supprimer les saves et quitter");
        JButton endGame = new JButton("Finir la partie");
        JButton leaveGame = new JButton("Quitter le jeu");
        this.prepareButton(newGame, window);
        this.prepareButton(newConfig, window);
        this.prepareButton(load, window);
        this.prepareButton(save, window);
        this.prepareButton(deleteSave, window);
        this.prepareButton(endGame, window);
        this.prepareButton(leaveGame, window);
    }

    /**
     * Methode permettant de styliser un bouton
     * @param button object JButton a styliser
     * @param window object MainWindow etant la fenetre graphique dans lequel sont les boutons
     */
    public void prepareButton(JButton button, MainWindow window){
        button.setContentAreaFilled(false);
        button.addActionListener(window);
        button.setSize(300, 100);
        this.add(button);
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public boolean getIsOver(){
        return isOver;
    }

    public boolean getPieceAdded(){
        return pieceAdded;
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public void setPieceAdded(boolean pieceAdded) {
        this.pieceAdded = pieceAdded;
    }

    public void setOver(boolean isOver) {
        this.isOver = isOver;
    }

    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

}
