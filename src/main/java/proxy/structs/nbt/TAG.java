package proxy.structs.nbt;

import java.util.LinkedList;

public class TAG {
    TAG parent;
    LinkedList<TAG> children;

    private final String name;
    private final byte id;

    public TAG(String name, int id) {
        this.name = name;
        this.id = (byte) (id & 0xff);
    }
}
