package org.zawamod.zawa.world.entity;

import java.util.List;

/** Recovered inheritance roots for the Stage 5 entity migration. */
public final class ZawaEntityHierarchy {
    private ZawaEntityHierarchy() {}
    public static final List<String> ROOTS = List.of(
        "ZawaBaseEntity extends TamableAnimal",
        "ZawaLandEntity extends ZawaBaseEntity",
        "ZawaAquaticEntity extends ZawaBaseEntity",
        "ZawaSemiAquaticEntity extends ZawaBaseEntity",
        "ZawaFlyingEntity extends ZawaBaseEntity implements FlyingAnimal",
        "ZawaBaseAmbientEntity extends Animal",
        "ZawaAmbientLandEntity extends ZawaBaseAmbientEntity",
        "ZawaAmbientFishEntity extends ZawaBaseAmbientEntity implements Bucketable",
        "ZawaAmbientFlyingEntity extends ZawaBaseAmbientEntity"
    );
}
