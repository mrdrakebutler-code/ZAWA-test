package org.zawamod.zawa.world.entity.ai.goal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import java.util.EnumSet;
public class FollowGroupGoal<T extends Mob> extends Goal { private final T mob; private T group; public FollowGroupGoal(T mob){this.mob=mob;setFlags(EnumSet.of(Flag.MOVE));}
 @Override public boolean canUse(){ group=mob.level().getEntitiesOfClass((Class<T>)mob.getClass(),mob.getBoundingBox().inflate(8),e->e!=mob && e.isAlive()).stream().findFirst().orElse(null); return group!=null; }
 @Override public boolean canContinueToUse(){return group!=null&&group.isAlive()&&mob.distanceToSqr(group)>9;}
 @Override public void tick(){if(group!=null)mob.getNavigation().moveTo(group,1.0);}
}
