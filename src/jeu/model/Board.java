package jeu.model;

import jeu.Main;
import jeu.factory.*;
import jeu.save.Save;
import jeu.save.SaveStorage;
import jeu.save.SaveWriteRead;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Board {

    private SaveStorage saveStorage;

    static AtomicInteger nextId = new AtomicInteger();
    private int id;

    private String playerName;

    private final int nbLines;
    private final int nbColumns;
    private ArrayList<ArrayList<String>> board;
    private ArrayList<PieceInterface> listPiece;
    private ArrayList<String> listFilling;
    private int cptMaxPieceOnBoard;
    ArrayList<ScoreWithVal> listSwv = new ArrayList<>();
    private int currentScore;

    public Board(int nbColumns, int nbLines, SaveStorage saveStorage) {
        this.saveStorage = saveStorage;
        this.id = nextId.incrementAndGet();
        this.cptMaxPieceOnBoard = 0;
        ArrayList<ArrayList<String>> board = new ArrayList<>();
        for (int i = 0; i < nbColumns; i++) {
            ArrayList<String> line = new ArrayList<>();
            board.add(line);
            for (int j = 0; j < nbLines; j++) {
                line.add("[ ]");
            }
        }
        this.listPiece = new ArrayList<>();
        this.board = board;
        this.nbLines = nbLines;
        this.nbColumns = nbColumns;
        this.listFilling = new ArrayList<>();
        List<String> anotherList = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        this.listFilling.addAll(anotherList);
    }

    public void fillBoardRandomly() {
        for (int i = 0; i < Main.MAX_NB_PIECE_ON_BOARD; i++) {
            addRandomPiece();
        }
    }

    public void saveBoard(int nbSave, SaveStorage saveStorage){
        if(saveStorage != null) {
            nbSave += saveStorage.getSize();
        }
        Save save = new Save(saveStorage, getPlayerName(), nbSave, getNbLines(),
                getNbColumns(), getBoard(), getCurrentScore(), getListPiece(), getListFilling(), getCptMaxPieceOnBoard(), getListSwv());
        try {
            SaveWriteRead.writeFile("save.txt", saveStorage);
            System.out.println("PARTIE SAUVEGUARDE");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void clear() {
        clearListPiece();
        clearBoard();
        clearListFilling();
    }

    public void clearListPiece() {
        this.listPiece = new ArrayList<>();
    }

    public void clearBoard() {
        ArrayList<ArrayList<String>> newBoard = new ArrayList<>();
        for (int i = 0; i < nbColumns; i++) {
            ArrayList<String> line = new ArrayList<>();
            newBoard.add(line);
            for (int j = 0; j < nbLines; j++) {
                line.add("[ ]");
            }
        }
        this.board = newBoard;
    }

    public void clearListFilling() {
        List<String> anotherList = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        this.listFilling = new ArrayList<>(anotherList);
    }

    public boolean addPiece(PieceInterface piece) {
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
            this.listPiece.remove(piece);
            this.listPiece.add(piece);
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
        PieceInterface randomPiece;
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

    public void delPiece(String filling) {
        for (int i = 0; i < this.nbColumns; i++) {
            for (int j = 0; j < this.nbLines; j++) {
                if (this.board.get(i).get(j).equals(filling)) {
                    this.board.get(i).set(j, "[ ]");
                }
            }
        }
    }

    public void rotatePiece(boolean direction, PieceInterface piece) {
        piece.rotate(direction);
        if (!(this.addPiece(piece))) {
            piece.rotate(!(direction));
        }
    }

    public boolean translatePiece(int direction, PieceInterface piece) {
        PieceInterface newPiece = piece.translation(direction);
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

    public boolean isSatisfied(Point piece) {
        if (piece.getX() >= 0 && piece.getY() >= 0 && piece.getX() < nbLines && piece.getY() < nbColumns) {
            return this.board.get(piece.getX()).get(piece.getY()).equals("[ ]");
        }
        return false;
    }

    public boolean isSatisfiedPiece(PieceInterface newPiece) {
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

    public void gameOver() {
        System.out.println("=============== SCORE DE LA PARTIE ===============");
        int score = evaluate();
        String type = defineAreaType(score);
        System.out.println("---> Aire la plus grand : " + score + " cases, de type : " + type);
        System.out.println("=============== FIN DE LA PARTIE ===============");
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

    //PARTIE EVALUATION SCORE

    public int evaluate() {
        int currentScore = areaMax(getMatrix());
        this.currentScore = currentScore;
        return currentScore;
    }

    public String defineAreaType(int max) {
        int maxX = 0, maxY = 0;
        for (ScoreWithVal swv : listSwv) {
            if (swv.getScore() == max) {
                maxX = swv.getX();
                maxY = swv.getY();
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
                ScoreWithVal swv = new ScoreWithVal(0, 0, 0);
                if (stack.isEmpty()) {
                    area = columnSize[x] * lineSize;
                    swv.setX(lineSize);
                    //System.out.println("Taille des lignes : " + lineSize);
                } else {
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

    public ArrayList<ScoreWithVal> getListSwv() { return listSwv; }

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

    public void setListPiece(ArrayList<PieceInterface> listPiece) {
        this.listPiece = listPiece;
    }

    public ArrayList<PieceInterface> getListPiece() {
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

    public void setListSwv(ArrayList<ScoreWithVal> listSwv) {
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

}
