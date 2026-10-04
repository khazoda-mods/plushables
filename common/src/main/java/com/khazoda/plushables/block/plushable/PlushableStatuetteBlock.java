package com.khazoda.plushables.block.plushable;

import com.khazoda.plushables.block.BasePlushable;
import com.khazoda.plushables.block.interaction.InteractionEffectBuilder;
import com.khazoda.plushables.block.tooltip.TooltipDataBuilder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PlushableStatuetteBlock extends BasePlushable {
  public PlushableStatuetteBlock(Properties settings) {
    super(settings,
        TooltipDataBuilder.create()
            .number(36)
            .artist("Khazoda")
            .creationDate("9th September 2023")
            .trivia("A tribute to Terracotta Knights, which is a tribute to Clay Soldiers")
            .build(),
        InteractionEffectBuilder.create()
            .cooldown(35)
            .sound(SoundEvents.METAL_STEP)
            .particle(ParticleTypes.HEART)
            .particleYOffset(0.2f)
            .particleSpread(0.1)
            .particleCount(1)
            .build());
  }

  @Override
  public VoxelShape useShape() {
    VoxelShape shape = Shapes.empty();
    shape = Shapes.or(shape, Shapes.box(0.40625, 0, 0.375, 0.59375, 0.75, 0.5));
    return shape;
  }
}
