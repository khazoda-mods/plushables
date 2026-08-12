package com.khazoda.plushables.datagen.provider;

import com.khazoda.core.reg.KhazReg.BlockEntry;
import com.khazoda.plushables.Constants;
import com.khazoda.plushables.block.BasePlushable;
import com.khazoda.plushables.item.PlushableBlockItem;
import com.khazoda.plushables.registry.MainRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.advancements.*;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class PlushablesAdvancementProvider extends AdvancementProvider {
  private static final int PLUSHABLES_PER_ADVANCEMENT = 5;
  private static final Identifier BACKGROUND = Identifier.withDefaultNamespace("block/light_gray_wool");

  public PlushablesAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture, List.of(new PlushablesAdvancements()));
  }

  private static class PlushablesAdvancements implements AdvancementSubProvider {
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
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> output) {
      List<BlockEntry<BasePlushable, PlushableBlockItem>> plushables = MainRegistry.ALL_PLUSHABLES;

      AdvancementHolder root = Advancement.Builder.advancement()
          .display(
              MainRegistry.PLUSHABLE_FROGLIN_BLOCK.get(),
              Component.translatable("advancements.plushables.root.title"),
              Component.translatable("advancements.plushables.root.description"),
              BACKGROUND,
              AdvancementType.TASK,
              false,
              false,
              false
          )
          .addCriterion("has_gold_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_NUGGET))
          .addCriterion("has_flowers", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(registries.lookupOrThrow(Registries.ITEM), BlockItemTags.FLOWERS.item())))
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
              null,
              AdvancementType.TASK,
              true,
              false,
              false
          )
          .addCriterion("has_heart_of_gold", InventoryChangeTrigger.TriggerInstance.hasItems(MainRegistry.HEART_OF_GOLD_ITEM.get()))
          .rewards(plushableRecipeRewards(plushables))
          .build(Constants.ID("plushables/heart_of_gold"));
      output.accept(heartOfGold);

      for (int start = 0; start < plushables.size(); start += PLUSHABLES_PER_ADVANCEMENT) {
        int end = Math.min(start + PLUSHABLES_PER_ADVANCEMENT, plushables.size());
        List<BlockEntry<BasePlushable, PlushableBlockItem>> group = plushables.subList(start, end);
        Advancement.Builder builder = Advancement.Builder.advancement()
            .parent(heartOfGold)
            .display(
                group.getFirst().item().get(),
                Component.translatable("advancements.plushables.collection.title", start + 1, end),
                Component.translatable("advancements.plushables.collection.description", start + 1, end),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            )
            .requirements(AdvancementRequirements.Strategy.AND);

        for (BlockEntry<BasePlushable, PlushableBlockItem> plushable : group) {
          builder.addCriterion(
              plushable.item().id().getPath(),
              InventoryChangeTrigger.TriggerInstance.hasItems(plushable.item().get())
          );
        }

        int advancementNumber = (start / PLUSHABLES_PER_ADVANCEMENT) + 1;
        output.accept(builder.build(Constants.ID("plushables/collection_%02d".formatted(advancementNumber))));
      }
    }
  }
}
