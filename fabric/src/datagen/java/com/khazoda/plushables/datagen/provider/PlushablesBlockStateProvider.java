package com.khazoda.plushables.datagen.provider;

import com.google.gson.JsonObject;
import com.khazoda.plushables.registry.MainRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class PlushablesBlockStateProvider implements DataProvider {
  private final PackOutput.PathProvider pathProvider;

  public PlushablesBlockStateProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
  }

  @Override
  public CompletableFuture<?> run(CachedOutput output) {
    return CompletableFuture.allOf(MainRegistry.ALL_PLUSHABLES.stream().map(plushable -> {
      var blockId = plushable.block().id();
      JsonObject variant = new JsonObject();
      variant.addProperty("model", blockId.withPrefix("block/").toString());

      JsonObject variants = new JsonObject();
      variants.add("", variant);

      JsonObject blockState = new JsonObject();
      blockState.add("variants", variants);
      return DataProvider.saveStable(output, blockState, this.pathProvider.json(blockId));
    }).toArray(CompletableFuture[]::new));
  }

  @Override
  public String getName() {
    return "Plushables Blockstates";
  }
}
