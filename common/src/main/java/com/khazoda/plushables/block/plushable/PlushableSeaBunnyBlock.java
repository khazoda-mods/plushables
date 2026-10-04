package com.khazoda.plushables.block.plushable;

import com.khazoda.plushables.block.BasePlushable;
import com.khazoda.plushables.block.interaction.InteractionEffectBuilder;
import com.khazoda.plushables.block.tooltip.TooltipDataBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PlushableSeaBunnyBlock extends BasePlushable {
  public PlushableSeaBunnyBlock(Properties settings) {
    super(settings, TooltipDataBuilder.create()
            .number(35)
            .artist("Khazoda")
            .creationDate("9th September 2023")
            .build(),
        InteractionEffectBuilder.create()
            .sound(SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT)
            .build());
  }

  @Override
  public VoxelShape useShape() {
    VoxelShape shape = Shapes.empty();
    shape = Shapes.or(shape, Shapes.create(0.25, 0, 0.25, 0.75, 0.5, 0.75));
    shape = Shapes.or(shape, Shapes.create(0.25, 0.5, 0.25, 0.375, 0.75, 0.375));
    shape = Shapes.or(shape, Shapes.create(0.625, 0.5, 0.25, 0.75, 0.75, 0.375));

    return shape;
  }
}