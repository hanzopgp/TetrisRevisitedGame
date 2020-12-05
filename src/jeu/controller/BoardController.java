package jeu.controller;

import jeu.Main;
import jeu.model.Board;
import jeu.save.SaveStorage;
import jeu.vue.MainWindow;
import jeu.vue.TerminalWindow;

import java.util.Scanner;

/**
 * Controller du board actuel
 */
public class BoardController {

    private final Board board;

    /**
     * Constructeur
     * @param model - Board a controler
     * @param gamemode - Mode de jeu choisi (graphique/terminal)
     */
    public BoardController(Board model, int gamemode) {
        this.board = model;
        if (gamemode == 2) {
            this.makePlateauView();
        } else {
            this.makePlateauTerminal();
        }
    }

    /**
     * Methode permettant d'initialiser le board ainsi que le systeme de sauvegarde
     * @param saveStorage - Instance qui gere les sauvegardes
     * @param playerName - Pseudo du joueur a sauvegarder
     * @return Board initialise
     */
    public static Board definePlateau(SaveStorage saveStorage, String playerName) {
        //Choix difficulte du jeu
        System.out.println("Choisissez une difficulte :\n(1) Chilling\n(2) Easy\n(3) Medium\n(4) Hard\n(5) Impossible\n(6) SolverTest");
        int difficulty = Main.scannerIntLimit(new Scanner(System.in), 1, 6);
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
            case 6 : //Utilisation d'une board 10 par 10 avec 8 pieces sans prendre en compte les rotations car algorithme fonctionnel mais non optimise
                System.out.println("Votre meilleur score sauvegarde est de : " + saveStorage.getHighScoreByPlayerNameAndSize(playerName, 8, 8));
                Board board = new Board(10, 10, saveStorage);
                board.setSolverTest(true);
                return board;
            default:
                throw new IllegalStateException("Unexpected value: " + difficulty);
        }
    }

    /**
     * Methode permettant de construire la vue graphique du board actuel
     */
    public void makePlateauView() {
        this.board.fillBoardHello(this.board.getNbLines());
        MainWindow window = new MainWindow("Tetris - v1.0", 1000, 1000, this.board);
        GUIController controle = new GUIController(window);
        window.addKeyListener(controle);
        window.addMouseListener(controle);
    }

    /**
     * Methode permettant de construire la vue en terminal du board actuel
     */
    public void makePlateauTerminal() {
        TerminalWindow window = new TerminalWindow(this.board);
        window.terminalUI();
    }

}
