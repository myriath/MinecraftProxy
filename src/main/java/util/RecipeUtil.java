package util;

import proxy.structs.recipes.*;

import java.util.LinkedList;

import static util.Util.*;

public class RecipeUtil {
    public static Recipe generate(LinkedList<Byte> bytes) {
        String type = unpackString(bytes);
        if (type == null) return null;

        return switch (type) {
            case "minecraft:crafting_shapeless" -> new RecipeShapeless(bytes);
            case "minecraft:crafting_shaped" -> new RecipeShaped(bytes);
            case "minecraft:smelting", "minecraft:blasting", "minecraft:smoking", "minecraft:campfire_cooking" -> new RecipeCooking(type, bytes);
            case "minecraft:stonecutting" -> new RecipeStonecutting(bytes);
            case "minecraft:smithing" -> new RecipeSmithing(bytes);
            default -> new Recipe(type, bytes);
        };
    }
}
