package com.khazoda.plushables.datagen.provider;

import com.khazoda.plushables.registry.MainRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class PlushablesRecipeProvider extends FabricRecipeProvider {

  public PlushablesRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
    // recipe unlocks are granted by obtaining a Heart of Gold
    RecipeOutput recipesOnly = new RecipeOutput() {
      @Override
      public void accept(ResourceKey<Recipe<?>> id, Recipe<?> recipe, @Nullable AdvancementHolder advancement) {
        recipes.register(id, recipe);
      }

      @Override
      public Advancement.Builder advancement() {
        return Advancement.Builder.recipeAdvancement();
      }

      @Override
      public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> key) {
        return recipes.lookup(key);
      }

      @Deprecated
      @Override
      public <S> Stream<Holder.Reference<S>> listContextElements(ResourceKey<? extends Registry<? extends S>> key) {
        return recipes.listContextElements(key);
      }
    };

    return new RecipeProvider(recipes, advancements) {
      @Override
      public void buildRecipes() {
        /* ==========[ Heart of Gold ]========== */
        shaped(RecipeCategory.MISC, MainRegistry.HEART_OF_GOLD_ITEM.get())
            .pattern("X#X")
            .pattern("#^#")
            .pattern(" # ")
            .define('#', Items.GOLD_NUGGET)
            .define('^', Items.HONEY_BOTTLE)
            .define('X', ConventionalItemTags.FLOWERS)
            .unlockedBy("has_gold_nugget", has(Items.GOLD_NUGGET))
            .unlockedBy("has_flowers", has(ConventionalItemTags.FLOWERS))
            .unlockedBy("has_honey_bottle", has(Items.HONEY_BOTTLE))
            .save(recipesOnly);

        /* ==========[ Plushables ]========== */
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_ANIMATRONIC_BLOCK.item().get(), Items.REDSTONE, Items.WOOL.blue());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_WISP_BLOCK.item().get(), Items.ECHO_SHARD, Items.WOOL.lightBlue());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_WIZARD_BLOCK.item().get(), Items.GOLD_INGOT, Items.WOOL.purple());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_ZIGGY_BLOCK.item().get(), ItemTags.FISHES, Items.WOOL.white());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_WHALE_BLOCK.item().get(), Items.KELP, Items.WOOL.blue());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_WHELPLING_BLOCK.item().get(), Items.BLAZE_POWDER, Items.WOOL.red());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_TRUFFLES_BLOCK.item().get(), Items.LILY_OF_THE_VALLEY, Items.WOOL.pink());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_UNICORN_BLOCK.item().get(), Items.CHERRY_LEAVES, Items.WOOL.white());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_WALRUS_BLOCK.item().get(), Items.SEA_PICKLE, Items.WOOL.brown());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_TIGER_BLOCK.item().get(), Items.PORKCHOP, Items.WOOL.orange());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_TRATER_BLOCK.item().get(), Items.POISONOUS_POTATO, Items.WOOL.cyan());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_TRICERATOPS_BLOCK.item().get(), Items.FERN, Items.WOOL.green());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_SHRUMP_BLOCK.item().get(), Items.RED_MUSHROOM, Items.WOOL.white());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_SNAIL_BLOCK.item().get(), Items.SLIME_BALL, Items.WOOL.cyan());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_STATUETTE_BLOCK.item().get(), Items.TERRACOTTA, Items.WOOL.brown());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_RUPERT_BLOCK.item().get(), ItemTags.FISHES, Items.WOOL.black());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_SEA_BUNNY_BLOCK.item().get(), Items.SEAGRASS, Items.WOOL.white());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_POTSY_BLOCK.item().get(), Items.CAULDRON, Items.WOOL.black());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_RAPTOR_BLOCK.item().get(), Items.FLINT, Items.WOOL.blue());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_RATTIAM_BLOCK.item().get(), Items.WHEAT, Items.WOOL.lightGray());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_OWL_BLOCK.item().get(), Items.GLOW_BERRIES, Items.WOOL.brown());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_PENGUIN_BLOCK.item().get(), Items.SNOWBALL, Items.WOOL.gray());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_PIG_BLOCK.item().get(), Items.MUD, Items.WOOL.pink());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_ORANGUTAN_BLOCK.item().get(), Items.VINE, Items.WOOL.orange());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_OTTER_BLOCK.item().get(), ItemTags.FISHES, Items.WOOL.brown());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_MOOBLOOM_BLOCK.item().get(), Items.SUNFLOWER, Items.WOOL.yellow());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_OCTOPLUSHABLE_BLOCK.item().get(), Items.INK_SAC, Items.WOOL.lightBlue());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_MAMMOTH_BLOCK.item().get(), Items.POINTED_DRIPSTONE, Items.WOOL.brown());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_GOBLIN_BLOCK.item().get(), Items.RED_MUSHROOM, Items.WOOL.lime());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_GOLDFISH_BLOCK.item().get(), Items.GOLD_INGOT, Items.WOOL.orange());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_HAMSTER_BLOCK.item().get(), Items.CARROT, Items.WOOL.orange());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_FOX_BLOCK.item().get(), Items.PUMPKIN, Items.WOOL.orange());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_FROGE_BLOCK.item().get(), Items.LILY_PAD, Items.WOOL.yellow());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_FROGLIN_BLOCK.item().get(), Items.LILY_PAD, Items.WOOL.green());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_DJUNGELSKOG_BLOCK.item().get(), Items.HONEYCOMB, Items.WOOL.brown());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_DORMOUSE_BLOCK.item().get(), Items.OAK_LEAVES, Items.WOOL.brown());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_DRAGON_BLOCK.item().get(), Items.BLAZE_ROD, Items.WOOL.white());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_CONDUCTOR_BLOCK.item().get(), Items.RAIL, Items.WOOL.gray());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_COOPER_BLOCK.item().get(), Items.BONE, Items.WOOL.black());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_CLUCKY_BLOCK.item().get(), Items.EGG, Items.WOOL.white());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_BIG_IRRITATER_BLOCK.item().get(), Items.POTATO, Items.WOOL.red());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_BIG_TATER_BLOCK.item().get(), Items.POTATO, Items.WOOL.pink());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_BLAHAJ_BLOCK.item().get(), Items.PINK_PETALS, Items.WOOL.lightBlue());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_BEAUX_BLOCK.item().get(), Items.BONE, Items.WOOL.gray());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_SNOWIE_BLOCK.item().get(), Items.SNOWBALL, Items.WOOL.white());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_RIBBIT_BLOCK.item().get(), Items.FISHING_ROD, Items.WOOL.lime());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_CREAKY_BLOCK.item().get(), Items.CHERRY_SAPLING, Items.WOOL.black());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_KWEEBEC_BLOCK.item().get(), Items.LEAF_LITTER, Items.WOOL.brown());
        createPlushableRecipe(recipesOnly, MainRegistry.PLUSHABLE_STONELING_BLOCK.item().get(), Items.STONE, Items.WOOL.gray());

      }

      private void createPlushableRecipe(RecipeOutput output, Item result, TagKey<Item> decorativeTag, Item wool) {
        shapeless(RecipeCategory.MISC, result)
            .requires(decorativeTag)
            .requires(wool)
            .requires(MainRegistry.HEART_OF_GOLD_ITEM.get())
            .unlockedBy("has_heart_of_gold", has(MainRegistry.HEART_OF_GOLD_ITEM.get()))
            .save(output);
      }

      private void createPlushableRecipe(RecipeOutput output, Item result, Item decorative, Item wool) {
        shapeless(RecipeCategory.MISC, result)
            .requires(decorative)
            .requires(wool)
            .requires(MainRegistry.HEART_OF_GOLD_ITEM.get())
            .unlockedBy("has_heart_of_gold", has(MainRegistry.HEART_OF_GOLD_ITEM.get()))
            .save(output);
      }
    };
  }

  @Override
  public String getName() {
    return "Plushables Recipes";
  }
}

