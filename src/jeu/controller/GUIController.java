package jeu.controller;

import jeu.Main;
import jeu.computer.MoveAndScore;
import jeu.computer.Solver;
import jeu.factory.PieceInterface;
import jeu.factory.PieceO;
import jeu.vue.Case;
import jeu.vue.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIController implements KeyListener, MouseListener{

    private final MainWindow mainWindow;

    public GUIController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void keyPressed(KeyEvent e) {
        if(!this.mainWindow.getBoard().getDemoMode()){
            //Ordinateur joue
            if (e.getKeyCode() == KeyEvent.VK_T && !this.mainWindow.getBoard().getIsSolving()) {
                this.mainWindow.getBoard().setSolving(true);
                Solver solver = new Solver();
                MoveAndScore move = solver.solve(Main.SOLVER_DEPTH,this.mainWindow.getBoard());
                System.out.println(move);
            }
            //Actions sur une piece selectionne
            if (this.mainWindow.getBoard().getPieceFocused() != null && !this.mainWindow.getBoard().getIsOver() && this.mainWindow.getBoard().getNbMove() < Main.NB_MOVE_MAX_GUI) {
                this.mainWindow.getBoard().setPlaying(true);
                boolean validMove = false;
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    validMove = this.mainWindow.getBoard().translatePiece(2, this.mainWindow.getBoard().getPieceFocused());
                }
                if (e.getKeyCode() == KeyEvent.VK_Z) {
                    validMove = this.mainWindow.getBoard().translatePiece(1, this.mainWindow.getBoard().getPieceFocused());
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    validMove = this.mainWindow.getBoard().translatePiece(3, this.mainWindow.getBoard().getPieceFocused());
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    validMove = this.mainWindow.getBoard().translatePiece(4, this.mainWindow.getBoard().getPieceFocused());
                }
                if (e.getKeyCode() == KeyEvent.VK_A && !(this.mainWindow.getBoard().getPieceFocused() instanceof PieceO)) {
                    validMove = this.mainWindow.getBoard().rotatePiece(true, this.mainWindow.getBoard().getPieceFocused());
                }
                if (e.getKeyCode() == KeyEvent.VK_E && !(this.mainWindow.getBoard().getPieceFocused() instanceof PieceO)) {
                    validMove = this.mainWindow.getBoard().rotatePiece(false, this.mainWindow.getBoard().getPieceFocused());
                }
                if(validMove){this.mainWindow.getBoard().movePlus(e);}
            }
            this.mainWindow.getVue().update();
            this.mainWindow.getVueScore().getMovesRemaning().setText("<html><p>Nombre de coups <br>restants : " + this.mainWindow.getBoard().getNbMove() + "/" + Main.NB_MOVE_MAX_GUI+"</p></html>");
            this.mainWindow.getVueScore().getCurrentScore().setText("<html><p>Votre score est un <br>" + this.mainWindow.getBoard().defineAreaType(this.mainWindow.getBoard().evaluate()) + " de score : " + this.mainWindow.getBoard().evaluate()+"</p></html>");
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent evt) {

    }

    public void mouseClicked(MouseEvent e) {
        if(!this.mainWindow.getBoard().getDemoMode()){
            Point point = new Point((int) e.getPoint().getX() - 8, (int) e.getPoint().getY() - 30);
            JPanel test = (JPanel) this.mainWindow.getVue().getComponentAt(point);
            try {
                Case cell = (Case) test.getComponent(0);
                for (PieceInterface p : this.mainWindow.getBoard().getListPiece()) {
                    if (p.getFilling().equals(cell.getCurrentPiece())) {
                        this.mainWindow.getBoard().setPieceFocused(p);
                        break;
                    }
                }
            } catch (Exception exc) {
                System.out.println("Attention a ne pas cliquer entre 2 cases !");
            }
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

}
