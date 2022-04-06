package proxy.packets.serverbound.handshaking;

import proxy.Proxy;
import proxy.packets.Packet;
import proxy.structs.VarInt;

import java.util.LinkedList;

import static util.Util.*;

public class PacketHandshake extends Packet {
    private final VarInt protocolVersion;
    private final String ip;
    private final Short port;
    private final VarInt nextState;

    public PacketHandshake(LinkedList<Byte> bytes) {
        super(0x00);
        protocolVersion = new VarInt(bytes);
        ip = unpackString(bytes);
        port = unpackShort(bytes);
        nextState = new VarInt(bytes);
        if (nextState.getValue() == 2) {
            Proxy.setServerState(Proxy.State.LOGIN);
        } else {
            Proxy.setServerState(Proxy.State.STATUS);
        }
    }

    @Override
    public void packData(LinkedList<Byte> bytes) {
        protocolVersion.pack(bytes);
        packString(ip, bytes);
        packShort(port, bytes);
        nextState.pack(bytes);
    }

    @Override
    public String toString() {
        return "PacketHandshake{" +
                "protocolVersion=" + protocolVersion +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", nextState=" + nextState +
                '}';
    }
}
