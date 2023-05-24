package net.andychen.mineterra.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class HellforgeRecipe implements Recipe<SimpleInventory> {
    protected final float experience;
    protected final int cookingTime;
    private final Identifier id;
    private final DefaultedList<Ingredient> recipeItems;
    private final ItemStack output;

    public HellforgeRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems, float experience, int cookingTime) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (world.isClient()) {
            return false;
        }
        return recipeItems.get(0).test(inventory.getStack(1)) && recipeItems.get(1).test(inventory.getStack(2))
                && recipeItems.get(2).test(inventory.getStack(3)) && recipeItems.get(3).test(inventory.getStack(4));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return this.output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return this.output.copy();
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    public float getExperience() {
        return this.experience;
    }

    public int getCookingTime() {
        return this.cookingTime;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<HellforgeRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "hellforging";

        private Type() {
        }
    }

    public static class Serializer implements RecipeSerializer<HellforgeRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "hellforging";
        // this is the name given in the json file

        @Override
        public HellforgeRecipe read(Identifier id, JsonObject json) {
            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(4, Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            ItemStack result = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
            float experience = JsonHelper.getFloat(json, "experience", 0.0F);
            int cookingTime = JsonHelper.getInt(json, "cookingtime", 400);

            return new HellforgeRecipe(id, result, inputs, experience, cookingTime);
        }

        @Override
        public HellforgeRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            ItemStack output = buf.readItemStack();
            float experience = buf.readFloat();
            int cookingTime = buf.readInt();
            return new HellforgeRecipe(id, output, inputs, experience, cookingTime);
        }

        @Override
        public void write(PacketByteBuf buf, HellforgeRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getOutput());
            buf.writeFloat(recipe.getExperience());
            buf.writeInt(recipe.getCookingTime());
        }
    }
}
