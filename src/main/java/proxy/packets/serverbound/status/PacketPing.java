package proxy.packets.serverbound.status;

import proxy.packets.Packet;

import java.util.LinkedList;

import static util.Util.*;

public class PacketPing extends Packet {
    private final Long payload;

    public PacketPing(LinkedList<Byte> bytes) {
        super(0x01);
        payload = unpackLong(bytes);
    }

    @Override
    public void packData(LinkedList<Byte> bytes) {
        packLong(payload, bytes);
    }

    @Override
    public String toString() {
        return "PacketPing{" +
                "payload=" + payload +
                '}';
    }
}
