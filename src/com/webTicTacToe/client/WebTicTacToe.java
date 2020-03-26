package com.webTicTacToe.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Label;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class WebTicTacToe implements EntryPoint {
    Canvas canvas;
    Context2d context;
    static final int canvasHeight = 300;
    static final int canvasWidth = 300;
    int playerSymbol;
    int cpuSymbol;
    int firstPlayer;
    Board board;
    boolean over;

     /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        canvas = Canvas.createIfSupported();
        if (canvas == null) {
            RootPanel.get().add(new Label("Sorry, your browser doesn't support the HTML5 Canvas element"));
            return;
        }
        canvas.setWidth(canvasWidth + "px");
        canvas.setCoordinateSpaceWidth(canvasWidth);

        canvas.setHeight(canvasHeight + "px");
        canvas.setCoordinateSpaceHeight(canvasHeight);
        context = canvas.getContext2d();
        welcomePage();
    }

    private void welcomePage() {
        board = new Board();
        final Label welcome = new Label("Welcome to Tic Tac Toe! To begin, choose X or O.");
        final Button X = new Button("X");
        final Button O = new Button("O");

        RootPanel.get("slot1").add(welcome);
        RootPanel.get("slot1").add(X);
        RootPanel.get("slot1").add(O);

        class StartHandler implements ClickHandler {
            int symbol;

            public StartHandler(int symbol) {
                this.symbol = symbol;
            }

            @Override
            public void onClick(ClickEvent event) {
                RootPanel.get("slot1").remove(welcome);
                RootPanel.get("slot1").remove(X);
                RootPanel.get("slot1").remove(O);
                coinToss();
                playerSymbol = symbol;
                cpuSymbol = Math.abs(1 - symbol);
            }
        }

        X.addClickHandler(new StartHandler(1));
        O.addClickHandler(new StartHandler(0));
    }

    private void coinToss() {
        final Label choose = new Label("I am going to flip a coin to see who goes first. Choose heads or tails");
        final Button heads = new Button("heads");
        final Button tails = new Button("tails");
        final Button begin = new Button("Begin");

        class TossHandler implements ClickHandler {

            int choice;

            public TossHandler(int choice) {
                this.choice = choice;
            }
            @Override
            public void onClick(ClickEvent event) {
                int toss = Random.nextInt(2);
                String text;
                if (toss == choice) {
                    text = "You won the coin toss, you go first!";
                    firstPlayer = playerSymbol;
                } else {
                    text = "I won the coin toss, so I'll go first!";
                    firstPlayer = cpuSymbol;
                }
                Label result = new Label(text);
                RootPanel.get("slot1").remove(choose);
                RootPanel.get("slot1").remove(heads);
                RootPanel.get("slot1").remove(tails);
                RootPanel.get("slot1").add(result);
                RootPanel.get("slot1").add(begin);
            }
        }

        RootPanel.get("slot1").add(choose);
        RootPanel.get("slot1").add(heads);
        RootPanel.get("slot1").add(tails);
        heads.addClickHandler(new TossHandler(1));
        tails.addClickHandler(new TossHandler(0));

        class BeginHandler implements ClickHandler {
            @Override
            public void onClick(ClickEvent event) {
                RootPanel.get("slot1").clear();
                drawGrid();
                setButtons();
                beginGame();
            }
        }
        begin.addClickHandler(new BeginHandler());
    }

    private void beginGame() {
        if (firstPlayer == cpuSymbol) {
            cpuTurn();
        }
    }

    private void cpuTurn() {
        Move cpuTurn = GetMove.minimax(board, cpuSymbol);
        board.applyMove(cpuTurn, cpuSymbol);
        if (cpuSymbol == 0) {
            drawO(cpuTurn.row + 1, cpuTurn.column + 1);
        } else {
            drawX(cpuTurn.row + 1, cpuTurn.column + 1);
        }
        Button b = new Button("Try Again?");
        b.addClickHandler(new RestartHandler());
        if (board.checkWinner(cpuSymbol)) {
            over = true;
            Label cpuWon = new Label("I won!");
            RootPanel.get("slot1").add(cpuWon);
            RootPanel.get("slot1").add(b);
        } else if (board.isFull) {
            over = true;
            Label draw = new Label("Draw!");
            RootPanel.get("slot1").add(draw);
            RootPanel.get("slot1").add(b);
        }
    }

    private void drawGrid() {
        RootPanel.get("slot1").add(canvas);
        context.beginPath();
        context.moveTo(100, 0);
        context.lineTo(100, 300);
        context.moveTo(200, 0);
        context.lineTo(200, 300);
        context.moveTo(0, 100);
        context.lineTo(300, 100);
        context.moveTo(0, 200);
        context.lineTo(300, 200);
        context.setLineWidth(6.0);
        context.fill();
        context.stroke();
        context.closePath();
    }

    public void drawX(int row, int column) {
        int x1;
        int y1;
        int x2;
        int y2;

        if (row == 1) {
            y1 = 15;
            y2 = 85;
        } else if (row == 2) {
            y1 = 115;
            y2 = 185;
        } else  {
            y1 = 215;
            y2 = 285;
        }

        if (column == 1) {
            x1 = 15;
            x2 = 85;
        } else if (column == 2) {
            x1 = 115;
            x2 = 185;
        } else {
            x1 = 215;
            x2 = 285;
        }
        context.beginPath();
        context.moveTo(x1, y1);
        context.lineTo(x2, y2);
        context.moveTo(x2, y1);
        context.lineTo(x1, y2);
        context.fill();
        context.stroke();
        context.closePath();
    }

    public void drawO(int row, int column) {
        int x;
        int y;

        if (row == 1) {
            y = 50;
        } else if (row == 2) {
            y = 150;
        } else {
            y = 250;
        }

        if (column == 1) {
            x = 50;
        } else if (column == 2) {
            x = 150;
        } else {
            x = 250;
        }

        context.beginPath();
        context.arc(x, y, 35, 0, 2 * Math.PI);
        context.stroke();
        context.closePath();
    }

    private class CustomHandler implements ClickHandler {

        int r;
        int c;

        public CustomHandler (int r, int c) {
            this.r = r;
            this.c = c;
        }

        public void onClick(ClickEvent event) {
            if (!board.checkEmptySpot(new Move(r - 1, c - 1)) || over) {
                return;
            }
            if (playerSymbol == 0) {
                drawO(r, c);
            } else {
                drawX(r, c);
            }
            Move playerMove = new Move(r - 1, c - 1);
            board.applyMove(playerMove, playerSymbol);
            Button b = new Button("Try Again?");
            b.addClickHandler(new RestartHandler());
            if (board.checkWinner(playerSymbol)) {
                over = true;
                Label playerWon = new Label("You Won!");
                RootPanel.get("slot1").add(playerWon);
                RootPanel.get("slot1").add(b);
            } else if (board.isFull) {
                over = true;
                Label draw = new Label("Draw!");
                RootPanel.get("slot1").add(draw);
                RootPanel.get("slot1").add(b);
            } else {
                cpuTurn();
            }
        }
    }

    class RestartHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent event) {
            Window.Location.reload();
        }
    }

    private void setButtons() {
        int top = canvas.getAbsoluteTop();
        int left = canvas.getAbsoluteLeft();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Image i = new Image();
                i.setPixelSize(100, 100);
                i.addClickHandler(new CustomHandler(r + 1, c + 1));
                i.setVisible(true);
                RootPanel.get("slot1").add(i, left + (c * 100), top + (r * 100));
            }
        }
    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}
