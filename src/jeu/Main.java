package jeu;

import jeu.controller.MainController;
import jeu.save.Save;
import jeu.save.SaveStorage;
import jeu.save.SaveWriteRead;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static final int NB_DIFF_PIECE = 5; //Suivant le nombre de classe piece existant
    public static int MAX_NB_PIECE_ON_BOARD = 26; //Attention le maximum est 26
    public static int NB_MOVE_MAX_TERMINAL = 50;
    public static int NB_MOVE_MAX_GUI = 50;
    public static int SOLVER_DEPTH = 10;
    public static int MIN_PIECE = 2;
    public static int MAX_PIECE = 3;
    public static String FILE_NAME = "save.txt";

    public static void main(String[] args) throws IOException {
        //On recupere les sauvegardes si elles existent sinon on creer un nouveau saveStorage
        SaveStorage saveStorage = SaveWriteRead.readFile(Main.FILE_NAME);
        ArrayList<Save> listSave;
        if (saveStorage == null) {
            System.out.println("Nous n'avons pas trouvé de sauvegarde sur votre ordinateur !");
            saveStorage = new SaveStorage();
        }
        else{
            System.out.println("Nous avons trouvé " + saveStorage.getSize() + " sauvegardes sur votre ordinateur ! ");
            listSave = saveStorage.getListSave();
            for (Save save : listSave) {
                System.out.println(save);
            }
            saveStorage.printHighscoreAndSize();
        }
        MainController controller = new MainController();
        controller.defineGamemode(saveStorage);
    }

    // Verifie que l'input est un int
    public static int scannerInt(Scanner scanner, String errormsg) {
        while (!scanner.hasNextInt()) {
            System.out.println(errormsg);
            scanner.nextLine();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    public static String scannerString(Scanner scanner, String errormsg) {
        while (!scanner.hasNext()) {
            System.out.println(errormsg);
            scanner.nextLine();
        }
        return scanner.next();
    }

    // Verifie que l'input est un int et qu'il est entre min et max
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

