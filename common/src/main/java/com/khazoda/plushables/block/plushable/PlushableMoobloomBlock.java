package com.khazoda.plushables.block.plushable;

import com.khazoda.plushables.block.BasePlushable;
import com.khazoda.plushables.block.interaction.InteractionEffectBuilder;
import com.khazoda.plushables.block.tooltip.TooltipDataBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.animal.cow.CowSoundVariants;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PlushableMoobloomBlock extends BasePlushable {
  public PlushableMoobloomBlock(Properties settings) {
    super(settings,
        TooltipDataBuilder.create()
            .number(30)
            .artist("ChrysanthCow")
            .creationDate("19th August 2023")
            .trivia("If you love mooblooms you'll adore the Bovines and Buttercups mod")
            .build(),
        InteractionEffectBuilder.create()
            .sound(SoundEvents.COW_SOUNDS.get(CowSoundVariants.SoundSet.CLASSIC).ambientSound().value())
            .volume(0.35f)
            .pitch(2.5f)
            .build()
    );
  }

  @Override
  public VoxelShape useShape() {
    VoxelShape shape = Shapes.empty();
    shape = Shapes.or(shape, Shapes.create(0.3125, 0.1875, 0.125, 0.6875, 0.5, 0.4375));
    shape = Shapes.or(shape, Shapes.create(0.375, 0.0625, 0.3125, 0.625, 0.375, 0.8125));
    shape = Shapes.or(shape, Shapes.create(0.4375, 0, 0.59375, 0.5625, 0.0625, 0.71875));
    return shape;
  }
}