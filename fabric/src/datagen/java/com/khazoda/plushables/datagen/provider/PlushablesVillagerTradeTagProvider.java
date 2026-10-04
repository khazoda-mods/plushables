package com.khazoda.plushables.datagen.provider;

import com.khazoda.core.reg.KhazReg.BlockEntry;
import com.khazoda.plushables.block.BasePlushable;
import com.khazoda.plushables.item.PlushableBlockItem;
import com.khazoda.plushables.registry.MainRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.trading.VillagerTrade;

import java.util.concurrent.CompletableFuture;

public class PlushablesVillagerTradeTagProvider extends FabricTagsProvider<VillagerTrade> {
  public PlushablesVillagerTradeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, Registries.VILLAGER_TRADE, registriesFuture);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
    var builder = builder(PlushablesVillagerTradeProvider.MASTER_SHEPHERD_TRADES);

    for (BlockEntry<BasePlushable, PlushableBlockItem> plushable : MainRegistry.ALL_PLUSHABLES) {
      builder.addOptional(PlushablesVillagerTradeProvider.keyFor(plushable));
    }
  }
}
