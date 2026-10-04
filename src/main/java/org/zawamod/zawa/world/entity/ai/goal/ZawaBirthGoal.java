package org.zawamod.zawa.world.entity.ai.goal;
import net.minecraft.world.entity.ai.goal.Goal;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;
public class ZawaBirthGoal extends Goal {
 protected final ZawaBaseEntity mother; public ZawaBirthGoal(ZawaBaseEntity m){mother=m;}
 @Override public boolean canUse(){return mother.isPregnant() && mother.getGestationTimer()>0;}
 @Override public boolean canContinueToUse(){return mother.getGestationTimer()>0 && mother.isPregnant();}
 @Override public void tick(){int t=mother.getGestationTimer()-1; mother.setGestationTimer(t); if(t<=0 && mother.level() instanceof net.minecraft.server.level.ServerLevel s) mother.spawnChildrenFromPregnancy(s);}
}
