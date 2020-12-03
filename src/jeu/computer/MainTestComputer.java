package jeu.computer;

import jeu.factory.Piece;
import jeu.model.Board;

public class MainTestComputer {

    /**
     * Main de la classe permettant de tester les methodes lies a l'implementation de l'IA Tetris
     * @param args parametre du main
     */
    public static void main(String[] args){

//        System.out.println("TEST VALID MOVES");
//        Board board = new Board(10, 10, null);
//        board.fillBoardRandomly(2);
//        System.out.println("BOARD DE BASE : ");
//        System.out.println(board);
//        for(Piece p : board.getListPiece()){
//            System.out.println("PIECE : " + p.getFilling() + ", nbvalidmoves : " + board.getValidMoves(p).size());
//        }

        System.out.println("TEST SOLVER");
        Board board = new Board(20, 20, null);
        board.fillBoardRandomly(10);
        System.out.println("BOARD DE BASE : ");
        System.out.println(board);

        System.out.println("------------SOLVING------------");
        Solver solver = new Solver();

        for(int i = 0; i < 20; i++){
            System.out.println("------------NOUVEAU COUP------------");
//            Board tmpBoard = new Board(20, 20, null);
//            tmpBoard.setListPiece(board.copyListPiece());
//            tmpBoard.setBoard(board.copyBoard());
            MoveAndScore move = solver.solve(2, board, new MoveAndScore(new Move(null, ""), 0));
            System.out.println(board);
            System.out.println("COMPTEUR DE NOEUD : " + solver.getCpt());
            System.out.println("HASH PIECE : " + move.getPiece());
            System.out.println("PIECE : " + move.getPiece().getFilling());
            System.out.println("TYPE DE MOVE : " + move.getTypeMove());
            System.out.println("SCORE : " + board.evaluate());
            System.out.println("TEST PIECE INE LIST PIECE : " + board.getListPiece().contains(move.getPiece()));
            System.out.println("TEST MOVE IN VALID MOVE : " + board.getValidMoves(move.getPiece()).contains(move.getMove()));
            board.makeMove(move.getMove());
        }

    }

}
