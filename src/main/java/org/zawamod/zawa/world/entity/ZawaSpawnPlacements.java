package org.zawamod.zawa.world.entity;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import org.zawamod.zawa.world.entity.animal.Orca;
import org.zawamod.zawa.world.entity.animal.ZawaEntities;

/** Only the standalone orca requires a custom spawn-placement rule. */
public final class ZawaSpawnPlacements {
    private ZawaSpawnPlacements() {}

    public static void register(RegisterSpawnPlacementsEvent event) {
        event.register(ZawaEntities.ORCA.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Orca::checkOrcaSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    public static void attach(IEventBus modBus) {
        modBus.addListener(ZawaSpawnPlacements::register);
    }
}
