package jeu.computer;

import jeu.Main;
import jeu.model.Board;
import jeu.factory.Piece;

import java.util.ArrayList;

/**
 * Classe principale de l'IA du jeu, contenant le solver
 */
public class Solver {

    private int cpt = 0;
    private Board board;

    /**
     * Constructeur principal
     * @param board l'object Board que l'on veut solve
     */
    public Solver(Board board) {
        this.board = board;
    }

    /**
     * Constructeur au cas ou on utilise le boardGenerator pour effectuer des tests
     */
    public Solver() {

    }

    /**
     * Creer un object Board afin de tester la methode solve
     * @param nbColumns nombre de colonnes de la grille
     * @param nblines nombre de lignes de la grille
     * @param nbPieces nombre de pieces sur la grille
     */
    public void boardGenerator(int nbColumns, int nblines, int nbPieces){
        Board board = new Board(nbColumns, nblines, null);
        board.fillBoardRandomly(nbPieces);
        System.out.println("BOARD DE BASE : ");
        System.out.println(board);
        this.board = board;
    }

    /**
     * Methode permettant d'executer et d'afficher les coups joues par la methode solve
     * @param nbMove le nombre de moves restant avant d'utiliser le solver
     */
    public void execSolve(int nbMove){
        System.out.println("------------------------ SOLVING ------------------------");
        int depth;
        if(this.board.isSolverTest()){
            depth = Main.SOLVER_DEPTH;
        }else{
            depth = Main.SOLVER_DEPTH_GENERAL;
        }
        for(int i = 0; i < Main.NB_MOVE_MAX_GUI; i++){
            System.out.println("------------------------ NOUVEAU COUP ------------------------");
            long start = System.currentTimeMillis();
            MoveAndScore move;
            if((Main.NB_MOVE_MAX_GUI - nbMove) >= depth){ //Ici on evite qu'en fin de partie, le solver joue en fonction de coups futurs qu'il ne pourra pas jouer car il ne restera plus de coups restants
                move = this.solve(depth, board, new MoveAndScore(new Move(null, "none"), 0));
            }else{ //Sinon on joue normalement
                move = this.solve((Main.NB_MOVE_MAX_GUI - nbMove), board, new MoveAndScore(new Move(null, "none"), 0));
            }
            computeMove(move.getTypeMove(), board, move.getPiece());
            long end = System.currentTimeMillis();
            nbMove++;
            System.out.println("PROFONDEUR DE RECHERCHE : " + depth);
            System.out.println("TEMPS MIT PAR LE SOLVER : " + (end-start)/1000 + "s");
            System.out.println("NOMBRE DE NOEUDS EXPLORES : " + this.cpt);
            System.out.println("NOMBRE DE COUPS RESTANTS : " + (Main.NB_MOVE_MAX_GUI - nbMove));
            //System.out.println("HASH PIECE : " + move.getPiece());
            System.out.println("PIECE SELECTIONNEE : " + move.getPiece().getFilling());
            System.out.println("TYPE DE MOVE : " + move.getTypeMove());
            System.out.println("SCORE APRES LE MOVE : " + board.evaluate());
            //System.out.println("TEST PIECE INE LIST PIECE : " + board.getListPiece().contains(move.getPiece()));
            System.out.println(board);
        }
    }

    /**
     * Algorithme permettant d'aller chercher les scores qui pourront etre atteint apres <depth> coups joues,
     * puis de les ramener coups par coups jusqu'a l'etat de la partie actuelle afin d'effectuer le meilleur
     * coup en profondeur 0 (present).
     * @param depth nombre de coups d'avances explores
     * @param board la grille que l'on veut resoudre
     * @param bestPieceMove l'object MoveAndScore qui contient le meilleur coup a joue
     * @return le meilleur coups a joue sur la grille suivant la profondeur de recherche
     */
    public MoveAndScore solve(int depth, Board board, MoveAndScore bestPieceMove){
        boolean isInitialMove = false; //Ce booleen permet de savoir si le coup est un coup que l'on peut jouer hors du solver
        MoveAndScore bestGeneralMove = new MoveAndScore(new Move(null, ""), 0); //On initialise best
        if(depth != 0){ //Si nous somme en profondeur superieur a 0
            ArrayList<Piece> listPiece = new ArrayList<>(board.getListPiece());
            for(Piece piece : listPiece){ //Pour chaque piece
                for(Move move : board.getValidMoves(piece)){ //Pour chaque coups valides de chaque piece
                    this.cpt++; //Compteur nombre de noeud
                    if(bestPieceMove.getTypeMove().equals("none")){ //Si nous avons la configuration de depart
                        isInitialMove = true; //Alors c'est un coup reel
                        bestPieceMove.setTypeMove(move.getTypeMove()); //On affecte le coup valide a bestPieceMove
                        bestPieceMove.setPiece(piece);
                    }
                    computeMove(move.getTypeMove(), board, move.getPiece());
                    int currentScore = board.evaluate(); //On recupere le score de la grille apres avoir effectuer ce move
                    if(currentScore > bestPieceMove.getScore()){ //Si ce score est superieur au score du coup maximum enregistre a cette profondeur
                        bestPieceMove.setScore(currentScore); //Alors le coup que l'on vient de jouer devient le coup maximum a cette profondeur
                    }
                    MoveAndScore nextMoveAndScore = solve(depth - 1, board, bestPieceMove); //On utilise la recursion ici pour aller voir si il y a des meilleurs coups a joue a d'autre profondeur
                    if(nextMoveAndScore.getScore() > bestPieceMove.getScore()){ //Si un des coups recupere a cette autre profondeur est superieur a notre meilleur coup a la profondeur actuelle
                        bestPieceMove.setScore(nextMoveAndScore.getScore()); //Alors le meilleur coups devient le coups de l'autre profondeur
                    }
                    computeInverseMove(board, move);
                    if(isInitialMove) { //Si nous somme dans le cas d'un coup reel
                        if (bestPieceMove.getScore() > bestGeneralMove.getScore()) { //Et que notre meilleur score enregistre pour la piece en cours est superieur au score du meilleur coup general
                            bestGeneralMove = new MoveAndScore(new Move(bestPieceMove.getPiece(), bestPieceMove.getTypeMove()), bestPieceMove.getScore()); //Alors le meilleur coup general devient le meilleur coup de la piece actuelle
                        }
                        bestPieceMove.setTypeMove("none"); //On reset le meilleur move pour la piece
                    }
                }
                if(isInitialMove){ //Si nous somme dans le cas d'une piece reel
                    if (bestPieceMove.getScore() > bestGeneralMove.getScore()) { //Et que notre meilleur score enregistre pour la piece est superieur au score general
                        bestGeneralMove = new MoveAndScore(new Move(bestPieceMove.getPiece(), bestPieceMove.getTypeMove()), bestPieceMove.getScore()); //Alors le meilleur move general devient ce meilleur move pour la piece
                    }
                    bestPieceMove.setPiece(null); //On reset le meilleur move pour la piece courante
                }
            }
        }
        return (isInitialMove ? bestGeneralMove : bestPieceMove); //Si on est dans le cas d'une piece initiale on return le meilleur coup general sinon on return le meilleur coup enregistre pour la piece
    }

    /**
     * Joue un move, factorisation de code
     * @param typeMove string type de move
     * @param board object Board
     * @param piece object Piece
     */
    private void computeMove(String typeMove, Board board, Piece piece) {
        if(Main.ROTATION_ACTIVATED_SOLVER){ //Si rotation active
            if(typeMove.equals("trueRotation")){
                board.rotatePiece(true, piece);
            }else if(typeMove.equals("falseRotation")){
                board.rotatePiece(false, piece);
            }else{
                board.translatePiece(board.tradDirection(typeMove), piece);
            }
        }else{
            board.translatePiece(board.tradDirection(typeMove), piece);
        }
    }

    /**
     * Permet de jouer un move inverse, utile pour la lisibilite dans la methode solve
     * @param board object Board
     * @param move object Move
     */
    private void computeInverseMove(Board board, Move move) {
        if(Main.ROTATION_ACTIVATED_SOLVER){ //On joue le coup inverse avec les rotations si activees
            if(move.getTypeMove().equals("trueRotation")){
                board.rotatePiece(false, move.getPiece());
            }else if(move.getTypeMove().equals("falseRotation")){
                board.rotatePiece(true, move.getPiece());
            }else{
                board.translatePiece(board.reverseDirection(board.tradDirection(move.getTypeMove())), move.getPiece());
            }
        }else{ //On joue le coup inverse seulement pour les translations sinon
            board.translatePiece(board.reverseDirection(board.tradDirection(move.getTypeMove())), move.getPiece());
        }
    }

    /*=============================*/
    /*===== GETTER & SETTER =======*/
    /*=============================*/

    public int getCpt() {
        return this.cpt;
    }

}

/*==============================*/
/*===== ESSAIS DE SOLVER =======*/
/*==============================*/

//    public MoveAndScore bestMoveAndScoreOf(ArrayList<MoveAndScore> tabScore){
//        MoveAndScore bestMove = tabScore.get(0);
//        int maxScore = 0;
//        for(MoveAndScore move : tabScore){
//            if(move.getScore() > maxScore){
//                maxScore = move.getScore();
//            }
//        }
//        for(MoveAndScore move : tabScore){
//            if(move.getScore() == maxScore){
//                bestMove = move;
//            }
//        }
//        return bestMove;
//    }
//


//        public MoveAndScore solve(int depth, Board virtualBoard){
//            System.out.println("depth : " + depth);
//            Board virtualBoardNext;
//            MoveAndScore currentScore; //MoveAndScore : Piece + Move + Score
//            ArrayList<MoveAndScore> tab = new ArrayList<>();
//            ArrayList<Move> virtualValidMoves = new ArrayList<>(virtualBoard.getValidMoves()); //valid moves : haut base gauche droite truerotate falserotate;
//            ArrayList<PieceInterface> virtualListPiece = new ArrayList<>(virtualBoard.getListPiece());
//
//            for(PieceInterface virtualPiece : virtualListPiece){
//                for(Move virtualMove : virtualValidMoves){
//                    virtualBoardNext = virtualBoard.clone();
//                    virtualBoardNext.makeMove(virtualMove);
//                    if(depth == 0){
//                        currentScore =  new MoveAndScore(virtualPiece, virtualMove.getTypeMove(), virtualBoardNext.evaluate() - virtualBoard.evaluate());
//                    }else{
//                        currentScore = solve(depth - 1, virtualBoardNext);
//                    }
//                    tab.add(currentScore);
//                }
//            }
//            return bestMoveAndScoreOf(tab);
//    }


//    public MoveAndScore solve(int depth, Board board, MoveAndScore bestScore){
//        boolean firstMove = false;
//        if(depth != 0){
//            for(PieceInterface piece : new ArrayList<>(board.getListPiece())){
//                for(Move move : board.getValidMoves(piece)){
//                    if(bestScore.getTypeMove().equals("")){
//                        System.out.println("hi");
//                        firstMove = true;
//                        bestScore.setTypeMove(move.getTypeMove());
//                        bestScore.setPiece(piece);
//                    }
//                    System.out.println(board);
//                    System.out.println(move.getTypeMove());
//                    board.makeMove(move);
//                    System.out.println(board);
//                    int currentScore = board.evaluate();
//                    System.out.println("currentScore : " + currentScore);
//                    if(currentScore > bestScore.getScore()){
//                        bestScore.setScore(currentScore);
//                    }
//                    MoveAndScore nextMoveAndScore = solve(depth - 1, board, bestScore);
//                    if(nextMoveAndScore.getScore() > bestScore.getScore()){
//                        bestScore.setScore(nextMoveAndScore.getScore());
//                    }
//                    board.makeInverseMove(move);
//                    return bestScore;
//                        //bestScore.setTypeMove("");
//
//                }
//                if(firstMove){
//                    bestScore.setPiece(null);
//                }
//            }
//        }
//        return bestScore;
//    }

//    public MoveAndScore solve(int depth, Board virtualBoard){
//        System.out.println("depth : " + depth);
//        Board virtualBoardNext;
//        MoveAndScore currentScore; //MoveAndScore : Piece + Move + Score
//        ArrayList<MoveAndScore> tab = new ArrayList<>();
//        ArrayList<Move> virtualValidMoves = new ArrayList<>(virtualBoard.getValidMoves()); //valid moves : haut base gauche droite truerotate falserotate;
//        ArrayList<PieceInterface> virtualListPiece = new ArrayList<>(virtualBoard.getListPiece());
//
//        for(PieceInterface virtualPiece : virtualListPiece){
//            for(Move virtualMove : virtualValidMoves){
//                virtualBoardNext = virtualBoard.clone();
//                virtualBoardNext.makeMove(virtualMove);
//                if(depth == 0){
//                    currentScore =  new MoveAndScore(virtualPiece, virtualMove.getTypeMove(), virtualBoardNext.evaluate() - virtualBoard.evaluate());
//                }else{
//                    currentScore = solve(depth - 1, virtualBoardNext);
//                }
//                tab.add(currentScore);
//            }
//        }
//
//        return bestMoveAndScoreOf(tab);
//    }

//    public MoveAndScore solve(int depth, Board virtualBoard){
//        System.out.println("depth : " + depth);
//        Board virtualBoardNext;
//        MoveAndScore currentScore; //MoveAndScore : Piece + Move + Score
//        ArrayList<MoveAndScore> tab = new ArrayList<>();
//        ArrayList<Move> virtualValidMoves = new ArrayList<>(virtualBoard.getValidMoves()); //valid moves : haut base gauche droite truerotate falserotate;
//        ArrayList<PieceInterface> virtualListPiece = new ArrayList<>(virtualBoard.getListPiece());
//
//        if(depth == 0){
//            return null;
//        }
//
//        for(PieceInterface virtualPiece : virtualListPiece){
//            for(Move virtualMove : virtualValidMoves){
//                virtualBoardNext = virtualBoard.getCopy();
//                virtualBoardNext.makeMove(virtualMove);
//                if(depth == 0){
//                    currentScore = new MoveAndScore(virtualPiece, virtualMove.getTypeMove(), virtualBoardNext.evaluate() - virtualBoard.evaluate());
//                }else{
//                    currentScore = solve(depth - 1, virtualBoardNext);
//                    if(currentScore == null){
//                        currentScore = new MoveAndScore(virtualPiece, virtualMove.getTypeMove(), virtualBoardNext.evaluate() - virtualBoard.evaluate());
//                    }else{
//                        currentScore = new MoveAndScore(virtualPiece, virtualMove.getTypeMove(), virtualBoard.evaluate());
//                    }
//                }
//                tab.add(currentScore);
//                System.out.println(tab.size());
//            }
//        }
//        return bestMoveAndScoreOf(tab);
//    }

//    public MoveAndScore bestMoveAndScoreOf(ArrayList<MoveAndScore> tab){
//        MoveAndScore bestMove = tab.get(0);
//        int maxScore = 0;
//        for(MoveAndScore move : tab){
//            if(move.getScore() > maxScore){
//                maxScore = move.getScore();
//            }
//        }
//        for(MoveAndScore move : tab){
//            if(move.getScore() == maxScore){
//                bestMove = move;
//            }
//        }
//        return bestMove;
//    }

//    public void solve() {
//        ArrayList<PieceInterface> listPiece = this.board.getListPiece();
//        ArrayList<MoveWithScore> listMoveWithScore = tryAllMoves(listPiece);
//        for (MoveWithScore mws : listMoveWithScore) {
//            System.out.println(mws.getScoreDiff());
//        }
//        MoveWithScore bestMoveWithScore = findBestMove(listMoveWithScore);
//        int bestDirection = bestMoveWithScore.getDirection();
//        int bestScoreDiff = bestMoveWithScore.getScoreDiff();
//        PieceInterface bestPiece = listPiece.get(bestMoveWithScore.getIndexPiece());
//        if (bestScoreDiff >= 0) {
//            this.board.translatePiece(bestDirection, bestPiece);
//        }
//    }
//
//    public ArrayList<MoveWithScore> tryAllMoves(ArrayList<PieceInterface> listPiece) {
//        ArrayList<MoveWithScore> listMoveWithScore = new ArrayList<MoveWithScore>();
//        for (int i = 0; i < listPiece.size(); i++) {
//            PieceInterface currentPiece = listPiece.get(i);
//            for (int j = 1; j < 5; j++) {
//                int oldScore = this.board.evaluate();
//                boolean test = this.board.translatePiece(j, currentPiece);
//                int newScore = this.board.evaluate();
//                if (test) {
//                    this.board.translatePiece(this.board.reverseDirection(j), currentPiece);
//                }
//                int scoreDiff = newScore - oldScore;
//                MoveWithScore moveWithScore = new MoveWithScore(i, j, scoreDiff);
//                listMoveWithScore.add(moveWithScore);
//            }
//        }
//        return listMoveWithScore;
//    }
//
//    public MoveWithScore findBestMove(ArrayList<MoveWithScore> listMoveWithScore) {
//        int maxScoreDiff = 0;
//        MoveWithScore bestMoveWithScore = null;
//        for (MoveWithScore mws : listMoveWithScore) {
//            if (mws.getScoreDiff() > maxScoreDiff) {
//                maxScoreDiff = mws.getScoreDiff();
//            }
//        }
//        for (MoveWithScore mws : listMoveWithScore) {
//            if (mws.getScoreDiff() == maxScoreDiff) {
//                bestMoveWithScore = mws;
//            }
//        }
//        return bestMoveWithScore;
//    }


