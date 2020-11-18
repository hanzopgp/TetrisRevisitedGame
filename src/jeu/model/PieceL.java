package jeu.model;

import java.util.ArrayList;

public class PieceL implements PieceInterface {

	private Piece centralPiece;
	private String filling;
	private String baseFilling;
	private int width;
	private int height;
	private ArrayList<Piece> state1;
	private ArrayList<Piece> state2;
	private ArrayList<Piece> state3;
	private ArrayList<Piece> state4;
	private int currentState;
	private ArrayList<Piece> currentPiece;
	private ArrayList<ArrayList<Piece>> listState;

	public PieceL(Piece centralPiece, String filling, int height, int width, int currentState){
		this.filling = "["+filling+"]";
		this.baseFilling = filling;
		this.centralPiece = centralPiece;
		ArrayList<Piece> state1 = new ArrayList<>();
		ArrayList<Piece> state2 = new ArrayList<>();
		ArrayList<Piece> state3 = new ArrayList<>();
		ArrayList<Piece> state4 = new ArrayList<>();
		ArrayList<ArrayList<Piece>> listState = new ArrayList<>();
		state1.add(centralPiece);
		state2.add(centralPiece);
		state3.add(centralPiece);
		state4.add(centralPiece);

		for(int i = 0; i<height-1; i++){
			state1.add(new Piece(centralPiece.getX(), centralPiece.getY()-i));
			state2.add(new Piece(centralPiece.getX()+i, centralPiece.getY()));
			state3.add(new Piece(centralPiece.getX(), centralPiece.getY()+i));
			state4.add(new Piece(centralPiece.getX()-i, centralPiece.getY()));
		}
		for(int i = 0; i<width; i++){
			state1.add(new Piece(centralPiece.getX()+i, centralPiece.getY()+1));
			state2.add(new Piece(centralPiece.getX()-1, centralPiece.getY()+i));
			state3.add(new Piece(centralPiece.getX()-i, centralPiece.getY()-1));
			state4.add(new Piece(centralPiece.getX()+1, centralPiece.getY()-i));
		}

		listState.add(state1);
		listState.add(state2);
		listState.add(state3);
		listState.add(state4);

		this.state1 = state1;
		this.state2 = state2;
		this.state3 = state3;
		this.state4 = state4;
		this.listState = listState;

		this.width = width;
		this.height = height;
		this.currentState = currentState;
		this.changeState(this.currentState);
	}

	public void changeState(int state){
		switch(state) {
			case 1:
				this.setCurrentPiece(this.getState1());
				break;
			case 2:
				this.setCurrentPiece(this.getState2());
				break;
			case 3:
				this.setCurrentPiece(this.getState3());
				break;
			case 4:
				this.setCurrentPiece(this.getState4());
				break;
		}
	}

	@Override
	public void rotate(boolean direction){
		if(direction){
			if(this.currentState != 4){
				this.setCurrentState(this.getCurrentState()+1);
			}else{
				this.setCurrentState(1);
			}
		}else{
			if(this.currentState != 1){
				this.setCurrentState(this.getCurrentState()-1);
			}else{
				this.setCurrentState(4);
			}
		}
		changeState(this.currentState);
	}

	@Override
	public PieceInterface translation(int direction){
		switch(direction){
			case(1):
				return this.move(0,-1);
			case(2):
				return this.move(0,+1);
			case(3):
				return this.move(-1,0);
			case(4):
				return this.move(+1,0);
			default:
				throw new IllegalStateException("Unexpected value: " + direction);
		}
	}

	public PieceInterface move(int x, int y){
		PieceL newPiece = new PieceL(new Piece(this.centralPiece.getX()+x, this.centralPiece.getY()+y),this.getBaseFilling(),this.getHeight(),this.getWidth(),this.getCurrentState());
		this.setCentralPiece(newPiece.getCentralPiece());
		this.setCurrentPiece(newPiece.getCurrentPiece());
		this.setState1(newPiece.getState1());
		this.setState2(newPiece.getState2());
		this.setState3(newPiece.getState3());
		this.setState4(newPiece.getState4());
		this.setListState(newPiece.getListState());
		return this;
	}

	public ArrayList<ArrayList<Piece>> getListState(){
		return this.listState;
	}

	public Piece getCentralPiece(){
		return this.centralPiece;
	}

	public ArrayList<Piece> getCurrentPiece(){
		return this.currentPiece;
	}

	public ArrayList<Piece> getState1(){
		return this.state1;
	}

	public ArrayList<Piece> getState2(){
		return this.state2;
	}

	public ArrayList<Piece> getState3(){
		return this.state3;
	}

	public ArrayList<Piece> getState4(){
		return this.state4;
	}

	public int getCurrentState(){
		return this.currentState;
	}

	public String getFilling(){
		return this.filling;
	}

	public String getBaseFilling(){
		return this.baseFilling;
	}

	public int getHeight(){
		return this.height;
	}

	public int getWidth(){
		return this.width;
	}
		
	public void setCentralPiece(Piece centralPiece){
		this.centralPiece = centralPiece;
	}
	
	public void setCurrentPiece(ArrayList<Piece> piece){
		this.currentPiece = piece;
	}
	
	public void setCurrentState(int state){
		this.currentState = state;
	}

	public void setState1(ArrayList<Piece> state){
		this.state1 = state;
	}

	public void setState2(ArrayList<Piece> state){
		this.state2 = state;
	}

	public void setState3(ArrayList<Piece> state){
		this.state3 = state;
	}

	public void setState4(ArrayList<Piece> state){
		this.state4 = state;
	}

	public void setListState(ArrayList<ArrayList<Piece>> listState){
		this.listState = listState;
	}
	
}
