package jeu.controller;

import jeu.Main;
import jeu.computer.Move;
import jeu.computer.MoveAndScore;
import jeu.computer.Solver;
import jeu.factory.Piece;
import jeu.factory.PieceO;
import jeu.model.Board;
import jeu.vue.Case;
import jeu.vue.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Controller de l'interface graphique de l'application
 */
public class GUIController implements KeyListener, MouseListener{

    private final MainWindow mainWindow;

    /**
     * Constructeur
     * @param mainWindow - Fenetre principale
     */
    public GUIController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Methode pour gerer les saisies utilisateur
     * @param e - Event a gerer
     */
    public void keyPressed(KeyEvent e) {
        if(!this.mainWindow.getBoard().getDemoMode()){
            //Ordinateur joue un coup si on appuie sur 't'
            if (e.getKeyCode() == KeyEvent.VK_T && !this.mainWindow.getBoard().getIsSolving() && (this.mainWindow.getBoard().getNbMove() < Main.NB_MOVE_MAX_GUI)) {
                System.out.println("SOLVING ...");
                Board board = this.mainWindow.getBoard();
                board.setSolving(true);
                Solver solver = new Solver();
                MoveAndScore move;
                if(board.isSolverTest()){
                    move = solver.solve(Main.SOLVER_DEPTH, board, new MoveAndScore(new Move(null, "none"), 0));
                }else{
                    move = solver.solve(Main.SOLVER_DEPTH_GENERAL, board, new MoveAndScore(new Move(null, "none"), 0));
                }
                board.translatePiece(board.tradDirection(move.getTypeMove()), move.getPiece());
                board.setNbMove(board.getNbMove()+1);
                System.out.println("FOUND ONE MOVE");
                board.setSolving(false);
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
            //System.out.println(this.mainWindow.getBoard().toString());
            this.mainWindow.getVue().update();
            this.mainWindow.getVueScore().getMovesRemaning().setText("<html><p>Nombre de coups <br>restants : " + this.mainWindow.getBoard().getNbMove() + "/" + Main.NB_MOVE_MAX_GUI+"</p></html>");
            this.mainWindow.getVueScore().getCurrentScore().setText("<html><p>Votre score est un <br>" + this.mainWindow.getBoard().defineAreaType(this.mainWindow.getBoard().evaluate()) + " de score : " + this.mainWindow.getBoard().evaluate()+"</p></html>");
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent evt) {

    }

    /**
     * Methode permettant de gerer les clic de l'utilisateur
     * @param e - Event a gerer
     */
    public void mouseClicked(MouseEvent e) {
        if(!this.mainWindow.getBoard().getDemoMode()){
            Point point = new Point((int) e.getPoint().getX() - 8, (int) e.getPoint().getY() - 30);
            JPanel test = (JPanel) this.mainWindow.getVue().getComponentAt(point);
            try {
                Case cell = (Case) test.getComponent(0);
                for (Piece p : this.mainWindow.getBoard().getListPiece()) {
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
