package jeu.computer;

import jeu.model.Board;
import jeu.factory.PieceInterface;

import java.util.ArrayList;

public class Solver {

    public Solver() {

    }

    public MoveAndScore solve(int depth, Board virtualBoard){
        Board virtualBoardNext;
        MoveAndScore currentScore; //MoveAndScore : Piece + Move + Score
        ArrayList<MoveAndScore> tab = new ArrayList<>();
        ArrayList<Move> validMoves = virtualBoard.getValidMoves(); //valid moves : haut base gauche droite truerotate falserotate;
        ArrayList<PieceInterface> listPiece = virtualBoard.getListPiece();

        if(validMoves.isEmpty()){
            return null;
        }

        for(PieceInterface piece : listPiece){
            for(Move move : validMoves){
                virtualBoardNext = virtualBoard.getCopy();
                System.out.println("base : " + virtualBoard);
                System.out.println("next : " + virtualBoardNext);
                virtualBoardNext.makeMove(move);
                if(depth == 0){
                    currentScore = new MoveAndScore(piece, move.getTypeMove(), virtualBoardNext.evaluate() - virtualBoard.evaluate());
                }else{
                    currentScore = solve(depth - 1, virtualBoardNext);
                    if(currentScore == null){
                        currentScore = new MoveAndScore(piece, move.getTypeMove(), virtualBoardNext.evaluate() - virtualBoard.evaluate());
                    }else{
                        currentScore = new MoveAndScore(piece, move.getTypeMove(), virtualBoard.evaluate());
                    }
                }
                tab.add(currentScore);
            }
        }
        return bestMoveAndScoreOf(tab);
    }

    public MoveAndScore bestMoveAndScoreOf(ArrayList<MoveAndScore> tab){
        MoveAndScore bestMove = tab.get(0);
        int maxScore = 0;
        for(MoveAndScore move : tab){
            if(move.getScore() > maxScore){
                maxScore = move.getScore();
            }
        }
        for(MoveAndScore move : tab){
            if(move.getScore() == maxScore){
                bestMove = move;
            }
        }
        return bestMove;
    }

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

}
