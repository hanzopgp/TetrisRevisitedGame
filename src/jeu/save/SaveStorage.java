package jeu.save;

import jeu.model.Board;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveStorage implements Serializable {

    private ArrayList<Save> listSave;

    public SaveStorage() {
        this.listSave = new ArrayList<>();
    }

    public void addSave(Save save) {
        this.listSave.add(save);
    }

    public boolean hasAlreadyBoard(ArrayList<ArrayList<String>> board){
        if(this.listSave.size() > 0){
            for(Save save : this.listSave){
                for(int i = 0; i < save.getBoard().size(); i++){
                    for(int j = 0; j < save.getBoard().get(i).size(); j++){
                        String element = save.getBoard().get(i).get(j);
                        String element2 = board.get(i).get(j);
                        if(!element.equals(element2)){
                            return false;
                        }
                    }
                }
            }
            return true;
        }else{
            return false;
        }
    }

    public void deleteAll() {
        listSave = new ArrayList<>();
        SaveWriteRead.deleteSave();
    }

    public Board getSavedBoardByNumber(int number) {
        Board savedBoard = null;
        for (Save save : this.listSave) {
            if (save.getId() == number) {
                savedBoard = new Board(save.getNbLines(), save.getNbColumns(), this);
                savedBoard.setId(save.getId());
                savedBoard.setBoard(save.getBoard());
                savedBoard.setListPiece(save.getListPiece());
                savedBoard.setListFilling(save.getListFilling());
                savedBoard.setCptMaxPieceOnBoard(save.getCptMaxPieceOnBoard());
                savedBoard.setListSwv(save.listSwv);
                savedBoard.setCurrentScore(save.getCurrentScore());
                savedBoard.setPlayerName(save.getPlayerName());
                savedBoard.setNbMove(save.getNbMovePlayed());
            }
        }
        return savedBoard;
    }

    public void deleteSaveByNumber(int number) {
        this.listSave.removeIf(save -> save.getId() == number);
    }

    public void printHighscoreAndSize(){
        int highscore = 0;
        int nbLines = 0;
        int nbColumns = 0;
        for(Save save : this.listSave){
            if(save.getCurrentScore() > highscore){
                highscore = save.getCurrentScore();
            }
        }
        for(Save save : this.listSave){
            if(save.getCurrentScore() == highscore){
                nbLines = save.getNbLines();
                nbColumns = save.getNbColumns();
            }
        }
        System.out.println("Le meilleur score enregistre est : " + highscore + " pour une grille de taille : (" + nbLines + "," + nbColumns + ")");
    }

    public int getHighScoreByPlayerNameAndSize(String playerName, int width, int height){
        int highscore = 0;
        for(Save save : this.listSave){
            if(save.getPlayerName().equals(playerName) && save.getNbColumns() == height && save.getNbLines() == width){
                if(save.getCurrentScore() > highscore){
                    highscore = save.getCurrentScore();
                }
            }
        }
        return highscore;
    }

    public int getSize(){
        return listSave.size();
    }

    public ArrayList<Save> getListSave(){
        return listSave;
    }

}
