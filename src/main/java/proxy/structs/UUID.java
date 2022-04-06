package proxy.structs;

import java.util.Arrays;
import java.util.LinkedList;

public class UUID {
    LinkedList<Byte> bytes;

    public UUID(LinkedList<Byte> bytes) {
        this.bytes = new LinkedList<>();
        for (int i = 0; i < 16; i++) {
            this.bytes.add(bytes.remove());
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(bytes.toArray());
    }

    public void pack(LinkedList<Byte> bytes) {
        bytes.addAll(this.bytes);
    }
}
