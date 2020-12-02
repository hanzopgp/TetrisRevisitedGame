package jeu.controller;

import jeu.Main;
import jeu.model.Board;
import jeu.factory.PieceInterface;
import jeu.save.SaveStorage;
import jeu.vue.MainWindow;

import java.util.ArrayList;
import java.util.Scanner;

import static jeu.controller.MainController.scannerIntLimit;

public class BoardController {

    private Board board;

    public BoardController(Board model, int gamemode) {
        this.board = model;
        if (gamemode == 2) {
            this.makePlateauView();
        } else {
            this.makePlateauTerminal();
        }
    }

    public static Board definePlateau(SaveStorage saveStorage, String playerName) {
        //Choix difficulte du jeu
        System.out.println("Choisissez une difficulte :\n(1) Chilling\n(2) Easy\n(3) Medium\n(4) Hard\n(5) Impossible");
        int difficulty = scannerIntLimit(new Scanner(System.in), 1, 5);
        System.out.println("---> Vous avez choisit " + difficulty + "  !");
        System.out.println("=============== DEBUT DE LA PARTIE ===============");
        switch (difficulty) {
            case 1:
                System.out.println("Votre meilleur score sauvegarde est de : " + saveStorage.getHighScoreByPlayerNameAndSize(playerName, 28, 28));
                return new Board(28, 28, saveStorage);
            case 2:
                System.out.println("Votre meilleur score sauvegarde est de : " + saveStorage.getHighScoreByPlayerNameAndSize(playerName, 25, 25));
                return new Board(25, 25, saveStorage);
            case 3:
                System.out.println("Votre meilleur score sauvegarde est de : " + saveStorage.getHighScoreByPlayerNameAndSize(playerName, 22, 22));
                return new Board(22, 22, saveStorage);
            case 4:
                System.out.println("Votre meilleur score sauvegarde est de : " + saveStorage.getHighScoreByPlayerNameAndSize(playerName, 19, 19));
                return new Board(19, 19, saveStorage);
            case 5:
                System.out.println("Votre meilleur score sauvegarde est de : " + saveStorage.getHighScoreByPlayerNameAndSize(playerName, 16, 16));
                return new Board(16, 16, saveStorage);
            default:
                throw new IllegalStateException("Unexpected value: " + difficulty);
        }
    }

    public void makePlateauView() {
        this.board.fillBoardHello(this.board.getNbLines());
        MainWindow window = new MainWindow("Tetris - v1.0", 1000, 1000, this.board);
        GUIController controle = new GUIController(window);
        window.addKeyListener(controle);
        window.addMouseListener(controle);
    }

    public void makePlateauTerminal() {
        //Affichage sauvegarde et choix
        if(this.board.getSaveStorage().getSize() > 0){
            System.out.println("Voici la liste des sauvegarde : ");
            for(int n = 0; n < this.board.getSaveStorage().getSize(); n++){
                System.out.println(this.board.getSaveStorage().getListSave().get(n));
            }
            System.out.println("Voulez-vous charger une partie :\n(1) OUI\n(2) NON");
            int choix = scannerIntLimit(new Scanner(System.in), 1, 2);
            System.out.println("---> Votre choix : " + (choix == 1 ? "OUI" : "NON"));
            if(choix == 1){
                System.out.println("Tapez l'ID de la sauvegarde");
                int nbSave = scannerIntLimit(new Scanner(System.in), 0, this.board.getSaveStorage().getSize() - 1);
                System.out.println("---> Votre choix : ");
                System.out.println(this.board.getSaveStorage().getListSave().get(nbSave));
                this.board = this.board.getSaveStorage().getSavedBoardByNumber(nbSave);
            }
            else{
                this.board.fillBoardRandomly();
            }
        }else{
            this.board.fillBoardRandomly();
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
            nbMove++;
            //Choix rotation translation new save
            System.out.println("Voulez-vous :\n(1) Roter une pièce\n(2) Translater une pièce\n(3) Finir la partie\n(4) Sauvegarder la partie");
            int actionChosen = scannerIntLimit(new Scanner(System.in), 1, 4);
            //Rotation
            if (actionChosen == 1) {
                //Choix piece que l'on veut manipuler
                PieceInterface currentPiece = chosePiece();
                //Choix sens rotation
                System.out.println("Voulez-vous :\n(1) Sens horaire\n(2) Sens anti-horaire");
                int direction = scannerIntLimit(new Scanner(System.in), 1, 2);
                String strDir = direction == 1 ? "Sens horaire" : "Sens anti-horaire";
                System.out.println("---> Vous avez choisit : " + strDir);
                boolean boolDir = direction == 1;
                this.board.rotatePiece(boolDir, currentPiece);
                System.out.flush();
                System.out.println(this.board);
                //Affichage score
                displayScore(nbMove, actionChosen);
            }
            //Translation
            else if (actionChosen == 2) {
                //Choix piece que l'on veut manipuler
                PieceInterface currentPiece = chosePiece();
                //Choix direction translation
                System.out.println("Choisissez un deplacement :\n(1) Deplacement vers le haut\n(2) Deplacement vers le bas\n(3) Deplacement vers la gauche\n(4) Deplacement vers la droite");
                int direction = scannerIntLimit(new Scanner(System.in), 1, 4);
                System.out.println("---> Vous avez choisit l'option numero : " + direction);
                this.board.translatePiece(direction, currentPiece);
                System.out.flush();
                System.out.println(this.board);
                //Affichage score
                displayScore(nbMove, actionChosen);
            }
            //Fin de partie
            else if(actionChosen == 3){
                isPlaying = false;
            }
            //sauvegarder la partie
            else if(actionChosen == 4){
                int nbSave = 0;
                this.board.saveBoard(nbSave , board.getSaveStorage());
            }
        }
        this.board.gameOver();
    }

    private void displayScore(int nbMove, int actionChosen){
        int score = board.evaluate();
        String type = board.defineAreaType(score);
        System.out.println("---> Aire la plus grand : " + score + " cases, de type : " + type);
        System.out.println("---> Coups restant : " + (Main.NB_MOVE_MAX_TERMINAL - nbMove));
        System.out.println("---> Vous avez choisit le choix : " + actionChosen);
        System.out.println();
    }

    private PieceInterface chosePiece() {
        System.out.println("Choisissez une pièce a manipuler : ");
        ArrayList<PieceInterface> listPiece = board.getListPiece();
        for (int i = 1; i < listPiece.size() + 1; i++) { //Affichage en decalage, simplification pour l'utilisateur
            System.out.print("(" + (i) + ") Piece : " + listPiece.get(i - 1).getFilling() + "  |  ");
            if (i % 3 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        int pieceChosen = scannerIntLimit(new Scanner(System.in), 1, listPiece.size()) - 1;
        return listPiece.get(pieceChosen);
    }
}
