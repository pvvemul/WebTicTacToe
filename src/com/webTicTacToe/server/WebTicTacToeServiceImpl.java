package com.webTicTacToe.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.webTicTacToe.client.WebTicTacToeService;

public class WebTicTacToeServiceImpl extends RemoteServiceServlet implements WebTicTacToeService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}