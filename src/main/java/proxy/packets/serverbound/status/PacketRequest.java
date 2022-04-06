package proxy.packets.serverbound.status;

import proxy.packets.Packet;

import java.util.LinkedList;

public class PacketRequest extends Packet {
    public PacketRequest() {
        super(0x00);
    }

    @Override
    public void packData(LinkedList<Byte> bytes) {}

    @Override
    public String toString() {
        return "PacketPing{}";
    }
}
