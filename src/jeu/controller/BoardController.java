package jeu.controller;

import jeu.Main;
import jeu.model.Board;
import jeu.save.SaveStorage;
import jeu.vue.MainWindow;
import jeu.vue.TerminalWindow;

import java.util.Scanner;

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
        int difficulty = Main.scannerIntLimit(new Scanner(System.in), 1, 5);
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
        TerminalWindow window = new TerminalWindow(this.board);
        window.terminalUI();
    }

}
