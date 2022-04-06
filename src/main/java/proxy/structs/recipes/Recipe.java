package proxy.structs.recipes;

import java.util.LinkedList;

import static util.Util.*;

public class Recipe {
    private final String type;
    private final String id;

    public Recipe(String type, LinkedList<Byte> bytes) {
        this.type = type;
        id = unpackString(bytes);
    }

    public String getType() {
        return type;
    }

    public String getID() {
        return id;
    }

    public void pack(LinkedList<Byte> bytes) {
        packString(type, bytes);
        packString(id, bytes);
    }
}
