package jeu.model;

import jeu.Main;
import jeu.computer.Move;
import jeu.factory.*;
import jeu.save.Save;
import jeu.save.SaveStorage;
import jeu.save.SaveWriteRead;

import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Board implements Cloneable{

    private boolean demoMode = false;
    private boolean isOver = false;
    private boolean pieceAdded = false;
    private boolean isPlaying = false;
    private boolean newGame = false;
    private boolean isSolving = false;

    private Piece pieceFocused;
    private int nbMove;
    private SaveStorage saveStorage;
    static AtomicInteger nextId = new AtomicInteger();
    private int id;
    private String playerName;
    private final int nbLines;
    private final int nbColumns;
    private ArrayList<ArrayList<String>> board;
    private ArrayList<Piece> listPiece;
    private ArrayList<String> listFilling;
    private int cptMaxPieceOnBoard;
    ArrayList<PointWithScore> listSwv = new ArrayList<>();
    private int currentScore;

    public Board(int nbColumns, int nbLines, SaveStorage saveStorage) {
        this.nbMove = 0;
        this.saveStorage = saveStorage;
        this.id = nextId.incrementAndGet();
        this.cptMaxPieceOnBoard = 0;
        ArrayList<ArrayList<String>> board = initBoard(nbColumns, nbLines);
        this.listPiece = new ArrayList<>();
        this.board = board;
        this.nbLines = nbLines;
        this.nbColumns = nbColumns;
        this.listFilling = new ArrayList<>();
        List<String> anotherList = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        this.listFilling.addAll(anotherList);
    }

    /*=====================================*/
    /*===== INITIALISATION DE BOARD =======*/
    /*=====================================*/

    private ArrayList<ArrayList<String>> initBoard(int nbColumns, int nbLines) {
        ArrayList<ArrayList<String>> board = new ArrayList<>();
        for (int i = 0; i < nbColumns; i++) {
            ArrayList<String> line = new ArrayList<>();
            board.add(line);
            for (int j = 0; j < nbLines; j++) {
                line.add("[ ]");
            }
        }
        return board;
    }

    /*==================================*/
    /*===== REMPLISSAGE DE BOARD =======*/
    /*==================================*/

    public void fillBoardRandomly() {
        for (int i = 0; i < Main.MAX_NB_PIECE_ON_BOARD; i++) {
            addRandomPiece();
        }
    }

    public void fillBoardRandomly(int n) {
        for (int i = 0; i < n; i++) {
            addRandomPiece();
        }
    }

    public void fillBoardHello(int nbLines){
        //SPIRAL FILLING
        int[][] spiral = new int[nbLines][nbLines];
        int value = 1;
        int minCol = 0;
        int maxCol = nbLines -1;
        int minLine = 0;
        int maxLine = nbLines -1;
        while (value <= nbLines * nbLines) {
            ArrayList<String> line = new ArrayList<>();
            board.add(line);
            for (int i = minCol; i <= maxCol; i++) {
                spiral[minLine][i] = value;
                value++;
            }
            for (int i = minLine+1; i <= maxLine; i++) {
                spiral[i][maxCol] = value;
                value++;
            }
            for (int i = maxCol-1; i >= minCol; i--) {
                spiral[maxLine][i] = value;
                value++;
            }
            for (int i = maxLine-1; i >= minLine+1; i--) {
                spiral[i][minCol] = value;
                value++;
            }
            minCol++;
            minLine++;
            maxCol--;
            maxLine--;
        }
        ArrayList<ArrayList<String>> board = new ArrayList<>();
        for (int[] ints : spiral) {
            ArrayList<String> lines = new ArrayList<>();
            for (int j = 0; j < spiral.length; j++) {
                //System.out.print(spiral[i][j]+ "\t");
                lines.add("[" + listFilling.get(ints[j] % 26) + "]");
            }
            board.add(lines);
            //System.out.println();
        }
        this.board = board;
    }

    /*======================================*/
    /*===== SAUVEGARDER DE LA PARTIE =======*/
    /*======================================*/

    public void saveBoard(int nbSave, SaveStorage saveStorage){
        if(!saveStorage.hasAlreadyBoard(getBoard())){
            if(this.currentScore > 0){
                nbSave += saveStorage.getSize();
                new Save(saveStorage, getPlayerName(), nbSave, getNbLines(),
                        getNbColumns(), getBoard(), getCurrentScore(), getListPiece(), getListFilling(), getCptMaxPieceOnBoard(), getListSwv(), getNbMove());
                try {
                    SaveWriteRead.writeFile("save.txt", saveStorage);
                    System.out.println("PARTIE SAUVEGARDE");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else{
                System.out.println("PARTIE NON SAUVEGARDE CAR SCORE NUL");
            }
        }else{
            System.out.println("CETTE PARTIE EST DEJA EXISTANTE PARMIS LES SAUVEGARDES ACTUELLES !");
        }
    }

    /*===================================*/
    /*===== PARTIE CLEARING BOARD =======*/
    /*===================================*/

    public void clear() {
        clearListPiece();
        clearBoard();
        clearListFilling();
    }

    public void clearListPiece() {
        this.listPiece = new ArrayList<>();
    }

    public void clearBoard() {
        ArrayList<ArrayList<String>> newBoard = initBoard(nbColumns, nbLines);
        this.board = newBoard;
    }

    public void clearListFilling() {
        List<String> anotherList = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        this.listFilling = new ArrayList<>(anotherList);
    }

    /*===================================*/
    /*===== PARTIE ADD/DEL PIECES =======*/
    /*===================================*/

    public boolean addPiece(Piece piece) {
        if (this.isSatisfiedPiece(piece)) {
            this.delPiece(piece.getFilling());
            for (int i = 0; i < this.board.size(); i++) {
                for (int j = 0; j < this.board.size(); j++) {
                    for (int k = 0; k < piece.getCurrentPiece().size(); k++) {
                        if (piece.getCurrentPiece().get(k).getX() == i && piece.getCurrentPiece().get(k).getY() == j) {
                            this.board.get(i).set(j, piece.getFilling());
                        }
                    }
                }
            }
            ArrayList<Piece> tmp = new ArrayList<>();
            for(Piece p : this.listPiece){
                if(!p.getFilling().equals(piece.getFilling())){
                    tmp.add(p);
                }
            }
            tmp.add(piece);
            this.setListPiece(tmp);
            return true;
        } else {
            return false;
        }
    }

    public int randomIntLimit(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void addRandomPiece() throws IllegalStateException {
        boolean isValid = false;
        Piece randomPiece;
        //On cree des pieces aleatoires jusqu'a ce qu'une piece soit valide
        while (!isValid) {
            //Parametres aleatoires
            int randomType = randomIntLimit(1, Main.NB_DIFF_PIECE);
            int randomFillingIndex = randomIntLimit(0, this.listFilling.size() - 1);
            String randomFilling = this.listFilling.get(randomFillingIndex); //On enleve ce remplissage de la liste
            int randomWidth = randomIntLimit(1, this.nbLines);
            int randomHeight = randomIntLimit(1, this.nbColumns);
            Point randomCentralPiece = new Point(randomWidth, randomHeight);
            int randomState = randomIntLimit(1, 4);
            int randomPieceWidth = randomIntLimit(Main.MIN_PIECE, Main.MAX_PIECE);
            int randomPieceHeight = randomIntLimit(Main.MIN_PIECE, Main.MAX_PIECE);
            int randomPieceWidth2 = randomIntLimit(Main.MIN_PIECE, Main.MAX_PIECE);
            //Creation de la piece random
            switch (randomType) {
                case 1:
                    randomPiece = new PieceL(randomCentralPiece, randomFilling, randomPieceHeight, randomPieceWidth, randomState);
                    break;
                case 2:
                    randomPiece = new PieceS(randomCentralPiece, randomFilling, randomPieceHeight, randomPieceWidth, randomPieceWidth2, randomState);
                    break;
                case 3:
                    randomPiece = new PieceT(randomCentralPiece, randomFilling, randomPieceHeight, randomPieceWidth, 3);
                    break;
                case 4:
                    randomPiece = new PieceI(randomCentralPiece, randomFilling, randomPieceHeight, 1, randomState);
                    break;
                case 5:
                    randomPiece = new PieceO(randomCentralPiece, randomFilling, randomPieceWidth, randomPieceWidth, randomState);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + randomType);
            }
            //Verification si la randomPiece rentre sur le plateau sans probleme
            isValid = addPiece(randomPiece);
            //On enleve la lettre seulement si on a trouve un emplacement valide pour la piece !
            if (isValid) {
                this.listFilling.remove(randomFillingIndex);
            }
        }
        this.cptMaxPieceOnBoard++;
    }

    public void delPiece(String filling) {
        for (int i = 0; i < this.nbColumns; i++) {
            for (int j = 0; j < this.nbLines; j++) {
                if (this.board.get(i).get(j).equals(filling)) {
                    this.board.get(i).set(j, "[ ]");
                }
            }
        }
    }

    /*=====================================*/
    /*===== PARTIE ACTION SUR PIECE =======*/
    /*=====================================*/

    public boolean rotatePiece(boolean direction, Piece piece) {
        piece.rotate(direction);
        if (!(this.addPiece(piece))) {
            piece.rotate(!(direction));
            return false;
        }
        return true;
    }

    public boolean translatePiece(int direction, Piece piece) {
        Piece newPiece = piece.translation(direction);
        if (!(this.addPiece(newPiece))) {
            newPiece.translation(this.reverseDirection(direction));
            return false;
        }
        return true;
    }

    public int reverseDirection(int direction) {
        switch (direction) {
            case (1):
                return 2;
            case (2):
                return 1;
            case (3):
                return 4;
            case (4):
                return 3;
        }
        return direction;
    }

    /*=========================================*/
    /*===== PARTIE VERIFICATION PLACEMENT =====*/
    /*=========================================*/

    public boolean isSatisfied(Point piece) {
        if (piece.getX() >= 0 && piece.getY() >= 0 && piece.getX() < nbLines && piece.getY() < nbColumns) {
            return this.board.get(piece.getX()).get(piece.getY()).equals("[ ]");
        }
        return false;
    }

    public boolean isSatisfiedPiece(Piece newPiece) {
        boolean value = false;
        ArrayList<Point> piece = newPiece.getCurrentPiece();
        for (Point item : piece) {
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

    /*========================================*/
    /*===== PARTIE FONCTIONS PRATIQUES =======*/
    /*========================================*/

    public void gameOver() {
        System.out.println("=============== SCORE DE LA PARTIE ===============");
        int score = evaluate();
        String type = defineAreaType(score);
        System.out.println("---> Aire la plus grand : " + score + " cases, de type : " + type);
        System.out.println("=============== FIN DE LA PARTIE ===============");
    }

    public void movePlus(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
            case KeyEvent.VK_Q:
            case KeyEvent.VK_Z:
                this.nbMove++;
                break;
        }
    }

    @Override
    public String toString() {
        for (int i = 0; i < this.nbLines; i++) {
            for (int j = 0; j < this.nbColumns; j++) {
                System.out.print(this.board.get(j).get(i) + " ");
            }
            System.out.println();
        }
        return "";
    }

    /*=======================*/
    /*===== PARTIE IA =======*/
    /*=======================*/

    @Override
    public Board clone(){
        Object o = null;
        try{
            o = super.clone();
        }catch(CloneNotSupportedException cnse){
            cnse.printStackTrace(System.err);
        }
        return (Board)o;
    }

    public ArrayList<Piece> copyListPiece(){
        ArrayList<Piece> copyListPiece = new ArrayList<>();
        for(Piece p : this.listPiece){
            copyListPiece.add(p.clone());
        }
        return copyListPiece;
    }

    public ArrayList<ArrayList<String>> copyBoard(){
        ArrayList<ArrayList<String>>  copyBoard = new ArrayList<>();
        for(ArrayList<String> list : new ArrayList<>(this.board)){
            copyBoard.add(list);
            for(String str : new ArrayList<>(list)){
                list.add(str);
            }
        }
        return copyBoard;
    }

    public void makeMove(Move move){
        switch (move.getTypeMove()){
            case "haut" :
                this.translatePiece(3, move.getPiece());
                break;
            case "bas" :
                this.translatePiece(4, move.getPiece());
                break;
            case "gauche" :
                this.translatePiece(1, move.getPiece());
                break;
            case "droite" :
                this.translatePiece(2, move.getPiece());
                break;
            case "trueRotation" :
                this.rotatePiece(true, move.getPiece());
                break;
            case "falseRotation" :
                this.rotatePiece(false, move.getPiece());
                break;
        }
    }

    public void makeInverseMove(Move move){
        switch (move.getTypeMove()){
            case "haut" :
                this.translatePiece(reverseDirection(3), move.getPiece());
                break;
            case "bas" :
                this.translatePiece(reverseDirection(4), move.getPiece());
                break;
            case "gauche" :
                this.translatePiece(reverseDirection(1), move.getPiece());
                break;
            case "droite" :
                this.translatePiece(reverseDirection(2), move.getPiece());
                break;
            case "trueRotation" :
                System.out.println("trigger");
                this.rotatePiece(false, move.getPiece());
                break;
            case "falseRotation" :
                this.rotatePiece(true, move.getPiece());
                break;
        }
    }

    public ArrayList<Move> getValidMoves(Piece piece){
        Board copyBoard = this.clone();
        copyBoard.setListPiece(this.copyListPiece());
        ArrayList<Move> validMoves = new ArrayList<>();
        if(copyBoard.translatePiece(3, piece)){
            validMoves.add(new Move(piece, "haut"));
            copyBoard.translatePiece(reverseDirection(3), piece);
        }
        if(copyBoard.translatePiece(4, piece)){
            validMoves.add(new Move(piece, "bas"));
            copyBoard.translatePiece(reverseDirection(4), piece);
        }
        if(copyBoard.translatePiece(1, piece)){
            validMoves.add(new Move(piece, "gauche"));
            copyBoard.translatePiece(reverseDirection(1), piece);
        }
        if(copyBoard.translatePiece(2, piece)){
            validMoves.add(new Move(piece, "droite"));
            copyBoard.translatePiece(reverseDirection(2), piece);
        }
        return validMoves;
    }

    public String tradDirection(int direction){
        switch(direction){
            case 3 :
                return "haut";
            case 4 :
                return "bas";
            case 1 :
                return "gauche";
            case 2 :
                return "droite";
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    /*=====================================*/
    /*===== PARTIE EVALUATION SCORE =======*/
    /*=====================================*/

    public int evaluate() {
        int currentScore = areaMax(getMatrix());
        this.currentScore = currentScore;
        return currentScore;
    }

    public String defineAreaType(int max) {
        int maxX = 0;
        int maxY = 0;
        for (PointWithScore pws : listSwv) {
            if (pws.getScore() == max) {
                maxX = pws.getX();
                maxY = pws.getY();
            }
        }
        //System.out.println("ER : " + maxX + " : "+maxY);
        if (maxX == 1) {
            return "Colonne";
        } else if (maxY == 1) {
            return "Ligne";
        }
        if (maxX == maxY) {
            return "Carre";
        } else {
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
                    if (i == 0) {
                        height[i][j] = 1;
                    } else {
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
        Stack<Integer> stack = new Stack<>();
        int lineSize = 0;
        int maximum = 0;
        while (lineSize < columnSize.length) {
            if (stack.isEmpty() || columnSize[stack.peek()] <= columnSize[lineSize]) {
                stack.push(lineSize);
                lineSize++;
            } else {
                int area;
                int x = stack.pop();
                PointWithScore pws = new PointWithScore(new Point(0,0), 0);
                if (stack.isEmpty()) {
                    area = columnSize[x] * lineSize;
                    pws.setX(lineSize);
                    //System.out.println("Taille des lignes : " + lineSize);
                } else {
                    area = columnSize[x] * (lineSize - stack.peek() - 1);
                    pws.setX(lineSize - stack.peek() - 1);
                    //System.out.println("Taille des lignes : " + (lineSize - stack.peek() - 1));
                }
                //System.out.println("Taille des colonnes : " + columnSize[x]);
                //System.out.println("Aires : " + area);
                //Suivie des valeurs
                pws.setScore(area);
                pws.setY(columnSize[x]);
                this.listSwv.add(pws);
                maximum = Math.max(maximum, area);
            }
        }
        return maximum;
    }

    //Fonction qui transforme notre plateau en matrice pour facilite le calcule du score
    public int[][] getMatrix() {
        int[][] matrix = new int[this.nbLines][this.nbColumns];
        for (int i = 0; i < this.nbLines; i++) {
            for (int j = 0; j < this.nbColumns; j++) {
                if (this.board.get(j).get(i).equals("[ ]")) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = 1;
                }
            }
        }
        return matrix;
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public boolean getDemoMode() {
        return this.demoMode;
    }

    public void setDemoMode(boolean demoMode) {
        this.demoMode = demoMode;
    }

    public ArrayList<PointWithScore> getListSwv() { return listSwv; }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public String getCase(int x, int y) {
        return this.board.get(x).get(y);
    }

    public ArrayList<ArrayList<String>> getBoard() {
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

    public static AtomicInteger getNextId() {
        return nextId;
    }

    public static void setNextId(AtomicInteger nextId) {
        Board.nextId = nextId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setListPiece(ArrayList<Piece> listPiece) {
        this.listPiece = listPiece;
    }

    public ArrayList<Piece> getListPiece() {
        return listPiece;
    }

    public int getNbLines() {
        return nbLines;
    }

    public int getNbColumns() {
        return nbColumns;
    }

    public ArrayList<String> getListFilling() {
        return listFilling;
    }

    public void setCptMaxPieceOnBoard(int cptMaxPieceOnBoard) {
        this.cptMaxPieceOnBoard = cptMaxPieceOnBoard;
    }

    public void setListSwv(ArrayList<PointWithScore> listSwv) {
        this.listSwv = listSwv;
    }

    public ArrayList<String> getListRemplissage() {
        return listFilling;
    }

    public void setListFilling(ArrayList<String> listFilling) {
        this.listFilling = listFilling;
    }

    public SaveStorage getSaveStorage() {
        return saveStorage;
    }

    public void setSaveStorage(SaveStorage saveStorage) {
        this.saveStorage = saveStorage;
    }

    public int getNbMove() {
        return this.nbMove;
    }
    
    public Piece getPieceFocused(){
        return this.pieceFocused;
    }

    public boolean getIsOver(){
        return this.isOver;
    }

    public boolean getAdded(){
        return this.pieceAdded;
    }

    public boolean getIsPlaying(){
        return this.isPlaying;
    }

    public boolean getNewGame(){
        return this.newGame;
    }

    public boolean getIsSolving(){
        return this.isSolving;
    }
    
    public void setPieceFocused(Piece pieceFocused) {
        this.pieceFocused = pieceFocused;
    }

    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }

    public void setOver(boolean isOver) {
        this.isOver = isOver;
    }

    public void setPieceAdded(boolean pieceAdded) {
        this.pieceAdded = pieceAdded;
    }

    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public void setSolving(boolean isSolving) {
        this.isSolving = isSolving;
    }

    public void setNbMove(int nbMove) {
        this.nbMove = nbMove;
    }

}
