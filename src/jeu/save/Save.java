package jeu.save;

import jeu.factory.Piece;
import jeu.model.PointWithScore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Classe représentant la sauvegarde d'une partie
 */
public class Save implements Serializable {

    private int id;
    private int saveNumber;
    static final AtomicInteger nextSaveNumber = new AtomicInteger();
    private String playerName;
    private ArrayList<Save> listSave = new ArrayList<>();
    private final int nbLines;
    private final int nbColumns;
    private ArrayList<ArrayList<String>> board;
    private ArrayList<Piece> listPiece;
    private ArrayList<String> listFilling;
    private int cptMaxPieceOnBoard;
    ArrayList<PointWithScore> listSwv;
    private int currentScore;
    private int nbMovePlayed;

    /**
     * Constructeur
     * @param saveStorage l'object stockant les sauvegardes
     * @param playerName le nom du joueur
     * @param id le numero de la partie
     * @param nbLines le nombre de lignes de la grille
     * @param nbColumns le nombre de colonnes de la grille
     * @param board la grille
     * @param currentScore le score de la grille
     * @param listPiece la liste des pieces de la partie
     * @param listFilling la liste des caracteres associes aux pieces
     * @param cptMaxPieceOnBoard le nombre de piece sur la grille
     * @param listSwv la liste des objets ScoreWithVal
     * @param nbMovePlayed le nombre de coups joues sur cette partie
     */
    public Save(SaveStorage saveStorage, String playerName, int id, int nbLines, int nbColumns, ArrayList<ArrayList<String>> board, int currentScore, ArrayList<Piece> listPiece, ArrayList<String> listFilling, int cptMaxPieceOnBoard, ArrayList<PointWithScore> listSwv, int nbMovePlayed) {
        this.id = id;
        this.playerName = playerName;
        this.saveNumber = nextSaveNumber.incrementAndGet();
        this.nbLines = nbLines;
        this.nbColumns = nbColumns;
        this.board = board;
        this.currentScore = currentScore;
        this.listPiece = listPiece;
        this.listFilling = listFilling;
        this.cptMaxPieceOnBoard = cptMaxPieceOnBoard;
        this.listSwv = listSwv;
        this.nbMovePlayed = nbMovePlayed;
        saveStorage.addSave(this);
    }

    /**
     * Methode permettant d'afficher tous les parametres de la sauveguarde
     * @return la chaine de caractere que l'on veut afficher
     */
    @Override
    public String toString(){
        String str = "++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        str += "+ ID de la partie : " + getId() + "\n";
        str += "+ Numero de la partie : " + getSaveNumber() + "\n";
        str += "+ Nom du joueur  : " + getPlayerName() + "\n";
        str += "+ Nombre de lignes : " + getNbLines() + "\n";
        str += "+ Nombre de colonnes : " + getNbColumns() + "\n";
        str += "+ Score : " + getCurrentScore() + "\n";
        str += "+ Coups joues : " + getNbMovePlayed() + "\n";
        str += "++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        return str;
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public int getNbMovePlayed() { return nbMovePlayed; }

    public void setNbMovePlayed(int nbMovePlayed) { this.nbMovePlayed = nbMovePlayed; }

    public int getSaveNumber() { return saveNumber; }

    public void setSaveNumber(int saveNumber) { this.saveNumber = saveNumber; }

    public int getId() { return id; }

    public ArrayList<Save> getListSave() { return listSave; }

    public void setListSave(ArrayList<Save> listSave) { this.listSave = listSave; }

    public int getNbLines() { return nbLines; }

    public int getNbColumns() { return nbColumns; }

    public int getCurrentScore() { return currentScore; }

    public void setCurrentScore(int currentScore) { this.currentScore = currentScore; }

    public ArrayList<ArrayList<String>> getBoard() { return board; }

    public void setBoard(ArrayList<ArrayList<String>> board) { this.board = board; }

    public ArrayList<Piece> getListPiece() { return listPiece; }

    public void setListPiece(ArrayList<Piece> listPiece) { this.listPiece = listPiece; }

    public ArrayList<String> getListFilling() { return listFilling; }

    public String getPlayerName() { return playerName; }

    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public void setListFilling(ArrayList<String> listFilling) { this.listFilling = listFilling; }

    public int getCptMaxPieceOnBoard() { return cptMaxPieceOnBoard; }

    public void setCptMaxPieceOnBoard(int cptMaxPieceOnBoard) { this.cptMaxPieceOnBoard = cptMaxPieceOnBoard; }

    public void setId(int id) { this.id = id; }

}
