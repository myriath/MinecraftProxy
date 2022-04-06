package proxy.packets.serverbound.login;

import proxy.packets.Packet;
import proxy.structs.VarInt;

import java.util.LinkedList;

import static util.Util.*;

public class PacketLoginPluginResponse extends Packet {
    private final VarInt messageID;
    private final Boolean successful;
    private final LinkedList<Byte> optional;

    public PacketLoginPluginResponse(LinkedList<Byte> bytes) {
        super(0x02);
        messageID = new VarInt(bytes);
        successful = unpackBoolean(bytes);
        optional = new LinkedList<>();
        while (!bytes.isEmpty()) {
            optional.add(bytes.remove());
        }
    }

    @Override
    public void packData(LinkedList<Byte> bytes) {
        messageID.pack(bytes);
        packBoolean(successful, bytes);
        bytes.addAll(optional);
    }
}
