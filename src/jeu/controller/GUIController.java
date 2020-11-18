package jeu.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.*;
import java.awt.Point;

import javax.swing.*;

import jeu.Main;
import jeu.computer.Solver;
import jeu.model.PieceInterface;
import jeu.model.Board;
import jeu.vue.MainWindow;
import jeu.vue.Case;

public class GUIController implements KeyListener, MouseListener{
	
	private final Board board;
	private final MainWindow mainWindow;
	private PieceInterface pieceFocused;
	private boolean pieceAdded = false;
	private boolean isOver = false;
	private int nbMove;
	private boolean isSolving = false;
	
	public GUIController(MainWindow mainWindow){
		this.nbMove = 0;
		this.mainWindow = mainWindow;
		this.board = mainWindow.getBoard();
	}
		
	public void keyPressed(KeyEvent e){
		//Si le joueur estime avoir termine
		if ((e.getKeyCode() == KeyEvent.VK_ENTER || this.nbMove >= Main.NB_MOVE_MAX_GUI) && !this.isOver){
			this.isOver = true;
			this.board.gameOver();
		}
		//Chargement de toutes les pieces sur le board
		if (e.getKeyCode() == KeyEvent.VK_R && !this.pieceAdded){
			this.pieceAdded = true;
			for(int i = 0; i < Main.MAX_NB_PIECE_ON_BOARD; i++){
				this.board.addRandomPiece();
			}
		}
		//Ordinateur joue
		if (e.getKeyCode() == KeyEvent.VK_T && this.pieceAdded){
			this.isSolving = true;
			Solver solver = new Solver(10, this.board, this.board.evaluate());
			solver.solve();
			System.out.println("SOLVING");
		}
		//Actions sur une piece selectionne
        if(this.pieceFocused != null && !this.isOver && this.nbMove < Main.NB_MOVE_MAX_GUI){
			this.mainWindow.getVueScore().getMovesRemaning().setText("Nombre de coups restants : "+nbMove+"/"+Main.NB_MOVE_MAX_GUI);
			this.mainWindow.getVueScore().getCurrentScore().setText("Votre score : "+ this.board.evaluate());
			if (e.getKeyCode() == KeyEvent.VK_S ){
				this.nbMove++;
				this.board.translatePiece(2,this.pieceFocused);
			}
			if (e.getKeyCode() == KeyEvent.VK_Z ){
				this.nbMove++;
				this.board.translatePiece(1,this.pieceFocused);
			}
			if (e.getKeyCode() == KeyEvent.VK_Q ){
				this.nbMove++;
				this.board.translatePiece(3,this.pieceFocused);
			}
			if (e.getKeyCode() == KeyEvent.VK_D ){
				this.nbMove++;
				this.board.translatePiece(4,this.pieceFocused);
			}
			if (e.getKeyCode() == KeyEvent.VK_A ){
				this.nbMove++;
				this.board.rotatePiece(true,this.pieceFocused);
			}
			if (e.getKeyCode() == KeyEvent.VK_E ){
				this.nbMove++;
				this.board.rotatePiece(false,this.pieceFocused);
			}
		}
		this.mainWindow.getVue().update();
	}
	
	public void keyReleased(KeyEvent e) {
		
	}

    public void keyTyped(KeyEvent evt) {
		
	}
	
	public void mouseClicked(MouseEvent e) {
		Point point = new Point((int)e.getPoint().getX()-8,(int)e.getPoint().getY()-30);
		JPanel test = (JPanel) this.mainWindow.getVue().getComponentAt(point);
		try {
			Case cell = (Case) test.getComponent(0);
			for (PieceInterface p : this.mainWindow.getBoard().getListPiece()) {
				if (p.getFilling().equals(cell.getCurrentPiece())) {
					System.out.println("alo");
					this.pieceFocused = p;
					break;
				}
			}
		}catch(Exception exc){
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

	public void movePlus(KeyEvent e){
		switch(e.getKeyCode()){
            case KeyEvent.VK_S :
				this.nbMove++;
				break;
            case KeyEvent.VK_Z :
				this.nbMove++;
				break;
			case KeyEvent.VK_Q :
				this.nbMove++;
				break;                
			case KeyEvent.VK_D :
				this.nbMove++;
                break;
			case KeyEvent.VK_A :
				this.nbMove++;
				break;
			case KeyEvent.VK_E :
				this.nbMove++;
				break;
		}
	}
 
}
