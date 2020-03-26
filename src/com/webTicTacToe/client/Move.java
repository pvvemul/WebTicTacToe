package com.webTicTacToe.client;

public class Move {
    int row;
    int column;
    int value;

    public Move(int value) {
        this.value = value;
    }
    public Move(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Move(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    public Move negate() {
        this.value = -value;
        return this;
    }
}
