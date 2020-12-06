package jeu;

import jeu.controller.MainController;
import jeu.save.Save;
import jeu.save.SaveStorage;
import jeu.save.SaveWriteRead;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe principale de l'application
 */
public class Main {

    public static final int NB_DIFF_PIECE = 5; //Suivant le nombre de classe piece existant
    public static int MAX_NB_PIECE_ON_BOARD = 15; //Attention le maximum est 26
    public static int NB_MOVE_MAX_TERMINAL = 50;
    public static int NB_MOVE_MAX_GUI = 50;
    public static int MIN_PIECE = 2; //taille min d'une piece aleatoire
    public static int MAX_PIECE = 3; //taille max d'une piece aleatoire
    public static String FILE_NAME = "save.txt";
    public static int SOLVER_DEPTH = 3; //Profondeur 3 si utilise dans une grille et un nombre de piece inferieur au jeu normal
    public static int SOLVER_DEPTH_GENERAL = 2; //Profondeur fonctionnement peu importe le mode de jeu
    public static boolean ROTATION_ACTIVATED_SOLVER = false; //Prise en compte des rotations dans le solver
    public static int NB_PIECE_IF_SOLVER = 6;

    /**
     * Main du programme
     * @param args parametre du main
     */
    public static void main(String[] args) throws IOException {
        //On recupere les sauvegardes si elles existent sinon on creer un nouveau saveStorage
        SaveStorage saveStorage = SaveWriteRead.readFile(Main.FILE_NAME);
        ArrayList<Save> listSave;
        if (saveStorage == null) {
            System.out.println("Nous n'avons pas trouve de sauvegarde sur votre ordinateur !");
            saveStorage = new SaveStorage();
        }
        else{
            System.out.println("Nous avons trouve " + saveStorage.getSize() + " sauvegardes sur votre ordinateur ! ");
            listSave = saveStorage.getListSave();
            for (Save save : listSave) {
                System.out.println(save);
            }
            saveStorage.printHighscoreAndSize();
        }
        MainController controller = new MainController();
        controller.defineGamemode(saveStorage);
    }

    /**
     * Verifie que l'input est un int
     * @param scanner - Scanner a utiliser
     * @param errormsg - Message d'erreur a envoyer
     * @return Valeur saisie
     */
    public static int scannerInt(Scanner scanner, String errormsg) {
        while (!scanner.hasNextInt()) {
            System.out.println(errormsg);
            scanner.nextLine();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    /**
     * Verifie que l'input est un string
     * @param scanner - Scanner a utiliser
     * @param errormsg - Message d'erreur a envoyer
     * @return Valeur saisie
     */
    public static String scannerString(Scanner scanner, String errormsg) {
        while (!scanner.hasNext()) {
            System.out.println(errormsg);
            scanner.nextLine();
        }
        return scanner.next();
    }

    /**
     * Verifie que l'input est un int et qu'il est entre min et max
     * @param scanner - Scanner a utiliser
     * @param min - Valeur minimale
     * @param max - Valeur maximale
     * @return Valeur saisie
     */
    public static int scannerIntLimit(Scanner scanner, int min, int max) {
        int input;
        int cpt = 0;
        do {
            if (cpt >= 1) { //affiche le msg d'erreur specifique a scannerIntLimit apres un cycle (donc une erreur de l'utilisateur)
                System.out.println("Entrez un nombre entre " + min + " et " + max + " !");
            }
            cpt++;
            input = scannerInt(scanner, "Veuillez entrer un nombre entier");
        } while (input < min || input > max);
        return input;
    }

}

