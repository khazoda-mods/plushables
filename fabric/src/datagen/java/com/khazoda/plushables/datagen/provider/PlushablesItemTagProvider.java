package com.khazoda.plushables.datagen.provider;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.khazoda.plushables.Constants;
import com.khazoda.plushables.registry.MainRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;

public class PlushablesItemTagProvider implements DataProvider {
  public static final Identifier ALL_PLUSHABLE_ITEMS = Constants.ID("all_plushables");
  private final PackOutput.PathProvider pathProvider;

  public PlushablesItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "tags/item");
  }

  @Override
  public CompletableFuture<?> run(CachedOutput output) {
    JsonObject tag = new JsonObject();
    tag.addProperty("replace", false);

    JsonArray values = new JsonArray();

    for (var plushable : MainRegistry.ALL_PLUSHABLES) {
      values.add(plushable.item().id().toString());
    }

    tag.add("values", values);
    return DataProvider.saveStable(output, tag, this.pathProvider.json(ALL_PLUSHABLE_ITEMS));
  }

  @Override
  public String getName() {
    return "Plushables Item Tags";
  }
}
