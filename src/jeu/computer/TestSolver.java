package jeu.computer;

import jeu.model.Board;

public class TestSolver {

    public static void main(String[] args){


        Board board = new Board(10, 10, null);
        board.fillBoardRandomly(8);

        for(int i = 0; i < 20; i++){
            Solver solver = new Solver();
            MoveAndScore move = solver.solve(2, board, new MoveAndScore(new Move(null, ""), 0));
            board.makeMove(move.getMove());
            System.out.println("cpt : " + solver.getCpt());
            System.out.println(move.getPiece());
            if(!(move.getPiece() == null)){
                System.out.println(move.getPiece().getFilling());
                System.out.println(move.getTypeMove());
                System.out.println(board);
                System.out.println(board.evaluate());
            }

        }

    }

}
