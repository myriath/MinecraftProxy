package proxy.structs;

import java.util.LinkedList;

public class Ingredient {
    private final VarInt count;
    private final Slot[] items;

    public Ingredient(LinkedList<Byte> bytes) {
        count = new VarInt(bytes);
        items = new Slot[count.getValue()];
        for (int i = 0; i < count.getValue(); i++) {
            items[i] = new Slot(bytes);
        }
    }

    public void pack(LinkedList<Byte> bytes) {
        count.pack(bytes);
        for (Slot s : items) {
            s.pack(bytes);
        }
    }
}
