package com.webTicTacToe.client;

import java.util.List;

public class GetMove {
    public static Move minimax(Board board, int playerTurn) {
        Move bestMove = new Move(-1000);
        List<Move> moves = board.getMoves();
        if(moves.isEmpty() || board.checkWinner(playerTurn)) {
            return new Move(board.eval(playerTurn));
        }

        for(Move m: moves) {
            board.applyMove(m, playerTurn);
            Move value = minimax(board, Math.abs(playerTurn - 1)).negate();
            board.undoMove(m);
            if(value.value > bestMove.value) {
                bestMove = new Move(m.row, m.column, value.value);
            }
        }
        return bestMove;
    }
}
