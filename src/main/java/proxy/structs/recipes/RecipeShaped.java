package proxy.structs.recipes;

import proxy.structs.Ingredient;
import proxy.structs.Slot;
import proxy.structs.VarInt;

import java.util.LinkedList;

import static util.Util.*;

public class RecipeShaped extends Recipe {
    private final VarInt width;
    private final VarInt height;
    private final String group;
    private final Ingredient[] ingredients;
    private final Slot result;

    public RecipeShaped(LinkedList<Byte> bytes) {
        super("minecraft:crafting_shaped", bytes);
        width = new VarInt(bytes);
        height = new VarInt(bytes);
        group = unpackString(bytes);
        ingredients = new Ingredient[width.getValue() * height.getValue()];
        for (int i = 0; i < ingredients.length; i++) {
            ingredients[i] = new Ingredient(bytes);
        }
        result = new Slot(bytes);
    }

    @Override
    public void pack(LinkedList<Byte> bytes) {
        super.pack(bytes);
        width.pack(bytes);
        height.pack(bytes);
        packString(group, bytes);
        for (Ingredient i : ingredients) {
            i.pack(bytes);
        }
        result.pack(bytes);
    }
}
