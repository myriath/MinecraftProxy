package proxy.structs.recipes;

import proxy.structs.Ingredient;
import proxy.structs.Slot;

import java.util.LinkedList;
import java.util.Queue;

import static util.Util.*;

public class RecipeSmithing extends Recipe {
    private final Ingredient base;
    private final Ingredient addition;
    private final Slot result;

    public RecipeSmithing(LinkedList<Byte> bytes) {
        super("minecraft:smithing", bytes);
        base = new Ingredient(bytes);
        addition = new Ingredient(bytes);
        result = new Slot(bytes);
    }

    @Override
    public void pack(LinkedList<Byte> bytes) {
        super.pack(bytes);
        base.pack(bytes);
        addition.pack(bytes);
        result.pack(bytes);
    }
}
