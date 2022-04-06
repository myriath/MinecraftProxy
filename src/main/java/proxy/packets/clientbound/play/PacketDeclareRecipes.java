package proxy.packets.clientbound.play;

import proxy.packets.Packet;
import proxy.structs.VarInt;
import proxy.structs.recipes.Recipe;
import util.RecipeUtil;

import java.util.Arrays;
import java.util.LinkedList;

public class PacketDeclareRecipes extends Packet {
    private final VarInt numRecipes;
    private final Recipe[] recipes;

    public PacketDeclareRecipes(LinkedList<Byte> bytes) {
        super(0x66);
        numRecipes = new VarInt(bytes);
        recipes = new Recipe[numRecipes.getValue()];
        for (int i = 0; i < numRecipes.getValue(); i++) {
            recipes[i] = RecipeUtil.generate(bytes);
        }
    }

    @Override
    public void packData(LinkedList<Byte> bytes) {
        numRecipes.pack(bytes);
        for (Recipe recipe : recipes) {
            recipe.pack(bytes);
        }
    }

    @Override
    public String toString() {
        return "PacketDeclareRecipes{" +
                "numRecipes=" + numRecipes +
                ", recipes=" + Arrays.toString(recipes) +
                '}';
    }
}
