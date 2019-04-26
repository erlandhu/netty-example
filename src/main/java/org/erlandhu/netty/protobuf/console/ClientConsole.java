package org.erlandhu.netty.protobuf.console;

import org.erlandhu.netty.protobuf.client.ProtocolClient;

public class ClientConsole {
    private ProtocolClient clientSocket;

    public void onDisconnect(){
        clientSocket.reconnect();
    }

    public ClientConsole(ProtocolClient clientSocket) {
        this.clientSocket = clientSocket;
    }

    public ProtocolClient getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(ProtocolClient clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void onConnect() {
        clientSocket.connectSuccess();
    }
}
