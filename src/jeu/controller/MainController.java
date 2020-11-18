package jeu.controller;

import jeu.model.Board;

import java.util.Scanner;

public class MainController {

    public MainController(){ }

    public void defineGamemode(){
        System.out.flush();
        System.out.println("=============== CHOIX DES PARAMETRES ===============");
        System.out.println("Voulez-vous jouer:\n(1) Dans le terminal\n(2) Dans une interface graphique");
        int gamemode = scannerIntLimit(new Scanner(System.in), 1, 2);
        String str = gamemode == 1 ? "terminal" : "graphique";
        System.out.println("---> Vous avez choisit l'affichage : " + str);
        if(gamemode == 1){ System.out.println("---> Vous avez le droit a 50 coups avant la fin de la partie !"); } //Si affichage terminale la partie finit apres 50 coups joues
        this.constructGame(gamemode);
    }

    public void constructGame(int gamemode){
        Board board = BoardController.definePlateau();
        BoardController plateauController = new BoardController(board, gamemode);
    }

    // Verifie que l'input est un int
    public static int scannerInt(Scanner scanner, String errormsg){
        while (!scanner.hasNextInt()) {
            System.out.println(errormsg);
            scanner.nextLine();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    // Verifie que l'input est un int et qu'il est entre min et max
    public static int scannerIntLimit(Scanner scanner, int min, int max){
        int input;
        int cpt = 0;
        do{
            if(cpt>=1){ //affiche le msg d'erreur specifique a scannerIntLimit apres un cycle (donc une erreur de l'utilisateur)
                System.out.println("Entrez un nombre entre " + min + " et " + max + " !");
            }
            cpt++;
            input = scannerInt(scanner, "Veuillez entrer un nombre entier");
        }while(input < min || input > max);
        return input;
    }

}
