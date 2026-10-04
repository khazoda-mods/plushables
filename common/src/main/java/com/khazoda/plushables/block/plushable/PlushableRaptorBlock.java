package com.khazoda.plushables.block.plushable;

import com.khazoda.plushables.block.BasePlushable;
import com.khazoda.plushables.block.interaction.InteractionEffectBuilder;
import com.khazoda.plushables.block.tooltip.TooltipDataBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PlushableRaptorBlock extends BasePlushable {
  public PlushableRaptorBlock(Properties settings) {
    super(settings, TooltipDataBuilder.create()
            .number(12)
            .artist("Luke")
            .creationDate("13th June 2023")
            .build(),
        InteractionEffectBuilder.create()
            .sound(SoundEvents.WOLF_GROWL_BABY.value())
            .build());
  }

  @Override
  public VoxelShape useShape() {
    VoxelShape shape = Shapes.empty();
    shape = Shapes.or(shape, Shapes.create(0.3125, 0, 0.078125, 0.6875, 0.734375, 0.59375));
    shape = Shapes.or(shape, Shapes.create(0.421875, 0.59375, -0.0625, 0.59375, 0.703125, 0.078125));
    return shape;
  }
}