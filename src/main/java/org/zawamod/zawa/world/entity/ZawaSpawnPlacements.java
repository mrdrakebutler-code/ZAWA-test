package org.zawamod.zawa.world.entity;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import org.zawamod.zawa.world.entity.animal.Flamingo;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;
import org.zawamod.zawa.world.entity.animal.ZawaEntities;

/**
 * NeoForge 1.21.1 spawn-placement migration for the entities currently
 * reconstructed in the port. Placement types and heightmaps mirror the
 * original ZAWA 1.20.1 registration.
 */
public final class ZawaSpawnPlacements {
    private ZawaSpawnPlacements() {}

    public static void register(RegisterSpawnPlacementsEvent event) {
        event.register(ZawaEntities.AFRICAN_LION.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZawaBaseEntity::checkLandSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.AFRICAN_WILD_DOG.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZawaBaseEntity::checkLandSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.ASIAN_ELEPHANT.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZawaBaseEntity::checkLandSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.BALD_EAGLE.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING, ZawaBaseEntity::checkFlyingSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.BLACK_FOOTED_FERRET.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZawaBaseEntity::checkLandSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.COMMON_CHIMPANZEE.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING, ZawaBaseEntity::checkLandSpawnRulesWithLeaves,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.COQUERELS_SIFAKA.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING, ZawaBaseEntity::checkLandSpawnRulesWithLeaves,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.EMPEROR_PENGUIN.get(), SpawnPlacementTypes.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZawaBaseEntity::checkSemiAquaticSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.FLAMINGO.get(), SpawnPlacementTypes.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING, Flamingo::checkFlamingoSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.SUMATRAN_ORANGUTAN.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING, ZawaBaseEntity::checkLandSpawnRulesWithLeaves,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.TREE_FROG.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING, ZawaBaseEntity::checkLandSpawnRulesWithLeaves,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.WESTERN_LOWLAND_GORILLA.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING, ZawaBaseEntity::checkLandSpawnRulesWithLeaves,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.AFRICAN_LAKE_CICHLID.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkAquaticSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.ANGELFISH.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkAquaticSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.BETTA.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkAquaticSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.CLOWNFISH.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkAquaticSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.COD.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkAquaticSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.CORYDORAS.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkAquaticSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.GRAMMA.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkAquaticSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.PLECOSTOMUS.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkAquaticSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.SALMON.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkAquaticSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.BUTTERFLY.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkFlyingSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.BROWN_RAT.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkLandSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.HONEY_BEE.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkFlyingSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.LEAFCUTTER_ANT.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkLandSpawnRulesWithLeaves,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.PRAYING_MANTIS.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkLandSpawnRulesWithLeaves,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.SCORPION.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkLandSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ZawaEntities.TARANTULA.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity::checkLandSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    public static void attach(IEventBus modBus) {
        modBus.addListener(ZawaSpawnPlacements::register);
    }
}
