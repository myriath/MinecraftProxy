package proxy.structs.recipes;

import proxy.structs.Ingredient;
import proxy.structs.Slot;
import proxy.structs.VarInt;

import java.util.LinkedList;

import static util.Util.*;

public class RecipeCooking extends Recipe {
    private final String group;
    private final Ingredient ingredient;
    private final Slot result;
    private final Float experience;
    private final VarInt cookingTime;

    public RecipeCooking(String type, LinkedList<Byte> bytes) {
        super(type, bytes);
        group = unpackString(bytes);
        ingredient = new Ingredient(bytes);
        result = new Slot(bytes);
        experience = unpackFloat(bytes);
        cookingTime = new VarInt(bytes);
    }

    @Override
    public void pack(LinkedList<Byte> bytes) {
        super.pack(bytes);
        packString(group, bytes);
        ingredient.pack(bytes);
        result.pack(bytes);
        packFloat(experience, bytes);
        cookingTime.pack(bytes);
    }
}
