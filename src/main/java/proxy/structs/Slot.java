package proxy.structs;

import proxy.structs.nbt.NBT;

import java.util.LinkedList;

import static util.Util.*;

public class Slot {
    private final Boolean present;
    private final VarInt itemID;
    private final Byte itemCount;
    private final NBT nbt;

    public Slot(LinkedList<Byte> bytes) {
        this.present = unpackBoolean(bytes);
        if (present != null && present) {
            itemID = new VarInt(bytes);
            itemCount = bytes.remove();
//            nbt = new NBT(bytes);
            if (bytes.get(0) == 0x00) {
                nbt = null;
                bytes.remove();
            } else {
                nbt = new NBT(bytes);
            }
        } else {
            itemID = null;
            itemCount = null;
            nbt = null;
        }
    }

    public void pack(LinkedList<Byte> bytes) {
        packBoolean(present, bytes);
        if (itemID != null) itemID.pack(bytes);
        if (itemCount != null) bytes.add(itemCount);
        if (nbt != null) nbt.pack(bytes);
        else bytes.add((byte)0x00);
    }
}
