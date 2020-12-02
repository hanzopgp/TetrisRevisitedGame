package jeu.computer;

import jeu.factory.PieceInterface;
import jeu.model.Board;

import java.util.ArrayList;

public class PseudoCode {


    //Faire une fonction getValidMoves()

    //boucle qui itere sur le nombre de coups restants
    //joue un coup avec une profondeur jusqu'a la fin

    //l'algo renvoie le meilleur move suivant une profondeur

//    public MoveAndScore solve(int depth, Board board, MoveAndScore bestScore){
//        boolean firstMove = false;
//        if(depth != 0){
//            for(PieceInterface piece : board.getListPiece()){
//                for(Move move : board.getValidMoves(piece)){
//                    if(bestScore.getTypeMove().equals("")){
//                        firstMove = true;
//                        bestScore.setTypeMove(move.getTypeMove());
//                        bestScore.setPiece(piece);
//                    }
//                    board.makeMove(move);
//                    int currentScore = board.getCurrentScore();
//                    if(currentScore > bestScore.getScore()){
//                        bestScore.setScore(currentScore);
//                    }
//                    MoveAndScore nextMoveAndScore = solve(depth - 1, board, bestScore);
//                    if(nextMoveAndScore.getScore() > bestScore.getScore()){
//                        bestScore.setScore(nextMoveAndScore.getScore());
//                    }
//                    board.makeInverseMove(move);
//                    if(firstMove){
//                        bestScore.setTypeMove("");
//                    }
//                }
//                if(firstMove){
//                    bestScore.setPiece(null);
//                }
//            }
//        }
//        return bestScore;
//    }



}
