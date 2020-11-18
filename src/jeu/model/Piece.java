package jeu.model;

import javax.swing.*;

public class Piece extends JPanel {

	private int x;
	private int y;
	private PieceInterface formerPiece;

	public Piece(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}

	public int getXLeft(){
		return this.x-1;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}

	public PieceInterface getFormerPiece() {
		return formerPiece;
	}

	public void setFormerPiece(PieceInterface formerPiece) {
		this.formerPiece = formerPiece;
	}

	public void resetFormerPiece(){
		this.formerPiece = null;
	}
}
