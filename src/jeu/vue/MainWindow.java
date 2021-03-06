package jeu.vue;

import jeu.controller.SaveController;
import jeu.model.Board;
import jeu.Main;
import jeu.save.Save;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe representant la construction de la fenetre de l'application ainsi
 * que ses differentes actions lors d'un click sur un bouton
 */
public class MainWindow extends JFrame implements ActionListener{

    private String title;
    private int width;
    private int height;
    private Board board;
    private final Grid vue;
    private final VueScore  score;
    private final ListButton listeButton;

    /**
     * Constructeur de l'object MainWindow
     * @param title titre de l'application
     * @param width largeur de la fenetre
     * @param height hauteur de la fenetre
     * @param board plateau de l'application
     */
    public MainWindow(String title, int width, int height, Board board) {
        this.board = board;
        this.title = title;
        this.width = width;
        this.height = height;
        this.setSize(this.width, this.height);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //grille
        Grid vue = new Grid(board.getColonnes(), board.getLignes(), (this.width) - 100, (this.height) - 100, this.board);
        this.vue = vue;
        //TABLEAU SCORE A DROITE + BOUTONS + ALERTES
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(4, 1));
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightPanel.setBackground(Color.GRAY);
        //Titre du jeu
        JLabel gameTitle = new JLabel(this.getTitle(), SwingConstants.CENTER);
        gameTitle.setFont(gameTitle.getFont().deriveFont(20f));
        rightPanel.add(gameTitle);
        //score
        VueScore score = new VueScore();
        this.score = score;
        rightPanel.add(score);
        //Boutons
        ListButton listeButton = new ListButton(this);
        this.listeButton = listeButton;
        rightPanel.add(listeButton);
        //affichage
        this.getContentPane().add(vue, BorderLayout.CENTER);
        this.getContentPane().add(rightPanel, BorderLayout.EAST);
        this.setVisible(true);
        this.setFocusable(true);
    }

    /**
     * Methode exercant une action lors d'un click sur un bouton
     * @param e l'event a gerer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String nameButton = ((JButton)source).getText();
        if(nameButton.equals("Sauvegarder")){
            int nbSave = 0;
            this.board.saveBoard(nbSave, this.board.getSaveStorage());
        }else if(nameButton.equals("Charger une partie") && this.board.getSaveStorage() != null){
            this.board.setDemoMode(false);
            new SaveController(this);
        }else if(nameButton.equals("Nouvelle Configuration")){
            this.board.setDemoMode(false);
            this.board.setNbMove(0);
            this.board.setPieceFocused(null);
            this.board.clear();
            if(this.board.isSolverTest()){
                this.board.fillBoardRandomly(Main.NB_PIECE_IF_SOLVER);
            }else{
                this.board.fillBoardRandomly();
            }
            this.vue.update();
        }else if(nameButton.equals("Reset")&& !this.getBoard().isPieceAdded()){
            this.board.setNbMove(0);
            this.board.setOver(false);
            this.board.setDemoMode(true);
            this.board.setNewGame(true);
            this.board.setPieceFocused(null);
            this.board.clear();
            this.board.setPieceAdded(false);
            this.board.fillBoardHello(this.board.getLignes());
            this.vue.update();
        }else if(nameButton.equals("Finir la partie")){
            this.board.setOver(true);
            this.board.gameOver();
            this.score.getMovesRemaning().setText("Coups restants :\n "+Main.NB_MOVE_MAX_GUI  +"/" + Main.NB_MOVE_MAX_GUI);
        }else if(nameButton.equals("Quitter le jeu")){
            this.board.saveBoard(0, this.board.getSaveStorage());
            System.out.println("Nous avons automatiquement sauvegarde votre progression !");
            System.out.println("=============== FIN DU PROGRAMME ===============");
            System.exit(1);
        }else if(nameButton.equals("Supprimer les saves et quitter")){
            System.out.println("SAUVEGARDES SUPPRIMEES");
            System.out.println("=============== FIN DU PROGRAMME ===============");
            this.board.getSaveStorage().deleteAll();
            System.exit(1);
        }
        this.getVueScore().getMovesRemaning().setText("<html><p>Nombre de coups <br>restants : " + this.getBoard().getNbMove() + "/" + Main.NB_MOVE_MAX_GUI+"</p></html>");
        //this.getVueScore().getCurrentScore().setText("<html><p>Votre score est un <br>" + this.getBoard().defineAreaType(this.getBoard().evaluate()) + " de score : " + this.getBoard().evaluate()+"</p></html>");
        this.getVueScore().getCurrentScore().setText("<html><p>Votre score est de <br>" + this.getBoard().evaluate()+"</p></html>");
        requestFocusInWindow();
    }

    /**
     * Charge une partie sauveguarde
     * @param s object Save
     */
    public void loadGame(Save s){
        Board saveChoosen = this.board.getSaveStorage().getSavedBoardByNumber(s.getId());
        this.setBoard(saveChoosen);
        this.vue.setBoard(this.board);
        this.vue.update();
        this.getVueScore().getMovesRemaning().setText("<html><p>Nombre de coups <br>restants : " + this.getBoard().getNbMove() + "/" + Main.NB_MOVE_MAX_GUI+"</p></html>");
        //this.getVueScore().getCurrentScore().setText("<html><p>Votre score est un <br>" + this.getBoard().defineAreaType(this.getBoard().evaluate()) + " de score : " + this.getBoard().evaluate()+"</p></html>");
        this.getVueScore().getCurrentScore().setText("<html><p>Votre score est de <br>" + this.getBoard().evaluate()+"</p></html>");
    }

    /*==============================*/
    /*===== GETTER & SETTERS =======*/
    /*==============================*/

    public Grid getVue() {
        return this.vue;
    }

    public VueScore getVueScore() {
        return this.score;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ListButton getListeButton() {
        return listeButton;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}
