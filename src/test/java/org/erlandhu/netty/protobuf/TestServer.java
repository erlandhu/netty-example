package org.erlandhu.netty.protobuf;

import org.erlandhu.netty.protobuf.server.ProtocolServer;

public class TestServer {
    public static void main(String[] args) {
        ProtocolServer server = new ProtocolServer();

        server.start(8888);
    }
}
