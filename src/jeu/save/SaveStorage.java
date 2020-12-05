package jeu.save;

import jeu.model.Board;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe contenant la liste des sauveguardes
 */
public class SaveStorage implements Serializable {

    private ArrayList<Save> listSave;

    /**
     * Constructeur de l'object SaveStorage, contenant la liste des sauveguardes
     */
    public SaveStorage() {
        this.listSave = new ArrayList<>();
    }

    /**
     * Resume du role de la methode.
     * Commentaires detailles sur le role de la methode
     * @param save la sauveguarde a ajouter
     */
    public void addSave(Save save) {
        this.listSave.add(save);
    }

    /**
     * Verifie si la grille est deja presente dans une des sauveguardes de la liste
     * @param board la grille a tester
     * @return booleen true si la board est presente dans la liste, false sinon
     */
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

    /**
     * Reinitialise la liste de sauveguarde et supprimer le fichier
     */
    public void deleteAll() {
        listSave = new ArrayList<>();
        SaveWriteRead.deleteSave();
    }

    /**
     * Renvoie la sauveguarde contenant un ID recherche
     * @param number l'ID de la sauveguarde
     * @return savedBoard l'object Save contenant la partie que l'on recherche
     */
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

    /**
     * Supprime la partie de l'ID recherche
     * @param number l'ID de la partie qu'on veut supprimer
     */
    public void deleteSaveByNumber(int number) {
        this.listSave.removeIf(save -> save.getId() == number);
    }

    /**
     * Affiche le meilleur score contenu dans les sauveguardes ainsi que la taille de la grille associee
     */
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

    /**
     * Recupere le meilleur score du joueur actif suivant la taille de la grille
     * @param playerName le nom du joueur
     * @param nbLines le nombre de lignes de la grille
     * @param nbColumns le nombre de colonnes de la grille
     * @return highscore le meilleur score associe aux parametres
     */
    public int getHighScoreByPlayerNameAndSize(String playerName, int nbLines, int nbColumns){
        int highscore = 0;
        for(Save save : this.listSave){
            if(save.getPlayerName().equals(playerName) && save.getNbColumns() == nbColumns && save.getNbLines() == nbLines){
                if(save.getCurrentScore() > highscore){
                    highscore = save.getCurrentScore();
                }
            }
        }
        return highscore;
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public int getSize(){
        return listSave.size();
    }

    public ArrayList<Save> getListSave(){
        return listSave;
    }

}
