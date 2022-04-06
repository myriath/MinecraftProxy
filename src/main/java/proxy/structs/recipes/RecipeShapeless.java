package proxy.structs.recipes;

import proxy.structs.Ingredient;
import proxy.structs.Slot;
import proxy.structs.VarInt;

import java.util.LinkedList;

import static util.Util.packString;
import static util.Util.unpackString;

public class RecipeShapeless extends Recipe {
    private final String group;
    private final VarInt ingredientCount;
    private final Ingredient[] ingredients;
    private final Slot result;

    public RecipeShapeless(LinkedList<Byte> bytes) {
        super("minecraft:crafting_shapeless", bytes);
        group = unpackString(bytes);
        ingredientCount = new VarInt(bytes);
        ingredients = new Ingredient[ingredientCount.getValue()];
        for (int i = 0; i < ingredientCount.getValue(); i++) {
            ingredients[i] = new Ingredient(bytes);
        }
        result = new Slot(bytes);
    }

    @Override
    public void pack(LinkedList<Byte> bytes) {
        super.pack(bytes);
        packString(group, bytes);
        ingredientCount.pack(bytes);
        for (Ingredient i : ingredients) {
            i.pack(bytes);
        }
        result.pack(bytes);
    }
}
