package org.zawamod.zawa.world.entity.ai.goal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;
import java.util.function.Predicate;
public class MoveToItemGoal extends Goal {
 protected final ZawaBaseEntity zawaBaseEntity; protected final Predicate<ItemEntity> pickupItemsPredicate; private int cooldown;
 public MoveToItemGoal(ZawaBaseEntity e, Predicate<ItemEntity> p){zawaBaseEntity=e;pickupItemsPredicate=p;}
 @Override public boolean canUse(){if(cooldown>0){cooldown--;return false;} return zawaBaseEntity.level().getEntitiesOfClass(ItemEntity.class,zawaBaseEntity.getBoundingBox().inflate(8),pickupItemsPredicate).stream().findFirst().isPresent();}
 @Override public void tick(){zawaBaseEntity.level().getEntitiesOfClass(ItemEntity.class,zawaBaseEntity.getBoundingBox().inflate(8),pickupItemsPredicate).stream().min((a,b)->Double.compare(zawaBaseEntity.distanceToSqr(a),zawaBaseEntity.distanceToSqr(b))).ifPresent(i->zawaBaseEntity.getNavigation().moveTo(i,1.0));}
 @Override public void stop(){cooldown=20;}
}
