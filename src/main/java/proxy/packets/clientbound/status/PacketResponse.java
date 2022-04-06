package proxy.packets.clientbound.status;

import proxy.packets.Packet;

import java.util.LinkedList;

import static util.Util.*;

public class PacketResponse extends Packet {
    private final String jsonResponse;

    public PacketResponse(LinkedList<Byte> bytes) {
        super(0x00);
        jsonResponse = unpackString(bytes);
    }

    @Override
    public void packData(LinkedList<Byte> bytes) {
        packString(jsonResponse, bytes);
    }

    @Override
    public String toString() {
        return "PacketResponse{" +
                "jsonResponse='" + jsonResponse + '\'' +
                '}';
    }
}
