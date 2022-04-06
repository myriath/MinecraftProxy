package proxy.packets.clientbound.login;

import proxy.packets.Packet;
import proxy.structs.VarInt;

import java.util.LinkedList;

import static util.Util.*;

public class PacketLoginPluginRequest extends Packet {
    private final VarInt messageID;
    private final String channel;
    private final LinkedList<Byte> data;

    public PacketLoginPluginRequest(LinkedList<Byte> bytes) {
        super(0x04);
        messageID = new VarInt(bytes);
        channel = unpackString(bytes);
        data = new LinkedList<>();
        while (!bytes.isEmpty()) {
            data.add(bytes.remove());
        }
    }

    @Override
    public void packData(LinkedList<Byte> bytes) {
        messageID.pack(bytes);
        packString(channel, bytes);
        bytes.addAll(data);
    }

    @Override
    public String toString() {
        return "PacketLoginPluginRequest{" +
                "messageID=" + messageID +
                ", channel='" + channel + '\'' +
                ", data=" + data +
                '}';
    }
}
