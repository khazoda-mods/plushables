package com.khazoda.plushables.datagen.provider;

import com.khazoda.core.reg.KhazReg.BlockEntry;
import com.khazoda.plushables.Constants;
import com.khazoda.plushables.block.BasePlushable;
import com.khazoda.plushables.item.PlushableBlockItem;
import com.khazoda.plushables.registry.MainRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancements.*;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class PlushablesAdvancementProvider extends FabricAdvancementProvider {
  private static final Identifier BACKGROUND = Identifier.withDefaultNamespace("block/light_gray_wool");

  public PlushablesAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  private static AdvancementRewards plushableRecipeRewards(List<BlockEntry<BasePlushable, PlushableBlockItem>> plushables) {
    AdvancementRewards.Builder rewards = new AdvancementRewards.Builder();
    for (BlockEntry<BasePlushable, PlushableBlockItem> plushable : plushables) {
      rewards.addRecipe(recipeKey(plushable.item().id()));
    }
    return rewards.build();
  }

  private static ResourceKey<Recipe<?>> recipeKey(Identifier id) {
    return ResourceKey.create(Registries.RECIPE, id);
  }

  @Override
  public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> output) {
    List<BlockEntry<BasePlushable, PlushableBlockItem>> plushables = MainRegistry.ALL_PLUSHABLES;

    AdvancementHolder root = Advancement.Builder.advancement()
        .rootDisplay(
            MainRegistry.PLUSHABLE_FROGLIN_BLOCK.item().get(),
            Component.translatable("advancements.plushables.root.title"),
            Component.translatable("advancements.plushables.root.description"),
            BACKGROUND,
            AdvancementType.TASK,
            false,
            false,
            false
        )
        .addCriterion("has_gold_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_NUGGET))
        .addCriterion("has_flowers", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(registries.lookupOrThrow(Registries.ITEM), ConventionalItemTags.FLOWERS)))
        .addCriterion("has_honey_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(Items.HONEY_BOTTLE))
        .requirements(AdvancementRequirements.Strategy.OR)
        .rewards(AdvancementRewards.Builder.recipe(recipeKey(MainRegistry.HEART_OF_GOLD_ITEM.id())))
        .build(Constants.ID("plushables/root"));
    output.accept(root);

    AdvancementHolder heartOfGold = Advancement.Builder.advancement()
        .parent(root)
        .display(
            MainRegistry.HEART_OF_GOLD_ITEM.get(),
            Component.translatable("advancements.plushables.heart_of_gold.title"),
            Component.translatable("advancements.plushables.heart_of_gold.description"),
            AdvancementType.TASK,
            true,
            false,
            false
        )
        .addCriterion("has_heart_of_gold", InventoryChangeTrigger.TriggerInstance.hasItems(MainRegistry.HEART_OF_GOLD_ITEM.get()))
        .rewards(plushableRecipeRewards(plushables))
        .build(Constants.ID("plushables/heart_of_gold"));
    output.accept(heartOfGold);

    // one advancement per plushable, grouped in rows of 5
    AdvancementHolder previous = heartOfGold;
    for (int index = 0; index < plushables.size(); index++) {
      var plushable = plushables.get(index);
      Component name = Component.translatable(plushable.get().getDescriptionId());
      AdvancementHolder advancement = Advancement.Builder.advancement()
          .parent(index % 5 == 0 ? heartOfGold : previous)
          .display(
              plushable.item().get(),
              name,
              Component.translatable("advancements.plushables.collect.description", name),
              AdvancementType.TASK,
              true,
              false,
              false
          )
          .addCriterion(
              plushable.item().id().getPath(),
              InventoryChangeTrigger.TriggerInstance.hasItems(plushable.item().get())
          )
          .build(Constants.ID("plushables/" + plushable.item().id().getPath()));
      output.accept(advancement);
      previous = advancement;
    }
  }
}