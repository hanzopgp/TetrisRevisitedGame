package jeu.vue;

import jeu.controller.SaveController;
import jeu.save.Save;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Classe representant la fenetre de choix des parties sauveguarde
 */
public class SaveWindow extends JFrame {

    private String title;
    private int width;
    private int height;
    private final SaveController controller;

    /**
     * Constructeur de l'object SaveWindow
     * @param title titre de la fenetre de sauvegarde
     * @param width largeur de la fenetre
     * @param height hauteur de la fenetre
     * @param controller controller du systeme de sauvegarde
     */
    public SaveWindow(String title, int width, int height, SaveController controller) throws HeadlessException {
        this.title = title;
        this.width = width;
        this.height = height;
        this.controller = controller;
        this.setSize(this.width, this.height);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.makeListSaveChoosing();
        this.setVisible(true);
    }

    /**
     * Methode qui affiche la liste les parties sauvegardees
     */
    public void makeListSaveChoosing(){
        JPanel list = new JPanel();
        list.setBorder(new EmptyBorder(10, 10, 10, 10));
        if(SaveController.getSaves().size() == 0){
            list.setLayout(new GridLayout(1, 1));
            list.add(new JLabel("Aucune sauvegarde enregistree ..."));
        }else{
            list.setLayout(new GridLayout(SaveController.getSaves().size(), 1, 10, 10));
            for(Save s : SaveController.getSaves()){
                JButton save = new JButton(s.getId()+" - "+s.getPlayerName());
                save.setSize(new Dimension(100, 50));
                save.addActionListener(controller);
                list.add(save);
            }
        }
        this.getContentPane().add(list, BorderLayout.NORTH);
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
