package jeu.vue;

import jeu.model.Board;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Grid extends JPanel {

    private final int width;
    private final int height;
    //DrawableWidth et DrawableHeight sont les dimensions
    //que va prendre la grille pour se dessiner
    private final int drawableWidth;
    private final int drawableHeight;
    private ArrayList<JPanel> pieces;
    private Board board;

    public Grid(int width, int height, int drawableWidth, int drawableHeight, Board board) {
        this.width = width;
        this.height = height;
        this.drawableWidth = drawableWidth;
        this.drawableHeight = drawableHeight;
        this.board = board;
        //on construit la grille
        this.make();
        //margin sur tout les côté de 10
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    public void make() {
        this.setLayout(new GridLayout(this.height, this.width, 1, 1));
        this.setPreferredSize(new Dimension(this.drawableWidth, this.drawableHeight));
        this.setBackground(Color.white);
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                JPanel cell = new JPanel();
                if (!this.board.getBoard().get(j).get(i).equals("[ ]")) {
                    cell.setBackground(setPieceColor(this.board.getBoard().get(j).get(i)));
                } else {
                    cell.setBackground(Color.BLACK);
                }
                JLabel content = new Case(this.board.getBoard().get(j).get(i));
                cell.add(content);
                cell.setBorder(BorderFactory.createLineBorder(Color.black));
                this.add(cell);
            }
        }
    }

    public static Color setPieceColor(String filling) {
        switch (filling) {
            case "[a]":
            case "[j]":
            case "[s]":
                return Color.RED;
            case "[b]":
            case "[k]":
            case "[t]":
                return Color.BLUE;
            case "[c]":
            case "[l]":
            case "[u]":
                return Color.YELLOW;
            case "[d]":
            case "[m]":
            case "[v]":
                return Color.GREEN;
            case "[e]":
            case "[n]":
            case "[w]":
                return Color.GRAY;
            case "[f]":
            case "[x]":
            case "[o]":
                return Color.MAGENTA;
            case "[g]":
            case "[p]":
            case "[y]":
                return Color.ORANGE;
            case "[h]":
            case "[q]":
            case "[z]":
                return Color.pink;
            case "[i]":
            case "[r]":
                return Color.white;
            default:
                return Color.CYAN;
        }
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public void update() {
        this.removeAll();
        this.make();
        this.revalidate();
        this.repaint();
        //this.setFocusable(true);
        //this.requestFocusInWindow();
    }

}