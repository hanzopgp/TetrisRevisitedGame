package jeu.vue;

import jeu.model.Board;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


/**
 * Classe representant la construction de la grille du jeu.
 */
public class Grid extends JPanel {

    private final int width;
    private final int height;
    //DrawableWidth et DrawableHeight sont les dimensions
    //que va prendre la grille pour se dessiner
    private final int drawableWidth;
    private final int drawableHeight;
    private Board board;


    /**
     * Constructeur de l'object Grid
     * @param width Largeur de la grille
     * @param height Hauteur de la grille
     * @param drawableWidth Largeur de la grille pour se dessiner
     * @param drawableHeight Hauteur de la grille pour se dessiner
     * @param board Plateau de jeu
     */
    public Grid(int width, int height, int drawableWidth, int drawableHeight, Board board) {
        this.width = width;
        this.height = height;
        this.drawableWidth = drawableWidth;
        this.drawableHeight = drawableHeight;
        this.board = board;
        //on construit la grille
        this.make();
        //margin sur tout les cote de 10
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    /**
     * Methode representant la construction de la grille
     */
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

    /**
     * Methode mettant a jour le board
     */
    public void update() {
        this.removeAll();
        this.make();
        this.revalidate();
        this.repaint();
    }

    /**
     * Methode permettant d'associer une couleur par piece
     * @param filling Caractere representant la piece pour le terminal
     * @return couleur du caractere
     */
    public static Color setPieceColor(String filling) {
        switch (filling) {
            case "[a]":
            case "[b]":
            case "[c]":
                return Color.white;
            case "[d]":
            case "[e]":
            case "[f]":
                return Color.yellow;
            case "[g]":
            case "[h]":
            case "[i]":
                return Color.orange;
            case "[j]":
            case "[k]":
            case "[l]":
                return Color.pink;
            case "[m]":
            case "[n]":
            case "[o]":
                return Color.red;
            case "[p]":
            case "[q]":
            case "[r]":
                return Color.green;
            case "[s]":
            case "[t]":
            case "[u]":
                return Color.blue;
            case "[v]":
            case "[w]":
            case "[x]":
                return Color.CYAN;
            case "[y]":
            case "[z]":
                return Color.gray;
            default:
                return Color.black;
        }
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public void setBoard(Board board){
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

}