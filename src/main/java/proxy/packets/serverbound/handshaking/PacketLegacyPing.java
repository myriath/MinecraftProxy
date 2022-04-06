package proxy.packets.serverbound.handshaking;

import proxy.packets.Packet;

import java.util.LinkedList;

public class PacketLegacyPing extends Packet {
    public PacketLegacyPing() {
        super(0xfe);
    }

    /**
     * Special case, ping packet is always 0xfe01
     */
    @Override
    public void pack(LinkedList<Byte> bytes) {
        getID().pack(bytes);
        bytes.add((byte) 0x01);
    }

    /**
     * Won't be used by this packet since pack is overridden.
     * @param bytes unused
     */
    @Override
    public void packData(LinkedList<Byte> bytes) {}
}
