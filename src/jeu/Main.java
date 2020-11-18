package jeu;

import jeu.controller.MainController;

/*** TODOLIST
 * - Ameliorer interface graphique
 * - Ajout classe Game avec attributs playerName, score, date etc etc + syteme de sauveguarde
 * - Ajout commentaire pour la javadoc
 * - Ajout piece rectangle et carre
 * - move ++ en methode dans le controller ?
 */

public class Main {

	public static final int NB_DIFF_PIECE = 3; //Suivant le nombre de classe piece existant
	public static int MAX_NB_PIECE_ON_BOARD = 6; //Attention le maximum est 26
	public static int NB_MOVE_MAX_TERMINAL = 100;
	public static int NB_MOVE_MAX_GUI = 50;

	public static void main (String[] args) {
		MainController controller = new MainController();
		controller.defineGamemode();
	}
}

