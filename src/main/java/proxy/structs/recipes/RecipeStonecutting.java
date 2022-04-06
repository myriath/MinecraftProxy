package proxy.structs.recipes;

import proxy.structs.Ingredient;
import proxy.structs.Slot;

import java.util.LinkedList;

import static util.Util.*;

public class RecipeStonecutting extends Recipe {
    private final String group;
    private final Ingredient ingredient;
    private final Slot result;

    public RecipeStonecutting(LinkedList<Byte> bytes) {
        super("minecraft:stonecutting", bytes);
        group = unpackString(bytes);
        ingredient = new Ingredient(bytes);
        result = new Slot(bytes);
    }

    @Override
    public void pack(LinkedList<Byte> bytes) {
        super.pack(bytes);
        packString(group, bytes);
        ingredient.pack(bytes);
        result.pack(bytes);
    }
}
