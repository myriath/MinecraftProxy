package proxy.packets.clientbound.login;

import proxy.Proxy;
import proxy.packets.Packet;
import proxy.structs.UUID;

import java.util.LinkedList;

import static util.Util.*;

public class PacketLoginSuccess extends Packet {
    private final UUID uuid;
    private final String username;

    public PacketLoginSuccess(LinkedList<Byte> bytes) {
        super(0x02);
        uuid = new UUID(bytes);
        username = unpackString(bytes);
        Proxy.setServerState(Proxy.State.PLAY);
    }

    @Override
    public void packData(LinkedList<Byte> bytes) {
        uuid.pack(bytes);
        packString(username, bytes);
    }

    @Override
    public String toString() {
        return "PacketLoginSuccess{" +
                "uuid=" + uuid +
                ", username='" + username + '\'' +
                '}';
    }
}
