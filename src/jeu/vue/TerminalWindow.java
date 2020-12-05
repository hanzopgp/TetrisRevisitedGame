package jeu.vue;

import jeu.Main;
import jeu.computer.Solver;
import jeu.factory.Piece;
import jeu.model.Board;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * Classe representant l'affichage et les actions lorsque l'on joue dans le terminal
 */
public class TerminalWindow {

    private Board board;

    /**
     * Constructeur de l'object TerminalWindow
     * @param board plateau
     */
    public TerminalWindow(Board board){
        this.board = board;
    }

    /**
     * Methode principale de la classe, contenant la boucle de jeu
     */
    public void terminalUI(){
        if (this.board.getSaveStorage().getSize() > 0) {
            choseSaveOrRandomBoard();
        } else {
            if(this.board.isSolverTest()){
                this.board.fillBoardRandomly(Main.NB_PIECE_IF_SOLVER);
            }else{
                this.board.fillBoardRandomly();
            }
        }
        //Premier affichage
        System.out.flush();
        System.out.println();
        System.out.println(this.board);
        System.out.println();
        //Boucle de jeu
        int nbMove = 0;
        boolean isPlaying = true;
        while (nbMove < Main.NB_MOVE_MAX_TERMINAL && isPlaying) {
            //Choix rotation translation new save
            System.out.println("Voulez-vous :\n(1) Roter une piece\n(2) Translater une piece\n(3) Finir la partie\n(4) Sauvegarder la partie\n(5) Resoudre la partie");
            int actionChosen = Main.scannerIntLimit(new Scanner(System.in), 1, 5);
            switch(actionChosen){
                case 1 : //Rotation
                    this.userRotate(nbMove, actionChosen);
                    break;
                case 2 : //Translation
                    this.userTranslate(nbMove, actionChosen);
                    break;
                case 3 : //Fin de partie
                    isPlaying = false;
                    break;
                case 4 : //Sauveguarder
                    int nbSave = 0;
                    this.board.saveBoard(nbSave , board.getSaveStorage());
                    break;
                case 5 : //Resoudre la partie
                    if(this.board.isSolverTest()){
                        Solver solver = new Solver(this.board);
                        solver.execSolve(this.board.getNbMove());
                        isPlaying = false;
                    }else{
                        System.out.println("Solver indisponible en dehors du SolverTest !");
                    }
                    break;
            }
        }
        this.board.gameOver();
    }

    /**
     * Methode gerant les sauveguardes
     */
    private void choseSaveOrRandomBoard() {
        System.out.println("Voici la liste des sauvegarde : ");
        for(int n = 0; n < this.board.getSaveStorage().getSize(); n++){
            System.out.println(this.board.getSaveStorage().getListSave().get(n));
        }
        System.out.println("Voulez-vous charger une partie :\n(1) OUI\n(2) NON");
        int choix = Main.scannerIntLimit(new Scanner(System.in), 1, 2);
        System.out.println("---> Votre choix : " + (choix == 1 ? "OUI" : "NON"));
        if(choix == 1){
            System.out.println("Tapez l'ID de la sauvegarde");
            int nbSave = Main.scannerIntLimit(new Scanner(System.in), 0, this.board.getSaveStorage().getSize() - 1);
            System.out.println("---> Votre choix : ");
            System.out.println(this.board.getSaveStorage().getListSave().get(nbSave));
            this.board = this.board.getSaveStorage().getSavedBoardByNumber(nbSave);
        }
        else{
            if(this.board.isSolverTest()){
                this.board.fillBoardRandomly(Main.NB_PIECE_IF_SOLVER);
            }else{
                this.board.fillBoardRandomly();
            }
        }
    }

    /**
     * Methode permettant une meilleure lisibilite dans la methode principale de cette classe
     * @param nbMove nombre de move effectue
     * @param actionChosen action chosit
     */
    private void userTranslate(int nbMove, int actionChosen) {
        nbMove++;
        //Choix piece que l'on veut manipuler
        Piece currentPiece = chosePiece();
        //Choix direction translation
        System.out.println("Choisissez un deplacement :\n(1) Deplacement vers le haut\n(2) Deplacement vers le bas\n(3) Deplacement vers la gauche\n(4) Deplacement vers la droite");
        int direction = Main.scannerIntLimit(new Scanner(System.in), 1, 4);
        System.out.println("---> Vous avez choisit l'option numero : " + direction);
        this.board.translatePiece(direction, currentPiece);
        System.out.flush();
        System.out.println(this.board);
        //Affichage score
        displayScore(nbMove, actionChosen);
    }

    /**
     * Methode permettant une meilleure lisibilite dans la methode principale de cette classe
     * @param nbMove nombre de move effectue
     * @param actionChosen action chosit
     */
    private void userRotate(int nbMove, int actionChosen) {
        //Choix piece que l'on veut manipuler
        Piece currentPiece = chosePiece();
        //Choix sens rotation
        System.out.println("Voulez-vous :\n(1) Sens horaire\n(2) Sens anti-horaire");
        int direction = Main.scannerIntLimit(new Scanner(System.in), 1, 2);
        String strDir = direction == 1 ? "Sens horaire" : "Sens anti-horaire";
        System.out.println("---> Vous avez choisit : " + strDir);
        boolean boolDir = direction == 1;
        this.board.rotatePiece(boolDir, currentPiece);
        System.out.flush();
        System.out.println(this.board);
        //Affichage score
        displayScore(nbMove, actionChosen);
    }

    /**
     * Methode permettant d'afficher le score actuel
     * @param nbMove nombre de move
     * @param actionChosen action choisit
     */
    private void displayScore(int nbMove, int actionChosen){
        int score = board.evaluate();
        //String type = board.defineAreaType(score);
        //System.out.println("---> Aire la plus grand : " + score + " cases, de type : " + type);
        System.out.println("---> Aire la plus grand : " + score);
        System.out.println("---> Coups restant : " + (Main.NB_MOVE_MAX_TERMINAL - nbMove));
        System.out.println("---> Vous avez choisit le choix : " + actionChosen);
        System.out.println();
    }

    /**
     * Methode permettant au joueur de selectionner une piece
     * @return object Piece selectionne
     */
    private Piece chosePiece() {
        System.out.println("Choisissez une piece a manipuler : ");
        ArrayList<Piece> listPiece = board.getListPiece();
        for (int i = 1; i < listPiece.size() + 1; i++) { //Affichage en decalage, simplification pour l'utilisateur
            System.out.print("(" + (i) + ") Piece : " + listPiece.get(i - 1).getFilling() + "  |  ");
            if (i % 3 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        int pieceChosen = Main.scannerIntLimit(new Scanner(System.in), 1, listPiece.size()) - 1;
        return listPiece.get(pieceChosen);
    }

}
