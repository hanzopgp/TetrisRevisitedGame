package jeu.controller;

import jeu.Main;
import jeu.model.Board;
import jeu.save.SaveStorage;

import java.util.Scanner;

/**
 * Controleur principale de l'application
 */
public class MainController {

    /**
     * Constructeur de l'application
     */
    public MainController() {

    }

    /**
     * Methode permettant de definir le mode de jeu (terminal/graphique)
     * @param saveStorage - Systeme de sauvegarde a envoyer au futur board
     */
    public void defineGamemode(SaveStorage saveStorage) {
        System.out.flush();
        System.out.println("=============== CHOIX DES PARAMETRES ===============");
        System.out.println("Entrez votre pseudo : ");
        String playerName = Main.scannerString(new Scanner(System.in), "Entrez un pseudo non vide");
        System.out.println("---> Vous avez choisit le pseudonyme : " + playerName);
        System.out.println("Voulez-vous jouer:\n(1) Dans le terminal\n(2) Dans une interface graphique");
        int gamemode = Main.scannerIntLimit(new Scanner(System.in), 1, 2);
        String str = gamemode == 1 ? "terminal" : "graphique";
        System.out.println("---> Vous avez choisit l'affichage : " + str);
        if (gamemode == 1) {
            System.out.println("---> Vous avez le droit a " + Main.NB_MOVE_MAX_TERMINAL + " coups avant la fin de la partie !");
        } //Si affichage terminal la partie finit apres 50 coups joues
        this.constructGame(playerName, gamemode, saveStorage);
    }

    /**
     * Methode permettant d'initialiser le jeu apres avoir choisi le mode de jeu
     * @param playerName - Pseudo de l'utilisateur
     * @param gamemode - Mode de jeu choisi
     * @param saveStorage - Systeme de sauvegarde a envoyer au board
     */
    public void constructGame(String playerName, int gamemode, SaveStorage saveStorage) {
        Board board = BoardController.definePlateau(saveStorage, playerName);
        board.setPlayerName(playerName);
        new BoardController(board, gamemode);
    }

}
