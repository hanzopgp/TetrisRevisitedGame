package jeu;

import jeu.controller.MainController;
import jeu.model.Board;
import jeu.save.Save;
import jeu.save.SaveStorage;
import jeu.save.SaveWriteRead;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*** TODOLIST
 * - Ameliorer interface graphique ( remplacer 'R' 'F' 'ENTRER' par un bouton nouvelle partie, nouvelle configuration, finir partie etc )
 * - Ajouter un menu simple ( Nouvelle partie, Charger partie, Quitter )
 * - Ajout commentaire pour la javadoc
 */

public class Main {

    public static final int NB_DIFF_PIECE = 5; //Suivant le nombre de classe piece existant
    public static int MAX_NB_PIECE_ON_BOARD = 10; //Attention le maximum est 26
    public static int NB_MOVE_MAX_TERMINAL = 50;
    public static int NB_MOVE_MAX_GUI = 50;
    public static int SOLVER_DEPTH = 10;
    public static int MIN_PIECE = 2;
    public static int MAX_PIECE = 3;
    public static String FILE_NAME = "save.txt";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //On recupere les sauveguardes si elles existent sinon on creer un nouveau saveStorage

        SaveStorage saveStorage = SaveWriteRead.readFile(Main.FILE_NAME);

        if (saveStorage == null) {
            System.out.println("Nous n'avons pas trouvé de sauveguarde sur votre ordinateur !");
            saveStorage = new SaveStorage();
        }
        else{
            System.out.println("Nous avons trouvé " + saveStorage.getSize() + " sauveguardes sur votre ordinateur ! ");
            ArrayList<Save> listSave = saveStorage.getListSave();
            for (Save save : listSave) {
                System.out.println(save);
            }
            saveStorage.printHighscoreAndSize();
        }
        MainController controller = new MainController();
        controller.defineGamemode(saveStorage);
    }

}

