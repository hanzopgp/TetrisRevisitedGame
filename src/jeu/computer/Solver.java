package jeu.computer;

import jeu.model.Board;
import jeu.factory.Piece;
import jeu.model.Point;

import java.util.ArrayList;

public class Solver {

    private int cpt = 0;

    public Solver() {

    }

    public MoveAndScore solve(int depth, Board board, MoveAndScore bestScore){
        boolean firstMove = false;
        MoveAndScore best = new MoveAndScore(new Move(null, ""), 0);
        if(depth != 0){
            for(Piece piece : new ArrayList<>(board.getListPiece())){
                for(Move move : board.getValidMoves(piece)){
                    this.cpt ++;
                    if(bestScore.getTypeMove().equals("")){
                        firstMove = true;
                        bestScore.setTypeMove(move.getTypeMove());
                        bestScore.setPiece(piece);
                    }
                    Point pieceCentral = piece.getCentralPiece().clone();
                    board.makeMove(move);
                    int currentScore = board.evaluate();
                    if(currentScore > bestScore.getScore()){
                        bestScore.setScore(currentScore);
                    }
                    MoveAndScore nextMoveAndScore = solve(depth - 1, board, bestScore);
                    if(nextMoveAndScore.getScore() > bestScore.getScore()){
                        bestScore.setScore(nextMoveAndScore.getScore());
                    }
                    piece.setCentralPiece(pieceCentral);
                    //board.makeInverseMove(move);
                    if(firstMove) {
                        if (bestScore.getScore() > best.getScore()) {
                            best.setScore(bestScore.getScore());
                            best.setPiece(bestScore.getPiece());
                            best.setTypeMove(bestScore.getTypeMove());
                        }
                        //best = bestScore;
                        bestScore.setTypeMove("");
                    }
                }
                if(firstMove){
                    if (bestScore.getScore() > best.getScore()) {
                        best.setScore(bestScore.getScore());
                        best.setPiece(bestScore.getPiece());
                        best.setTypeMove(bestScore.getTypeMove());
                    }
                    bestScore.setPiece(null);
                }
            }
        }
        if(firstMove) {
            return best;
        }
        else
            return bestScore;
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

//    public MoveAndScore solve(int depth, Board board, MoveAndScore bestScore) {
//        boolean firstMove = false;
//        MoveAndScore best = null;
//        if (depth != 0) {
//            for (Piece piece : new ArrayList<>(board.getListPiece())) {
//                for (Move move : board.getValidMoves(piece)) {
//                    this.cpt++;
//                    if (bestScore.getTypeMove().equals("")) {
//                        firstMove = true;
//                        bestScore.setTypeMove(move.getTypeMove());
//                        bestScore.setPiece(piece);
//                    }
//                    board.makeMove(move);
//                    int currentScore = board.evaluate();
//                    if (currentScore > bestScore.getScore()) {
//                        bestScore.setScore(currentScore);
//                    }
//                    MoveAndScore nextMoveAndScore = solve(depth - 1, board, bestScore);
//                    if (nextMoveAndScore.getScore() > bestScore.getScore()) {
//                        bestScore.setScore(nextMoveAndScore.getScore());
//                    }
//                    //board.makeInverseMove(move);
//                    if (firstMove) {
//                        if (best != null) {
//                            if (bestScore.getScore() > best.getScore()) {
//                                best = new MoveAndScore(new Move(bestScore.getPiece(), bestScore.getTypeMove()), bestScore.getScore());
//                            }
//                        } else {
//                            best = new MoveAndScore(new Move(bestScore.getPiece(), bestScore.getTypeMove()), bestScore.getScore());
//                        }
//                        bestScore.setTypeMove("");
//                    }
//                }
//                if (firstMove) {
//                    if (bestScore.getScore() > best.getScore()) {
//                        best = new MoveAndScore(new Move(bestScore.getPiece(), bestScore.getTypeMove()), bestScore.getScore());
//                    }
//                    bestScore.setPiece(null);
//                }
//            }
//        }
//        return (firstMove ? best : bestScore);
//    }

//    public MoveAndScore solve(int depth, Board board){
//        Board boardCopy = board.clone();
//        ArrayList<PieceInterface> listPieceCopy = board.copyListPiece();
//        boardCopy.setListPiece(listPieceCopy);
//        MoveAndScore currentScore;
//        ArrayList<MoveAndScore> tabScore = new ArrayList<>();
//        for(PieceInterface piece : listPieceCopy) {
//            for (Move move : boardCopy.getValidMoves(piece)) {
//                this.cpt++;
//                Board boardCopyNext = boardCopy.clone();
//                boardCopyNext.setListPiece(boardCopy.copyListPiece());
//                boardCopyNext.makeMove(move);
//                if(depth == 0){
//                    currentScore = new MoveAndScore(move, boardCopyNext.evaluate() - boardCopy.evaluate());
//                }else{
//                    currentScore = solve(depth - 1, boardCopyNext);
//                    if(currentScore == null){
//                        currentScore = new MoveAndScore(move, boardCopyNext.evaluate() - boardCopy.evaluate());
//                    }else{
//                        currentScore = new MoveAndScore(move, boardCopy.evaluate());
//                    }
//                }
//                tabScore.add(currentScore);
//            }
//        }
//        return bestMoveAndScoreOf(tabScore);
//    }
//
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


