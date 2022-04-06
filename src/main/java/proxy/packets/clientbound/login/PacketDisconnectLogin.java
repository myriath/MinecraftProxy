package proxy.packets.clientbound.login;

import proxy.packets.Packet;

import java.util.LinkedList;

import static util.Util.*;

public class PacketDisconnectLogin extends Packet {
    private final String reason;

    public PacketDisconnectLogin(LinkedList<Byte> bytes) {
        super(0x00);
        reason = unpackString(bytes);
    }

    @Override
    public void packData(LinkedList<Byte> bytes) {
        packString(reason, bytes);
    }

    @Override
    public String toString() {
        return "PacketDisconnectLogin{" +
                "reason='" + reason + '\'' +
                '}';
    }
}
