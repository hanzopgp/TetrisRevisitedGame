package jeu.computer;

import jeu.model.Board;
import jeu.model.PieceInterface;

import java.util.ArrayList;
import java.util.Random;

public class Solver {

    private int depth;
    private Board board;
    private int baseScore;

    public Solver(int depth, Board board, int baseScore){
        this.depth = depth;
        this.board = board;
        this.baseScore = baseScore;
    }

    public void solve(){
        ArrayList<PieceInterface> listPiece = this.board.getListPiece();
        ArrayList<MoveWithScore> listMoveWithScore = tryAllMoves(listPiece);
        for(MoveWithScore mws : listMoveWithScore){
            System.out.println(mws.getScoreDiff());
        }
        MoveWithScore bestMoveWithScore = findBestMove(listMoveWithScore);
        int bestDirection = bestMoveWithScore.getDirection();
        int bestScoreDiff = bestMoveWithScore.getScoreDiff();
        PieceInterface bestPiece = listPiece.get(bestMoveWithScore.getIndexPiece());
        if(bestScoreDiff >= 0){
            this.board.translatePiece(bestDirection, bestPiece);
        }
    }

    public ArrayList<MoveWithScore> tryAllMoves(ArrayList<PieceInterface> listPiece){
        ArrayList<MoveWithScore> listMoveWithScore = new ArrayList<MoveWithScore>();
        for(int i = 0; i < listPiece.size(); i++){
            PieceInterface currentPiece = listPiece.get(i);
            for(int j = 1; j < 5; j++){
                int oldScore = this.board.evaluate();
                boolean test = this.board.translatePiece(j, currentPiece);
                int newScore = this.board.evaluate();
                if(test){
                    this.board.translatePiece(this.board.reverseDirection(j), currentPiece);
                }
                int scoreDiff = newScore - oldScore;
                MoveWithScore moveWithScore = new MoveWithScore(i, j, scoreDiff);
                listMoveWithScore.add(moveWithScore);
            }
        }
        return listMoveWithScore;
    }

    public MoveWithScore findBestMove(ArrayList<MoveWithScore> listMoveWithScore){
        int maxScoreDiff = 0;
        MoveWithScore bestMoveWithScore = null;
        for(MoveWithScore mws : listMoveWithScore){
            if(mws.getScoreDiff() > maxScoreDiff){
                maxScoreDiff = mws.getScoreDiff();
            }
        }
        for(MoveWithScore mws : listMoveWithScore){
            if(mws.getScoreDiff() == maxScoreDiff){
                bestMoveWithScore = mws;
            }
        }
        return bestMoveWithScore;
    }

}
