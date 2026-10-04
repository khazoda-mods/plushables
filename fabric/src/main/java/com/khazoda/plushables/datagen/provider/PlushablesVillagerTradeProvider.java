package com.khazoda.plushables.datagen.provider;

import com.khazoda.core.reg.KhazReg.BlockEntry;
import com.khazoda.plushables.block.BasePlushable;
import com.khazoda.plushables.item.PlushableBlockItem;
import com.khazoda.plushables.registry.MainRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static com.khazoda.plushables.Constants.ID;

public class PlushablesVillagerTradeProvider extends FabricCodecDataProvider<VillagerTrade> {
  public static final TagKey<VillagerTrade> MASTER_SHEPHERD_TRADES = TagKey.create(Registries.VILLAGER_TRADE, Identifier.withDefaultNamespace("shepherd/level_5"));

  private static final int PLUSHABLE_EMERALD_COST = 4;
  private static final int PLUSHABLE_MAX_USES = 12;
  private static final int PLUSHABLE_VILLAGER_XP = 30;
  private static final float PLUSHABLE_PRICE_MULTIPLIER = 0.05F;

  public PlushablesVillagerTradeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture, Registries.VILLAGER_TRADE, VillagerTrade.CODEC);
  }

  public static ResourceKey<VillagerTrade> keyFor(BlockEntry<BasePlushable, PlushableBlockItem> plushable) {
    return ResourceKey.create(Registries.VILLAGER_TRADE, ID("shepherd/5/" + plushable.item().id().getPath()));
  }

  private static VillagerTrade createTrade(BlockEntry<BasePlushable, PlushableBlockItem> plushable) {
    return new VillagerTrade.Builder(
        new TradeCost(Items.EMERALD, PLUSHABLE_EMERALD_COST),
        new ItemStackTemplate(plushable.item().get()),
        ContextIntProviders.exactly(PLUSHABLE_MAX_USES),
        ContextIntProviders.exactly(PLUSHABLE_VILLAGER_XP),
        ContextFloatProviders.exactly(PLUSHABLE_PRICE_MULTIPLIER)
    ).build();
  }

  @Override
  protected void configure(BiConsumer<Identifier, VillagerTrade> provider, HolderLookup.Provider lookup) {
    for (BlockEntry<BasePlushable, PlushableBlockItem> plushable : MainRegistry.ALL_PLUSHABLES) {
      provider.accept(keyFor(plushable).identifier(), createTrade(plushable));
    }
  }

  @Override
  public String getName() {
    return "Plushables Villager Trades";
  }
}
