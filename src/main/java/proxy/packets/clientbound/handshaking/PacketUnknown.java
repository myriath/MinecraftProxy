package proxy.packets.clientbound.handshaking;

import proxy.packets.Packet;
import proxy.structs.VarInt;

import java.util.LinkedList;

public class PacketUnknown extends Packet {
    private final LinkedList<Byte> bytes;
    private final String tag;

    public PacketUnknown(int id, LinkedList<Byte> bytes, String tag) {
        super(id);
        this.bytes = bytes;
        this.tag = tag;
    }

//    /**
//     * Special case, just adds the bytes to the sending list
//     */
//    @Override
//    public void pack(LinkedList<Byte> bytes) {
//        getID().pack(bytes);
//        bytes.addAll(this.bytes);
//        packLength(bytes);
//    }

    /**
     * Won't be used by this packet since pack is overridden.
     * @param bytes unused
     */
    @Override
    public void packData(LinkedList<Byte> bytes) {
        bytes.addAll(this.bytes);
    }

    @Override
    public String toString() {
        return "PacketUnknown{" +
                "tag='" + tag + '\'' +
                ", bytes=[array]" + //Arrays.toString(bytes) +
                '}';
    }
}
