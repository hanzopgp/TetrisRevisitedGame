package jeu.model;

import java.util.*;

public interface PieceInterface {
		
	public ArrayList<Piece> getCurrentPiece();

	public void rotate(boolean direction);

	public PieceInterface translation(int direction);
	
	public String getFilling();

}
