package com.khazoda.plushables.block.plushable;

import com.khazoda.plushables.block.BasePlushable;
import com.khazoda.plushables.block.interaction.InteractionEffectBuilder;
import com.khazoda.plushables.block.tooltip.TooltipDataBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PlushablePenguinBlock extends BasePlushable {
  public PlushablePenguinBlock(Properties settings) {
    super(settings, TooltipDataBuilder.create()
            .number(1)
            .artist("Khazoda")
            .creationDate("21st March 2019")
            .build(),
        InteractionEffectBuilder.create()
            .sound(SoundEvents.PUFFER_FISH_FLOP)
            .build());
  }

  @Override
  public VoxelShape useShape() {
    VoxelShape shape = Shapes.empty();
    shape = Shapes.or(shape, Shapes.create(0.125, 0, 0.25, 0.875, 0.875, 0.625));
    shape = Shapes.or(shape, Shapes.create(0.75, 0.6875, 0.1875, 0.875, 0.8125, 0.25));
    shape = Shapes.or(shape, Shapes.create(0.125, 0.6875, 0.1875, 0.25, 0.8125, 0.25));
    shape = Shapes.or(shape, Shapes.create(0.375, 0.625, 0.125, 0.625, 0.75, 0.25));
    shape = Shapes.or(shape, Shapes.create(0.1875, 0.0625, 0.625, 0.8125, 0.8125, 0.6875));
    return shape;
  }
}