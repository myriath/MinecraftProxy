package proxy.packets.clientbound.login;

import proxy.Proxy;
import proxy.packets.Packet;
import proxy.structs.VarInt;

import java.util.LinkedList;

public class PacketSetCompression extends Packet {
    private final VarInt threshold;

    public PacketSetCompression(LinkedList<Byte> bytes) {
        super(0x03);
        threshold = new VarInt(bytes);
        if (threshold.getValue() >= 0) {
            Proxy.compressed = true;
            Proxy.compressionThreshold = threshold.getValue();
        }
    }

    @Override
    public void packData(LinkedList<Byte> bytes) {
        threshold.pack(bytes);
    }

    @Override
    public String toString() {
        return "PacketSetCompression{" +
                "threshold=" + threshold +
                '}';
    }
}
