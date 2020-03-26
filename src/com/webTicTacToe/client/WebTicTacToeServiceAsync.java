package com.webTicTacToe.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WebTicTacToeServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
}
