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

/**
 * Classe principale du model, elle contient la grille de jeu
 */
public class Board implements Cloneable{

    private boolean demoMode = false;
    private boolean isOver = false;
    private boolean pieceAdded = false;
    private boolean isPlaying = false;
    private boolean newGame = false;
    private boolean isSolving = false;
    private boolean solverTest = false;

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

    /**
     * Constructeur de l'object Board
     * @param nbColumns nombre de colonnes de la grille
     * @param nbLines nombre de lignes de la grille
     * @param saveStorage object stockant les sauveguardes
     */
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

    /**
     * Initialise la grille en mettant toute les valeurs a zero
     * @param nbColumns nombre de colonnes de la grille
     * @param nbLines nombre de lignes de la grille
     * @return la board initialisee
     */
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

    /**
     * Remplit aleatoirement la board de piece suivant le nombre Main.MAX_NB_PIECE_ON_BOARD
     */
    public void fillBoardRandomly() {
        for (int i = 0; i < Main.MAX_NB_PIECE_ON_BOARD; i++) {
            addRandomPiece();
        }
    }

    /**
     * Remplit aleatoirement la board de piece suivant le nombre n
     * @param n nombre de pieces a mettre
     */
    public void fillBoardRandomly(int n) {
        for (int i = 0; i < n; i++) {
            addRandomPiece();
        }
    }

    /**
     * Remplit la board de presentation en utilisant un algorithme permettant de faire une spirale
     * @param nbLines nombre de ligne de la grille
     */
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

    /**
     * Sauveguarde la partie
     * @param nbSave numero de la partie
     * @param saveStorage object contenant la liste des sauveguardes
     */
    public void saveBoard(int nbSave, SaveStorage saveStorage){
        if(!saveStorage.hasAlreadyBoard(getBoard())){
            if(this.currentScore > 0){
                nbSave += saveStorage.getSize();
                new Save(saveStorage, getPlayerName(), nbSave, getLignes(),
                        getColonnes(), getBoard(), getCurrentScore(), getListPiece(), getListFilling(), getCptMaxPieceOnBoard(), getListSwv(), getNbMove());
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

    /**
     * Reset toute la board
     */
    public void clear() {
        clearListPiece();
        clearBoard();
        clearListFilling();
    }

    /**
     * Reset la liste des pieces
     */
    public void clearListPiece() {
        this.listPiece = new ArrayList<>();
    }

    /**
     * Reinitialise le contenu de la board
     */
    public void clearBoard() {
        ArrayList<ArrayList<String>> newBoard = initBoard(nbColumns, nbLines);
        this.board = newBoard;
    }

    /**
     * Reinitialise la liste des caracteres disponibles pour les pieces
     */
    public void clearListFilling() {
        List<String> anotherList = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        this.listFilling = new ArrayList<>(anotherList);
    }

    /*===================================*/
    /*===== PARTIE ADD/DEL PIECES =======*/
    /*===================================*/

    /**
     * Ajoute une piece a la liste des pieces si c'est possible
     * @param piece la piece que l'on veut ajouter
     * @return booleen indiquant si l'ajout a ete effectue
     */
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

    /**
     * Renvoie un nombre aleatoire entre min et max
     * @param min borne minimum
     * @param max borne maximum
     * @return le nombre aleatoire borne
     */
    public int randomIntLimit(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * Methode permettant d'essayer d'ajouter une piece jusqu'a temps qu'elle soit
     * valide en appelant la fonction Board.addPiece();
     */
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
                    randomPiece = new PieceT(randomCentralPiece, randomFilling, randomPieceHeight, randomPieceWidth, randomState);
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

    /**
     * Methode permettant de supprimer du board une piece caracterise par une certaine lettre
     * @param filling - Lettre caracteristant une piece
     */
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

    /**
     * Methode permettant de pivoter une piece dans une direction voulu.
     * Si la rotation ne peut pas etre effectue, on renvoie false et l'action
     * est annulee
     * @param direction - Direction voulue
     * @param piece - Piece a pivoter
     * @return Boleen de succes / echec
     */
    public boolean rotatePiece(boolean direction, Piece piece) {
        piece.rotate(direction);
        if (!(this.addPiece(piece))) {
            piece.rotate(!(direction));
            return false;
        }
        return true;
    }

    /**
     * Methode permettant de bouger une piece vers une direction voulue.
     * Si la translation n'est pas possible, on renvoie false et l'action est annulee
     * @param direction - Direction voulue
     * @param piece - Piece a pivoter
     * @return Boleen de succes / echec
     */
    public boolean translatePiece(int direction, Piece piece) {
        Piece newPiece = piece.translation(direction);
        if (!(this.addPiece(newPiece))) {
            newPiece.translation(this.reverseDirection(direction));
            return false;
        }
        return true;
    }

    /**
     * Methode permettant d'inverser la direction d'une piece.
     * Ex : Une piece est vers le haut, elle passe vers le bas.
     * @param direction - Direction actuelle a inverser
     * @return direction opposee
     */
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

    /**
     * Methode permettant de tester si un point est correctement place
     * @param piece - Point a verifier
     * @return Booleen de succes/echec
     */
    public boolean isSatisfied(Point piece) {
        if (piece.getX() >= 0 && piece.getY() >= 0 && piece.getX() < nbLines && piece.getY() < nbColumns) {
            return this.board.get(piece.getX()).get(piece.getY()).equals("[ ]");
        }
        return false;
    }

    /**
     * Methode permettant de tester si une piece est correctement placee
     * @param newPiece - Piece a verifier
     * @return Booleen de succes/echec
     */
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

    /**
     * Methode permettant d'afficher la fin d'une partie et de calculer le score obtenu
     */
    public void gameOver() {
        System.out.println("=============== SCORE DE LA PARTIE ===============");
        int score = evaluate();
        //String type = defineAreaType(score);
        //System.out.println("---> Aire la plus grand : " + score + " cases, de type : " + type);
        System.out.println("---> Aire la plus grand : " + score);
        System.out.println("=============== FIN DE LA PARTIE ===============");
    }

    /**
     * Methode permettant d'incrementer le compteur de mouvements pour la partie graphique
     * @param e - Event a gerer
     */
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

    /**
     * Methode permettant d'afficher la grille dans le terminal
     */
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

    /**
     * Methode permettant de cloner l'object Board
     * @return l'objet Board clone
     */
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

    /**
     * Methode permettant d'effectuer une copie des pieces du board actuel a des fins
     * de traitement pour l'IA.
     * @return Copie des pieces actuelles sur le board
     */
    public ArrayList<Piece> copyListPiece(){
        ArrayList<Piece> copyListPiece = new ArrayList<>();
        for(Piece p : this.listPiece){
            copyListPiece.add(p.clone());
        }
        return copyListPiece;
    }

    /**
     * Methode permettant de retourner une copie du board actuel a des fins de
     * traitement pour l'IA
     * @return Copie du board.
     */
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

//    Methodes pouvant etre utile pour simplifier le code mais non implementees.
//    public void makeMove(Move move){
//        switch (move.getTypeMove()){
//            case "haut" :
//                this.translatePiece(1, move.getPiece());
//                break;
//            case "bas" :
//                this.translatePiece(2, move.getPiece());
//                break;
//            case "gauche" :
//                this.translatePiece(3, move.getPiece());
//                break;
//            case "droite" :
//                this.translatePiece(4, move.getPiece());
//                break;
//            case "trueRotation" :
//                this.rotatePiece(true, move.getPiece());
//                break;
//            case "falseRotation" :
//                this.rotatePiece(false, move.getPiece());
//                break;
//        }
//    }
//
//    public void makeInverseMove(Move move){
//        switch (move.getTypeMove()){
//            case "haut" :
//                this.translatePiece(reverseDirection(1), move.getPiece());
//                break;
//            case "bas" :
//                this.translatePiece(reverseDirection(2), move.getPiece());
//                break;
//            case "gauche" :
//                this.translatePiece(reverseDirection(3), move.getPiece());
//                break;
//            case "droite" :
//                this.translatePiece(reverseDirection(4), move.getPiece());
//                break;
//            case "trueRotation" :
//                this.rotatePiece(false, move.getPiece());
//                break;
//            case "falseRotation" :
//                this.rotatePiece(true, move.getPiece());
//                break;
//        }
//    }

    /**
     * Methode permettant de retourner tout les mouvements possibles d'une piece
     * a partir de sa position actuelle
     * @param piece - Piece a manipuler
     * @return Liste de tout les mouvements possibles.
     */
    public ArrayList<Move> getValidMoves(Piece piece){
        Board copyBoard = this.clone();
        Piece tmp = piece.clone();
        copyBoard.setListPiece(this.copyListPiece());
        ArrayList<Move> validMoves = new ArrayList<>();
        if(copyBoard.translatePiece(1, tmp)){
            validMoves.add(new Move(piece, "haut"));
            copyBoard.translatePiece(reverseDirection(1), tmp);
        }
        if(copyBoard.translatePiece(2, tmp)){
            validMoves.add(new Move(piece, "bas"));
            copyBoard.translatePiece(reverseDirection(2), tmp);
        }
        if(copyBoard.translatePiece(3, tmp)){
            validMoves.add(new Move(piece, "gauche"));
            copyBoard.translatePiece(reverseDirection(3), tmp);
        }
        if(copyBoard.translatePiece(4, tmp)){
            validMoves.add(new Move(piece, "droite"));
            copyBoard.translatePiece(reverseDirection(4), tmp);
        }
        if(Main.ROTATION_ACTIVATED_SOLVER){ //Si la recherche des rotations pour le solver est activee alors on ajoute les rotations aux coups valides
            if(copyBoard.rotatePiece(true, tmp)){
                validMoves.add(new Move(piece, "trueRotation"));
                copyBoard.rotatePiece(false , tmp);
            }
            if(copyBoard.rotatePiece(false, tmp)){
                validMoves.add(new Move(piece, "falseRotation"));
                copyBoard.rotatePiece(true, tmp);
            }
        }
        return validMoves;
    }

    /**
     * Methode permettant de traduire en entier une direction voulue
     * @param direction - Direction voulue
     * @return Entier correspondant
     */
    public int tradDirection(String direction){
        switch(direction){
            case "haut" :
                return 1;
            case "bas" :
                return 2;
            case "gauche" :
                return 3;
            case "droite" :
                return 4;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    /*=====================================*/
    /*===== PARTIE EVALUATION SCORE =======*/
    /*=====================================*/

    /**
     * Methode permettant d'evaluer le score actuel pendant la partie
     * @return Score actuel
     */
    public int evaluate() {
        int currentScore = areaMax(getMatrix());
        this.currentScore = currentScore;
        return currentScore;
    }

    /**
     * Methode permettant de definir le type de l'aire de pieces la plus grosse
     * du board actuel
     * @param max - score maximum
     * @return le type d'aire
     */
//    public String defineAreaType(int max) {
//        int maxX = 0;
//        int maxY = 0;
//        for (PointWithScore pws : listSwv) {
//            if (pws.getScore() == max) {
//                maxX = pws.getX();
//                maxY = pws.getY();
//            }
//        }
//        //System.out.println("ER : " + maxX + " : "+maxY);
//        if (maxX == 1) {
//            return "Colonne";
//        } else if (maxY == 1) {
//            return "Ligne";
//        }
//        if (maxX == maxY) {
//            return "Carre";
//        } else {
//            return "Rectangle";
//        }
//    }

    /**
     * Methode permettant de recuperer l'aire maximum d'un
     * rectange forme de 1 dans une matrice binaire
     * @param matrix matrice binaire
     * @return la plus grosse aire rectangulaire
     */
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

    /**
     * Methode permettant de retourner l'aire maximum pour une colonne donnee
     * @param columnSize la plus grande colonne
     * @return maximum l'aire maximum trouvee
     */
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
                int stackElement = stack.pop();
//                PointWithScore pws = new PointWithScore(new Point(0,0), 0);
                if (stack.isEmpty()) {
                    area = columnSize[stackElement] * lineSize;
//                    pws.setX(lineSize);
//                    System.out.println("Taille des lignes : " + lineSize);
                } else {
                    area = columnSize[stackElement] * (lineSize - stack.peek() - 1);
//                    pws.setX(lineSize - stack.peek() - 1);
//                    System.out.println("Taille des lignes : " + (lineSize - stack.peek() - 1));
                }
//                System.out.println("Taille des colonnes : " + columnSize[x]);
//                System.out.println("Aires : " + area);
//                pws.setScore(area); //Suivie des valeurs
//                pws.setY(columnSize[stackElement]);
//                this.listSwv.add(pws);
                maximum = Math.max(maximum, area); //Recuperation de la valeur maximum
            }
        }
        return maximum;
    }

    /**
     * Fonction qui transforme notre plateau en matrice binaire pour facilite le calcule du score
     * @return Matrice
     */
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
        return demoMode;
    }

    public void setDemoMode(boolean demoMode) {
        this.demoMode = demoMode;
    }

    public boolean getIsOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public boolean isPieceAdded() {
        return pieceAdded;
    }

    public void setPieceAdded(boolean pieceAdded) {
        this.pieceAdded = pieceAdded;
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean getIsNewGame() {
        return newGame;
    }

    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }

    public boolean getIsSolving() {
        return isSolving;
    }

    public void setSolving(boolean solving) {
        isSolving = solving;
    }

    public boolean isSolverTest() {
        return solverTest;
    }

    public void setSolverTest(boolean solverTest) {
        this.solverTest = solverTest;
    }

    public Piece getPieceFocused() {
        return pieceFocused;
    }

    public void setPieceFocused(Piece pieceFocused) {
        this.pieceFocused = pieceFocused;
    }

    public int getNbMove() {
        return nbMove;
    }

    public void setNbMove(int nbMove) {
        this.nbMove = nbMove;
    }

    public SaveStorage getSaveStorage() {
        return saveStorage;
    }

    public void setSaveStorage(SaveStorage saveStorage) {
        this.saveStorage = saveStorage;
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLignes() {
        return nbLines;
    }

    public int getColonnes() {
        return nbColumns;
    }

    public ArrayList<ArrayList<String>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<String>> board) {
        this.board = board;
    }

    public ArrayList<Piece> getListPiece() {
        return listPiece;
    }

    public void setListPiece(ArrayList<Piece> listPiece) {
        this.listPiece = listPiece;
    }

    public ArrayList<String> getListFilling() {
        return listFilling;
    }

    public void setListFilling(ArrayList<String> listFilling) {
        this.listFilling = listFilling;
    }

    public int getCptMaxPieceOnBoard() {
        return cptMaxPieceOnBoard;
    }

    public void setCptMaxPieceOnBoard(int cptMaxPieceOnBoard) {
        this.cptMaxPieceOnBoard = cptMaxPieceOnBoard;
    }

    public ArrayList<PointWithScore> getListSwv() {
        return listSwv;
    }

    public void setListSwv(ArrayList<PointWithScore> listSwv) {
        this.listSwv = listSwv;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }
}
