package com.webTicTacToe.client;

import java.util.ArrayList;

public class Board {
    public static int[][] board;
    public static final int EMPTY = -1;
    public static final int O = 0;
    public static final int X = 1;
    public static boolean isFull;

    public Board() {
        this.board = new int[3][3];
        isFull = false;
        initBoard();
    }

    public void initBoard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    public static boolean checkWinner(int player) {
        // Check three rows
        for(int i = 0; i < 3; i++) {
            if(board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }

        // Check three columns
        for(int i = 0; i < 3; i++) {
            if(board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }

        // Check diagonals
        boolean flag = true;
        for(int i = 0; i < 3; i++) {
            flag = true;
            if(board[i][i] != player) {
                flag = false;
                break;
            }
        }
        if (flag) {
            return flag;
        }

        flag = true;
        for(int i = 0; i < 3; i++) {
            if(board[i][2 - i] != player) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private static boolean checkIfFull() {
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    isFull = false;
                    return false;
                }
            }
        }
        isFull = true;
        return true;
    }

    public static int eval(int player) {
        int other = O;
        if(player == O) { 
            other = X;
        }

        if(checkWinner(player)) {
            return 10;
        } else if(checkWinner(other)) {
            return -10;
        } else {
            return 0;
        }
    }

    public static void applyMove(Move move, int player) {
        board[move.row][move.column] = player;
        checkIfFull();
    }

    public static void undoMove(Move move) {
        board[move.row][move.column] = EMPTY;
    }

    public static boolean checkEmptySpot(Move move) {
        if(board[move.row][move.column] == EMPTY) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<Move> getMoves() {
        ArrayList<Move> result = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(board[i][j] == EMPTY) {
                    result.add(new Move(i,j));
                }
            }
        }
        return result;
    }
}
