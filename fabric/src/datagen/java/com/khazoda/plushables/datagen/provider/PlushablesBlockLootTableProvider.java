package com.khazoda.plushables.datagen.provider;

import com.khazoda.plushables.registry.MainRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.concurrent.CompletableFuture;

public class PlushablesBlockLootTableProvider extends FabricBlockLootSubProvider {
  public PlushablesBlockLootTableProvider(FabricPackOutput packOutput,
                                          CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(packOutput, registryLookup);
  }

  @Override
  public void generate() {
    for (var plushable : MainRegistry.ALL_PLUSHABLES) {
      add(plushable.get(), this::createSingleItemTableWithContainer);
    }
  }

  private LootTable.Builder createSingleItemTableWithContainer(Block block) {
    return LootTable.lootTable()
        .withPool(applyExplosionCondition(block, LootPool.lootPool()
            .setRolls(ContextIntProviders.exactly(1))
            .add(LootItem.lootTableItem(block)
                .apply(CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
                    .include(DataComponents.CONTAINER)))));
  }
}
