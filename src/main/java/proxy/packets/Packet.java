package proxy.packets;

import proxy.Proxy;
import proxy.structs.VarInt;

import java.util.LinkedList;

public abstract class Packet {
    private final VarInt id;

    public Packet(VarInt id) {
        this.id = id;
    }

    public Packet(int id) {
        this(new VarInt(id));
    }

//    public byte[] packData(byte[][] data) {
//        int size = 0;
//        for (byte[] b : data) {
//            size += b.length;
//        }
//        byte[] packed = new byte[size];
//        size = 0;
//        for (byte[] b : data) {
//            System.arraycopy(b, 0, packed, size, b.length);
//            size += b.length;
//        }
//        return packed;
//    }

    public void packLength(LinkedList<Byte> bytes) {
        LinkedList<Byte> ret = new LinkedList<>();
        new VarInt(bytes.size()).pack(ret);
        bytes.addAll(0, ret);
    }

    public void compress(LinkedList<Byte> bytes) {
        if (Proxy.compressed) {
            // do stuff
        }
    }

    public void encrypt(LinkedList<Byte> bytes) {
        if (Proxy.encrypted) {
            // do stuff
        }
    }

    /**
     * Steps to follow:
     * Pack ID
     * Pack Data
     * Compress
     * Pack packet length
     * Encrypt
     */
    public void pack(LinkedList<Byte> bytes) {
        id.pack(bytes);
        packData(bytes);
        packLength(bytes);
        compress(bytes);
        encrypt(bytes);
    }

    protected VarInt getID() {
        return id;
    }

    public abstract void packData(LinkedList<Byte> bytes);
}