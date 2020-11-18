package jeu.vue;

import jeu.model.Board;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private String title;
    private int width;
    private int height;
    private JPanel panel;
    private JPanel grid;
    private Board board;
    private Grid vue;
    private JLabel movesRemaining;
    private VueScore  score;

    public MainWindow(String title, int width, int height, Board board){
        this.board = board;
        this.title = title;
        this.width = width;
        this.height = height;
        this.panel = new JPanel();
        this.grid = new JPanel();
        this.setSize(this.width, this.height);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Grid vue = new Grid(board.getColonnes(), board.getLignes(), (this.width)-20, (this.height)-100, this.board);
        this.vue = vue;
        VueScore score = new VueScore();
        this.score = score;
        this.getContentPane().add(vue, BorderLayout.NORTH);
        this.getContentPane().add(score, BorderLayout.SOUTH);
        this.setVisible(true);
    }

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

    public Board getBoard(){
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

}
