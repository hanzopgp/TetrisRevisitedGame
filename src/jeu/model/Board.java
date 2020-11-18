package jeu.model;

import jeu.Main;

import java.util.*;

public class Board {

	private final int nbLines;
	private final int nbColumns ;
	private ArrayList<ArrayList<String>> board;
	private ArrayList<PieceInterface> listPiece;
	private ArrayList<String> listFilling;
	private int cptMaxPieceOnBoard;
	ArrayList<ScoreWithVal> listSwv = new ArrayList<ScoreWithVal>();

	public Board(int nbColumns, int nbLines){
		this.cptMaxPieceOnBoard = 0;
		ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < nbColumns; i++){
			ArrayList<String> line = new ArrayList<String>();
			board.add(line);
			for (int j = 0; j < nbLines; j++){
				line.add("[ ]");
			}
		}
		this.listPiece = new ArrayList<PieceInterface>();
		this.board = board;
		this.nbLines = nbLines;
		this.nbColumns = nbColumns;
		this.listFilling = new ArrayList<String>();
		List<String> anotherList = Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
		this.listFilling.addAll(anotherList);
	}

	public boolean addPiece(PieceInterface piece){
		if(this.isSatisfiedPiece(piece)){
			this.delPiece(piece.getFilling());
			for (int i = 0; i < this.board.size(); i++) {
	            for (int j = 0; j < this.board.size();j++) {
					for(int k = 0; k < piece.getCurrentPiece().size(); k++){
						if(piece.getCurrentPiece().get(k).getX() == i && piece.getCurrentPiece().get(k).getY() == j){
							this.board.get(i).set(j, piece.getFilling());
						}
					}
				}
			}
			this.listPiece.remove(piece);
			this.listPiece.add(piece);
			return true;
		}else{
			return false;
		}
	}

	public void addRandomPiece() throws IllegalStateException {
		boolean isValid = false;
		PieceInterface randomPiece = null;
		//On cree des pieces aleatoires jusqu'a ce qu'une piece soit valide
		while(!isValid) {
			//Choix du type de piece
			Random r1 = new Random();
			int min1 = 1;
			int max1 = Main.NB_DIFF_PIECE;
			int randomType = r1.nextInt((max1 - min1) + 1) + min1;
			//Choix remplissage
			Random r2 = new Random();
			int minR = 0;
			int maxR = this.listFilling.size() - 1;
			int randomFillingIndex = r2.nextInt((maxR - minR) + 1) + minR;
			String randomFilling = this.listFilling.get(randomFillingIndex); //On enleve ce remplissage de la liste
			//Choix placement
			Random r3 = new Random();
			int min2 = 1;
			int max2 = this.nbLines;
			int randomWidth = r3.nextInt((max2 - min2) + 1) + min2;
			Random r4 = new Random();
			int min3 = 1;
			int max3 = this.nbColumns;
			int randomHeight = r4.nextInt((max3 - min3) + 1) + min3;
			Piece randomCentralPiece = new Piece(randomWidth, randomHeight);
			//Choix etat
			Random r5 = new Random();
			int min4 = 1;
			int max4 = 4;
			int randomState = r5.nextInt((max4 - min4) + 1) + min4;
			//Creation de la piece random
			switch (randomType) {
				case 1:
					randomPiece = new PieceL(randomCentralPiece, randomFilling, 3, 2, randomState);
					break;
				case 2:
					randomPiece = new PieceS(randomCentralPiece, randomFilling, 3, 2, 2, randomState);
					break;
				case 3:
					randomPiece = new PieceT(randomCentralPiece, randomFilling, 3, 2, randomState);
					break;
				default:
					throw new IllegalStateException("Unexpected value: " + randomType);
			}
			//Verification si la randomPiece rentre sur le plateau sans probleme
			isValid = addPiece(randomPiece);
			//On enleve la lettre seulement si on a trouve un emplacement valide pour la piece !
			if(isValid){
				this.listFilling.remove(randomFillingIndex);
			}
		}
		this.cptMaxPieceOnBoard++;
	}

	public void delPiece(String filling){
		for (int i = 0; i < this.nbColumns; i++) {
			for (int j = 0; j < this.nbLines; j++) {
				if(this.board.get(i).get(j).equals(filling)){
					this.board.get(i).set(j,"[ ]");
				}
			}
		}
	}

	public void rotatePiece(boolean direction, PieceInterface piece){
		piece.rotate(direction);
		if(!(this.addPiece(piece))){
			piece.rotate(!(direction));
		}
	}

	public boolean translatePiece(int direction, PieceInterface piece){
		PieceInterface newPiece = piece.translation(direction);
		if(!(this.addPiece(newPiece))){
			newPiece = newPiece.translation(this.reverseDirection(direction));
			return false;
		}
		return true;
	}

	public int reverseDirection(int direction){
		switch(direction){
			case(1):
				return 2;
			case(2):
				return 1;
			case(3):
				return 4;
			case(4):
				return 3;
		}
		return direction;
	}

	public boolean isSatisfied(Piece piece){
		if(piece.getX()>=0 && piece.getY()>=0 && piece.getX()<nbLines && piece.getY()<nbColumns){
			return this.board.get(piece.getX()).get(piece.getY()).equals("[ ]");
		}
		return false;
	}

	public boolean isSatisfiedPiece(PieceInterface newPiece){
		boolean value = false;
		ArrayList<Piece> piece = newPiece.getCurrentPiece();
		for (Piece item : piece) {
			boolean inRangeMin = item.getX() >= 0 && item.getY() >= 0;
			boolean inRangeMax = item.getX() < nbLines && item.getY() < nbColumns;
			if (inRangeMin && inRangeMax) {
				boolean isEmpty = this.board.get(item.getX()).get(item.getY()).equals("[ ]");
				boolean isIn = this.board.get(item.getX()).get(item.getY()).equals(newPiece.getFilling());
				if (isEmpty || isIn) {
					value = true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return value;
	}

	@Override
	public String toString(){
		for (int i = 0; i < this.nbLines; i++) {
            for (int j = 0; j < this.nbColumns;j++) {
                System.out.print(this.board.get(j).get(i) + " ");
            }
            System.out.println();
        }
        return "";
	}

	public void gameOver(){
		System.out.println("=============== SCORE DE LA PARTIE ===============");
		int score = evaluate();
		String type = defineAreaType(score);
		System.out.println("---> Aire la plus grand : " + score + " cases, de type : " + type);
		System.out.println("=============== FIN DE LA PARTIE ===============");
	}

	//PARTIE EVALUATION SCORE

	public int evaluate(){
		return areaMax(getMatrix());
	}

	public String defineAreaType(int max){
		int maxX = 0, maxY = 0;
		for(ScoreWithVal swv : listSwv){
			if(swv.getScore() == max){
				maxX = swv.getX();
				maxY = swv.getY();
			}
		}
		//System.out.println("ER : " + maxX + " : "+maxY);
		if(maxX == 1){
			return "Colonne";
		}else if(maxY == 1){
			return "Ligne";
		}if(maxX == maxY){
			return "Carre";
		}else{
			return "Rectangle";
		}
	}

	public int areaMax(int[][] matrix) {
		int nbLine = matrix.length;
		int nbColumn = matrix[0].length;
		int[][] height = new int[nbLine][nbColumn + 1];
		int areaMax = 0;
		for (int i = 0; i < nbLine; i++) {
			for (int j = 0; j < nbColumn; j++) {
				if (matrix[i][j] == 0) {
					height[i][j] = 0;
				} else {
					if(i == 0){
						height[i][j] = 1;
					}else{
						height[i][j] = height[i - 1][j] + 1;
					}
				}
			}
		}
		for (int i = 0; i < nbLine; i++) {
			int area = maxAreaHeight(height[i]);
			if (area > areaMax) {
				areaMax = area;
			}
		}
		return areaMax;
	}

	private int maxAreaHeight(int[] columnSize) {
		Stack<Integer> stack = new Stack<Integer>();
		int lineSize = 0;
		int maximum = 0;
		while (lineSize < columnSize.length) {
			if (stack.isEmpty() || columnSize[stack.peek()] <= columnSize[lineSize]) {
				stack.push(lineSize);
				lineSize++;
			} else {
				int area;
				int x = stack.pop();
				ScoreWithVal swv = new ScoreWithVal(0, 0, 0);
				if(stack.isEmpty()){
					area = columnSize[x] * lineSize;
					swv.setX(lineSize);
					//System.out.println("Taille des lignes : " + lineSize);
				}else{
					area = columnSize[x] * (lineSize - stack.peek() - 1);
					swv.setX(lineSize - stack.peek() - 1);
					//System.out.println("Taille des lignes : " + (lineSize - stack.peek() - 1));
				}
				//System.out.println("Taille des colonnes : " + columnSize[x]);
				//System.out.println("Aires : " + area);
				//Suivie des valeurs
				swv.setScore(area);
				swv.setY(columnSize[x]);
				this.listSwv.add(swv);
				maximum = Math.max(maximum, area);
			}
		}
		return maximum;
	}

	//Fonction qui transforme notre plateau en matrice pour facilite le calcule du score
	public int[][] getMatrix(){
		int[][] matrix = new int[this.nbLines][this.nbColumns];
		for (int i = 0; i < this.nbLines; i++) {
			for (int j = 0; j < this.nbColumns; j++) {
				if(this.board.get(j).get(i).equals("[ ]")){
					matrix[i][j] = 0;
				}else{
					matrix[i][j] = 1;
				}
			}
		}
		return matrix;
	}

	//GETTER ET SETTER

	public String getCase(int x, int y){
		return this.board.get(x).get(y);
	}

	public ArrayList<ArrayList<String>> getBoard(){
		return this.board;
	}

	public int getColonnes() {
		return this.nbColumns;
	}

	public int getLignes() {
		return this.nbLines;
	}

	public void setBoard(ArrayList<ArrayList<String>> board) {
		this.board = board;
	}

	public int getCptMaxPieceOnBoard() {
		return cptMaxPieceOnBoard;
	}

	public void setListPiece(ArrayList<PieceInterface> listPiece) {
		this.listPiece = listPiece;
	}

	public ArrayList<PieceInterface> getListPiece() {
		return listPiece;
	}

	public ArrayList<String> getListRemplissage() {
		return listFilling;
	}

	public void setListFilling(ArrayList<String> listFilling) {
		this.listFilling = listFilling;
	}
}
