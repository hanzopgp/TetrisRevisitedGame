package jeu.controller;

import jeu.Main;
import jeu.computer.Solver;
import jeu.model.Board;
import jeu.factory.PieceInterface;
import jeu.factory.PieceO;
import jeu.vue.Case;
import jeu.vue.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIController implements KeyListener, MouseListener{

    private final Board board;
    private final MainWindow mainWindow;
    private PieceInterface pieceFocused;
    private boolean pieceAdded = false;
    private boolean isOver = false;
    private int nbMove;
    private boolean isPlaying = false;
    private boolean isSolving = false;

    public GUIController(MainWindow mainWindow) {
        this.nbMove = 0;
        this.mainWindow = mainWindow;
        this.board = mainWindow.getBoard();
    }

    public void keyPressed(KeyEvent e) {
//        //Le joueur decide de finir la partie
//        if ((e.getKeyCode() == KeyEvent.VK_ENTER || this.nbMove >= Main.NB_MOVE_MAX_GUI) && !this.isOver) {
//            this.isOver = true;
//            this.board.gameOver();
//        }
//        //Pour lancer la partie
//        if (e.getKeyCode() == KeyEvent.VK_R && !this.pieceAdded) {
//            this.pieceAdded = true;
//            this.board.fillBoardRandomly();
//        }
//        //Pour changer la configuration de depart
//        if (e.getKeyCode() == KeyEvent.VK_F && !isPlaying) {
//            this.board.clear();
//            this.board.fillBoardRandomly();
//        }
//        //Pour sauveguarder la partie
//        if (e.getKeyCode() == KeyEvent.VK_C) {
//
//        }
        //Ordinateur joue
        if (e.getKeyCode() == KeyEvent.VK_T && this.pieceAdded) {
            this.isSolving = true;
            Solver solver = new Solver(Main.SOLVER_DEPTH, this.board, this.board.evaluate());
            solver.solve();
        }
        //Actions sur une piece selectionne
        if (this.pieceFocused != null && !this.isOver && this.nbMove < Main.NB_MOVE_MAX_GUI) {
            isPlaying = true;
            if (e.getKeyCode() == KeyEvent.VK_S) {
                this.board.translatePiece(2, this.pieceFocused);
                this.movePlus(e);
            }
            if (e.getKeyCode() == KeyEvent.VK_Z) {
                this.board.translatePiece(1, this.pieceFocused);
                this.movePlus(e);
            }
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                this.board.translatePiece(3, this.pieceFocused);
                this.movePlus(e);
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                this.board.translatePiece(4, this.pieceFocused);
                this.movePlus(e);
            }
            if (e.getKeyCode() == KeyEvent.VK_A && !(this.pieceFocused instanceof PieceO)) {
                this.board.rotatePiece(true, this.pieceFocused);
                this.movePlus(e);
            }
            if (e.getKeyCode() == KeyEvent.VK_E && !(this.pieceFocused instanceof PieceO)) {
                this.board.rotatePiece(false, this.pieceFocused);
                this.movePlus(e);
            }
        }
        this.mainWindow.getVue().update();
        this.mainWindow.getVueScore().getPlayerName().setText("<html><p>Nom du joueur : " + this.board.getPlayerName() + "</p></html>");
        this.mainWindow.getVueScore().getMovesRemaning().setText("<html><p>Nombre de coups <br>restants : " + nbMove + "/" + Main.NB_MOVE_MAX_GUI+"</p></html>");
        this.mainWindow.getVueScore().getCurrentScore().setText("<html><p>Votre score est un <br>" + this.board.defineAreaType(this.board.evaluate()) + " de score : " + this.board.evaluate()+"</p></html>");
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent evt) {

    }

    public void mouseClicked(MouseEvent e) {
        Point point = new Point((int) e.getPoint().getX() - 8, (int) e.getPoint().getY() - 30);
        JPanel test = (JPanel) this.mainWindow.getVue().getComponentAt(point);
        try {
            Case cell = (Case) test.getComponent(0);
            for (PieceInterface p : this.mainWindow.getBoard().getListPiece()) {
                if (p.getFilling().equals(cell.getCurrentPiece())) {
                    this.pieceFocused = p;
                    break;
                }
            }
        } catch (Exception exc) {
            System.out.println("Attention a ne pas cliquer entre 2 cases !");
        }

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void movePlus(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_S:
            case KeyEvent.VK_E:
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
            case KeyEvent.VK_Q:
            case KeyEvent.VK_Z:
                this.nbMove++;
                break;
        }
    }
}
