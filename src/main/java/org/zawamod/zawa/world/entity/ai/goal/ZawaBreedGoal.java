package org.zawamod.zawa.world.entity.ai.goal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;
import org.zawamod.zawa.world.entity.animal.Orca;
import java.util.EnumSet;
import java.util.List;

public class ZawaBreedGoal extends Goal {
    private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D);
    protected final ZawaBaseEntity animal;
    private final Class<? extends ZawaBaseEntity> partnerClass;
    private final double speedModifier;
    protected ZawaBaseEntity partner;
    private int loveTime;
    public ZawaBreedGoal(ZawaBaseEntity animal, double speed) { this(animal, speed, animal.getClass()); }
    public ZawaBreedGoal(ZawaBaseEntity animal, double speed, Class<? extends ZawaBaseEntity> partnerClass) {
        this.animal=animal; this.speedModifier=speed; this.partnerClass=partnerClass; setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }
    @Override public boolean canUse() { return animal.isInLove() && (animal instanceof Orca || !animal.isPregnant()) && (partner=getFreePartner()) != null; }
    @Override public boolean canContinueToUse() { return partner != null && partner.isAlive() && partner.isInLove() && loveTime < 60; }
    @Override public void stop() { partner=null; loveTime=0; }
    @Override public void tick() {
        animal.getLookControl().setLookAt(partner, 10.0F, animal.getMaxHeadXRot());
        animal.getNavigation().moveTo(partner, speedModifier); loveTime++;
        if (loveTime >= 60 && closeEnoughToMate()) breed();
    }
    // Measure distance between collision boxes rather than entity centres.
    // Orcas can be adjacent without their centre points getting within 3 blocks.
    private boolean closeEnoughToMate() {
        if (partner == null) return false;
        var a = animal.getBoundingBox();
        var b = partner.getBoundingBox();
        double dx = Math.max(0.0D, Math.max(a.minX - b.maxX, b.minX - a.maxX));
        double dy = Math.max(0.0D, Math.max(a.minY - b.maxY, b.minY - a.maxY));
        double dz = Math.max(0.0D, Math.max(a.minZ - b.maxZ, b.minZ - a.maxZ));
        return dx * dx + dy * dy + dz * dz <= 2.25D;
    }
    private ZawaBaseEntity getFreePartner() {
        List<? extends ZawaBaseEntity> list = animal.level().getEntitiesOfClass(partnerClass, animal.getBoundingBox().inflate(8), e -> e != animal && e.isAlive() && animal.canMate(e));
        return list.stream().min((a,b)->Double.compare(animal.distanceToSqr(a), animal.distanceToSqr(b))).orElse(null);
    }
    protected void breed() { if (animal.level() instanceof net.minecraft.server.level.ServerLevel server) animal.breed(server, partner); }
}
