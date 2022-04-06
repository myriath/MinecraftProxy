package proxy.structs;

import java.util.LinkedList;

public class VarInt {
    Integer value;

    public VarInt(Integer value) {
        this.value = value;
    }

    public VarInt(LinkedList<Byte> bytes) {
        if (bytes.isEmpty()) return;

        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = bytes.remove();
            value |= (currentByte & 0x7f) << position;

            if ((currentByte & 0x80) == 0) {
                this.value = value;
                return;
            }
            position += 7;
            if (position >= 32) throw new RuntimeException("VarInt too large");
        }
    }

    public Integer getValue() {
        return value;
    }

    public void pack(LinkedList<Byte> bytes) {
        int value = this.value;
        while (true) {
            if ((value & ~0x7f) == 0) {
                bytes.add((byte) (value & 0xff));
                return;
            }

            bytes.add((byte) ((value & 0x7f) | 0x80));
            value >>>= 7;
        }
    }
}
