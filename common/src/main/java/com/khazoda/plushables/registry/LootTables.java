package com.khazoda.plushables.registry;

import com.khazoda.plushables.PlushablesConfig;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraft.core.registries.Registries.LOOT_TABLE;
import static net.minecraft.world.level.storage.loot.BuiltInLootTables.*;

public final class LootTables {
  private static final Map<ResourceKey<LootTable>, LootConfig> LOOT_TABLE_CONFIGS = new HashMap<>();

  static {
    putAll(List.of(
        VILLAGE_DESERT_HOUSE,
        VILLAGE_PLAINS_HOUSE,
        VILLAGE_SAVANNA_HOUSE,
        VILLAGE_SNOWY_HOUSE,
        VILLAGE_TAIGA_HOUSE,
        SPAWN_BONUS_CHEST,
        VILLAGE_ARMORER,
        VILLAGE_BUTCHER,
        VILLAGE_TANNERY,
        VILLAGE_TEMPLE,
        VILLAGE_TOOLSMITH,
        VILLAGE_WEAPONSMITH,
        RUINED_PORTAL,
        ABANDONED_CAMP_SECRET_CHEST
    ), plushList(), ContextIntProviders.between(0, 1));

    putAll(List.of(
        ABANDONED_MINESHAFT,
        BASTION_HOGLIN_STABLE,
        BASTION_TREASURE,
        BURIED_TREASURE,
        DESERT_PYRAMID,
        END_CITY_TREASURE,
        JUNGLE_TEMPLE,
        NETHER_BRIDGE,
        PILLAGER_OUTPOST,
        SHIPWRECK_TREASURE,
        STRONGHOLD_CORRIDOR,
        UNDERWATER_RUIN_BIG,
        WOODLAND_MANSION,
        IGLOO_CHEST,
        ANCIENT_CITY_ICE_BOX,
        ANCIENT_CITY,
        SIMPLE_DUNGEON
    ), List.of(MainRegistry.HEART_OF_GOLD_ITEM.get()), ContextIntProviders.between(0, 1));
  }

  private LootTables() {
  }

  public static boolean modifyLootTable(Identifier id, LootTable.Builder tableBuilder) {
    if (!PlushablesConfig.lootTablePopulationEnabled()) return false;
    LootConfig config = LOOT_TABLE_CONFIGS.get(ResourceKey.create(LOOT_TABLE, id));
    if (config == null) {
      return false;
    }
    tableBuilder.withPool(createPool(config));
    if (RUINED_PORTAL.identifier().equals(id)) {
      tableBuilder.withPool(createPool(new LootConfig(List.of(MainRegistry.HEART_OF_GOLD_ITEM.get()), ContextIntProviders.exactly(1))));
    }
    return true;
  }

  private static LootPool.Builder createPool(LootConfig config) {
    LootPool.Builder pool = LootPool.lootPool().setRolls(config.rolls());
    for (Item item : config.items()) {
      pool.add(LootItem.lootTableItem(item));
    }
    return pool;
  }

  private static void putAll(List<ResourceKey<LootTable>> tables, List<Item> items, Holder<ContextIntProvider> rolls) {
    LootConfig config = new LootConfig(items, rolls);
    for (ResourceKey<LootTable> table : tables) {
      LOOT_TABLE_CONFIGS.put(table, config);
    }
  }

  private static List<Item> plushList() {
    return MainRegistry.ALL_PLUSHABLES.stream()
        .map(plushable -> (Item) plushable.item().get())
        .toList();
  }

  private record LootConfig(List<Item> items, Holder<ContextIntProvider> rolls) {
  }
}
