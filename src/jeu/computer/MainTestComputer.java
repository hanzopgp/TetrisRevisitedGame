package jeu.computer;

import jeu.Main;
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
        Solver solver = new Solver();
        solver.boardGenerator(8, 8, 5);
        solver.execSolve(0);

    }

}
