package proxy.packets.serverbound.login;

import proxy.packets.Packet;

import java.util.LinkedList;

import static util.Util.*;

public class PacketLoginStart extends Packet {
    private final String username;

    public PacketLoginStart(LinkedList<Byte> bytes) {
        super(0x00);
        username = unpackString(bytes);
    }

    @Override
    public void packData(LinkedList<Byte> bytes) {
        packString("Bob", bytes);
    }

    @Override
    public String toString() {
        return "PacketLoginStart{" +
                "username='" + username + '\'' +
                '}';
    }
}
