package org.zawamod.zawa.world.entity.ai.goal;
import net.minecraft.world.entity.PathfinderMob; import net.minecraft.world.entity.ai.goal.Goal;
public class BreachGoal extends Goal { private final PathfinderMob entity; private final int interval; public BreachGoal(PathfinderMob e,int interval){entity=e;this.interval=interval;} @Override public boolean canUse(){return entity.isInWater() && entity.getRandom().nextInt(Math.max(1,interval))==0;} @Override public void start(){entity.setDeltaMovement(entity.getDeltaMovement().add(0,0.5,0));} }
