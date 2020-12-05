package jeu.controller;

import jeu.save.Save;
import jeu.vue.MainWindow;
import jeu.vue.SaveWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Controleur du systeme de sauvegarde
 */
public class SaveController implements ActionListener {
    private static ArrayList<Save> saves;
    private SaveWindow vue;
    private static Save saveFocused;
    private final MainWindow mainWindow;

    /**
     * Constructeur
     * @param mainWindow - Fenetre principale de l'application
     */
    public SaveController(MainWindow mainWindow){
        saves = mainWindow.getBoard().getSaveStorage().getListSave();
        this.mainWindow = mainWindow;
        this.makeView();
    }

    /**
     * Methode permettant de construire la vue pour choisir sa sauvegarde
     */
    public void makeView(){
        this.vue = new SaveWindow("Tetris v1.0", 500, 500, this);
    }

    /**
     * Charge la sauveguarde sur laquelle on a clique
     * @param e action a gerer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton saveFocused = (JButton) e.getSource();
        String[] name = saveFocused.getText().replace(" ", "").split("-");
        for(Save s : saves){
            if(s.getPlayerName().equals(name[1]) && Integer.parseInt(name[0]) == s.getId()){
                this.setSaveFocused(s);
                break;
            }
        }
        this.vue.dispose();
        this.mainWindow.loadGame(this.getSaveFocused());
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public static ArrayList<Save> getSaves() {
        return saves;
    }

    public static void setSaves(ArrayList<Save> saves) {
        SaveController.saves = saves;
    }

    public SaveWindow getVue() {
        return vue;
    }

    public void setVue(SaveWindow vue) {
        this.vue = vue;
    }

    public Save getSaveFocused() {
        return saveFocused;
    }

    public void setSaveFocused(Save saveFocused) {
        SaveController.saveFocused = saveFocused;
    }

}
