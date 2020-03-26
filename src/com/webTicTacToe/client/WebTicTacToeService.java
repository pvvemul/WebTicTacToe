package com.webTicTacToe.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("WebTicTacToeService")
public interface WebTicTacToeService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use WebTicTacToeService.App.getInstance() to access static instance of WebTicTacToeServiceAsync
     */
    public static class App {
        private static WebTicTacToeServiceAsync ourInstance = GWT.create(WebTicTacToeService.class);

        public static synchronized WebTicTacToeServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
