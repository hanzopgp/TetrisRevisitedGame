package jeu.vue;

import javax.swing.*;

import java.awt.*;

public class ListButton extends JPanel{

    private boolean isOver = false;
    private boolean pieceAdded = false;
    private boolean isPlaying = false;

    public ListButton(MainWindow window){
        this.setLayout(new GridLayout(9, 1, 5, 5));
        this.setBackground(Color.WHITE);
        JButton newGame = new JButton("Reset");
        JButton newConfig = new JButton("Nouvelle Configuration");
        JButton load = new JButton("Charger une partie");
        JButton save = new JButton("Sauvegarder");
        JButton deleteSave = new JButton("Supprimer les saves et quitter");
        JButton endGame = new JButton("Finir la partie");
        JButton leaveGame = new JButton("Quitter le jeu");
        save.addActionListener(window);
        load.addActionListener(window);
        newGame.addActionListener(window);
        newConfig.addActionListener(window);
        endGame.addActionListener(window);
        deleteSave.addActionListener(window);
        leaveGame.addActionListener(window);
        this.add(newGame);
        this.add(newConfig);
        this.add(load);
        this.add(save);
        this.add(deleteSave);
        this.add(endGame);
        this.add(leaveGame);
    }

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
