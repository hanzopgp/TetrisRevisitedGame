package jeu.save;

import jeu.factory.PieceInterface;
import jeu.model.ScoreWithVal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Save implements Serializable {

    private int id;

    private int saveNumber;
    static AtomicInteger nextSaveNumber = new AtomicInteger();

    private String playerName;

    private ArrayList<Save> listSave = new ArrayList<>();

    private final int nbLines;
    private final int nbColumns;
    private ArrayList<ArrayList<String>> board;
    private ArrayList<PieceInterface> listPiece;
    private ArrayList<String> listFilling;
    private int cptMaxPieceOnBoard;
    ArrayList<ScoreWithVal> listSwv;
    private int currentScore;

    public Save(SaveStorage saveStorage, String playerName, int id, int nbLines, int nbColumns, ArrayList<ArrayList<String>> board, int currentScore, ArrayList<PieceInterface> listPiece, ArrayList<String> listFilling, int cptMaxPieceOnBoard, ArrayList<ScoreWithVal> listSwv) {

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

        saveStorage.addSave(this);
    }

    @Override
    public String toString(){
        String str = "++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        str += "+ ID de la partie : " + getId() + "\n";
        str += "+ Numero de la partie : " + getSaveNumber() + "\n";
        str += "+ Nom du joueur  : " + getPlayerName() + "\n";
        str += "+ Nombre de lignes : " + getNbLines() + "\n";
        str += "+ Nombre de colonnes : " + getNbColumns() + "\n";
        str += "+ Score : " + getCurrentScore() + "\n";
        str += "++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        return str;
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public int getSaveNumber() { return saveNumber; }

    public void setSaveNumber(int saveNumber) { this.saveNumber = saveNumber; }

    public ArrayList<ScoreWithVal> getListSwv() { return listSwv; }

    public void setListSwv(ArrayList<ScoreWithVal> listSwv) { this.listSwv = listSwv; }

    public int getId() {
        return id;
    }

    public ArrayList<Save> getListSave() {
        return listSave;
    }

    public void setListSave(ArrayList<Save> listSave) {
        this.listSave = listSave;
    }

    public int getNbLines() {
        return nbLines;
    }

    public int getNbColumns() {
        return nbColumns;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public ArrayList<ArrayList<String>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<String>> board) {
        this.board = board;
    }

    public ArrayList<PieceInterface> getListPiece() {
        return listPiece;
    }

    public void setListPiece(ArrayList<PieceInterface> listPiece) {
        this.listPiece = listPiece;
    }

    public ArrayList<String> getListFilling() {
        return listFilling;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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

    public void setId(int id) {
        this.id = id;
    }

}
