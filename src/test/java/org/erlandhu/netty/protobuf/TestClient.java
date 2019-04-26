package org.erlandhu.netty.protobuf;

import org.erlandhu.netty.protobuf.client.ProtocolClient;
import org.erlandhu.netty.protobuf.cmd.Cmd.CmdMessage;
import org.erlandhu.netty.protobuf.cmd.Cmd.CmdTest;
import org.erlandhu.netty.protobuf.cmd.Cmd.CmdData;

public class TestClient {

    public static void main(String[] args) {
        ProtocolClient client = new ProtocolClient("127.0.0.1", 8888);
        send(client);
    }

    public static void send(ProtocolClient client){
       CmdData.Builder builder = CmdData.newBuilder();

        CmdTest.Builder ct = CmdTest.newBuilder();
        ct.setId(1);
        ct.setName("蔡徐坤");

        CmdMessage.Builder cmb = CmdMessage.newBuilder();
        cmb.setMessageId(1);
        cmb.setClientTime(1);
        cmb.setServerId(1);
        cmb.setServerTime(1);

        builder.setMessage(cmb);
        builder.setData(ct.build().toByteString());

        client.write(builder.build().getData());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
