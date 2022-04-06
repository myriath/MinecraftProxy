package proxy.structs;

import java.util.LinkedList;

public class VarLong {
    private Long value;

    public VarLong(Long value) {
        this.value = value;
    }

    public VarLong(LinkedList<Byte> bytes) {
        if (bytes.isEmpty()) return;

        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = bytes.remove();
            value |= (long) (currentByte & 0x7f) << position;

            if ((currentByte & 0x80) == 0) {
                this.value = value;
                return;
            }
            position += 7;

            if (position >= 64) throw new RuntimeException("VarLong too large");
        }
    }

    public Long getValue() {
        return value;
    }

    public void pack(LinkedList<Byte> bytes) {
        long value = this.value;
        while (true) {
            if ((value & ~((long) 0x7f)) == 0) {
                bytes.add((byte) (value & 0xff));
                return;
            }

            bytes.add((byte) ((value & 0x7f) | 0x80));
            value >>>= 7;
        }
    }
}
