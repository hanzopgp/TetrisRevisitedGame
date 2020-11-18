package jeu.controller;

import jeu.Main;
import jeu.model.PieceInterface;
import jeu.model.Board;
import jeu.vue.MainWindow;

import java.util.ArrayList;
import java.util.Scanner;

import static jeu.controller.MainController.scannerIntLimit;

public class BoardController {

    private Board board;
    private PieceInterface piece;
    private int gamemode;

    public BoardController(Board model, int gamemode) {
        this.board = model;
        this.gamemode = gamemode;
        if(this.gamemode == 2){
            this.makePlateauView();
        }else{
            this.makePlateauTerminal();
        }
    }

    public static Board definePlateau(){
        //Choix difficulte du jeu
        System.out.println("Choisissez une difficulte :\n(1) Chilling\n(2) Easy\n(3) Medium\n(4) Hard\n(5) Impossible");
        int difficulty = scannerIntLimit(new Scanner(System.in),  1, 5);
        System.out.println("---> Vous avez choisit " + difficulty + "  !");
        System.out.println("=============== DEBUT DE LA PARTIE ===============");
        switch(difficulty){
            case 1 :
                return new Board(28, 28);
            case 2 :
                return new Board(25, 25);
            case 3 :
                return new Board(22, 22);
            case 4 :
                return new Board(19, 19);
            case 5 :
                return new Board(16, 16);
            default :
                throw new IllegalStateException("Unexpected value: " + difficulty);
        }
    }

    public void makePlateauView(){
        MainWindow window = new MainWindow("Tetris - v1.0", 600, 800, this.board);
        GUIController controle = new GUIController(window);
        window.addKeyListener(controle);
        window.addMouseListener(controle);
    }

    public void makePlateauTerminal(){
        //On met les pieces dans le board
        for(int i = 0; i < Main.MAX_NB_PIECE_ON_BOARD; i++){
            this.board.addRandomPiece();
        }
        //Premier affichage
        System.out.flush();
        System.out.println();
        this.board.toString();
        System.out.println();
        //Boucle de jeu
        int nbMove = 0;
        while(nbMove < Main.NB_MOVE_MAX_TERMINAL) {
            nbMove++;
            //Choix piece que l'on veut manipuler
            System.out.println("Choisissez une pièce a manipuler : ");
            ArrayList<PieceInterface> listPiece = board.getListPiece();
            for(int i = 1; i < listPiece.size() + 1; i++){ //Affichage en decalage, simplification pour l'utilisateur
                System.out.print("("+(i)+") Piece : " + listPiece.get(i-1).getFilling() + "  |  ");
                if(i%3==0){System.out.println();}
            }
            System.out.println();
            int pieceChosen = scannerIntLimit(new Scanner(System.in),1, listPiece.size()) - 1;
            PieceInterface currentPiece = listPiece.get(pieceChosen);
            //Affichage score
            int score = board.evaluate();
            String type = board.defineAreaType(score);
            System.out.println("---> Aire la plus grand : " + score + " cases, de type : " + type);
            //Choix rotation ou translation
            System.out.println("Voulez-vous :\n(1) Roter une pièce\n(2) Translater une pièce");
            int actionChosen = scannerIntLimit(new Scanner(System.in),1, 2);
            String strAc = actionChosen == 1 ? "Rotation" : "Translation";
            System.out.println("---> Vous avez choisit : " + strAc);
            //Rotation
            if (actionChosen == 1) {
                //Choix sens rotation
                System.out.println("Voulez-vous :\n(1) Sens horaire\n(2) Sens anti-horaire");
                int direction = scannerIntLimit(new Scanner(System.in), 1, 2);
                String strDir = direction == 1 ? "Sens horaire" : "Sens anti-horaire";
                System.out.println("---> Vous avez choisit : " + strDir);
                boolean boolDir = direction == 1;
                this.board.rotatePiece(boolDir, currentPiece);
                System.out.flush();
                this.board.toString();
                System.out.println();
            }
            //Translation
            else if (actionChosen == 2) {
                //Choix direction translation
                System.out.println("Choisissez un deplacement :\n(1) Deplacement vers le haut\n(2) Deplacement vers le bas\n(3) Deplacement vers la gauche\n(4) Deplacement vers la droite");
                int direction = scannerIntLimit(new Scanner(System.in),  1, 4);
                System.out.println("---> Vous avez choisit l'option numero : " + direction);
                this.board.translatePiece(direction, currentPiece);
                System.out.flush();
                this.board.toString();
                System.out.println();
            }
        }
        this.board.gameOver();
    }
}
