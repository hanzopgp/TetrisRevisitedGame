package jeu.vue;

import jeu.model.Board;
import jeu.Main;
import jeu.save.Save;
import jeu.save.SaveStorage;
import jeu.save.SaveWriteRead;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow extends JFrame implements ActionListener{

    private String title;
    private int width;
    private int height;
    private Board board;
    private Grid vue;
    private VueScore  score;
    private ListButton listeButton;
    private boolean isOver = false;
    private boolean pieceAdded = false;
    private boolean isPlaying = false;
    private boolean newGame = false;

    public MainWindow(String title, int width, int height, Board board) {
        this.board = board;
        this.title = title;
        this.width = width;
        this.height = height;
        this.setSize(this.width, this.height);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //grile
        Grid vue = new Grid(board.getColonnes(), board.getLignes(), (this.width) - 100, (this.height) - 100, this.board);
        this.vue = vue;

        //TABLEAU SCORE A DROITE + BOUTONS + ALERTES
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(4, 1));
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightPanel.setBackground(Color.WHITE);

        //Titre du jeu
        JLabel gameTitle = new JLabel(this.getTitle(), SwingConstants.CENTER);
        gameTitle.setFont(gameTitle.getFont().deriveFont(20f));
        rightPanel.add(gameTitle);

        //score
        VueScore score = new VueScore();
        this.score = score;
        rightPanel.add(score);

        //Boutons
        ListButton listeButton = new ListButton(this.board, this.vue, score, this);
        this.listeButton = listeButton;
        rightPanel.add(listeButton);

        //affichage
        this.getContentPane().add(vue, BorderLayout.CENTER);
        this.getContentPane().add(rightPanel, BorderLayout.EAST);
        this.setVisible(true);
        this.setFocusable(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String nameButton = ((JButton)source).getText();
        if(nameButton.equals("Save")){
            int nbSave = 0;
            this.board.saveBoard(nbSave, this.board.getSaveStorage());
        }else if(nameButton.equals("Load derniere partie") && this.board.getSaveStorage().getSize() != 0 && newGame){
            Board lastSavedBoardTest = this.board.getSaveStorage().getSavedBoardByNumber(this.board.getSaveStorage().getSize() - 1);
            this.vue.setBoard(lastSavedBoardTest);
            this.vue.update();
        }else if(nameButton.equals("Nouvelle Configuration")&& !isPlaying){
            this.board.clear();
            this.board.fillBoardRandomly();
            this.vue.update();
        }else if(nameButton.equals("Nouvelle Partie")&& !this.pieceAdded){
            this.newGame = true;
            this.board.clear();
            this.pieceAdded = false;
            this.board.fillBoardRandomly();
            this.vue.update();
            this.score.getMovesRemaning().setText("Coups restants :\n 0/" + Main.NB_MOVE_MAX_GUI);
        }else if(nameButton.equals("Finir la partie")){
            this.isOver = true;
            this.board.gameOver();
            this.score.getMovesRemaning().setText("Coups restants :\n "+Main.NB_MOVE_MAX_GUI  +"/" + Main.NB_MOVE_MAX_GUI);
        }
        requestFocusInWindow();
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public Grid getVue() {
        return this.vue;
    }

    public VueScore getVueScore() {
        return this.score;
    }

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

    public Board getBoard() {
        return this.board;
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

    public ListButton getListeButton() {
        return listeButton;
    }

}
