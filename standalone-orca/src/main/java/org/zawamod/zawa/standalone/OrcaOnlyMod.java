package org.zawamod.zawa.standalone;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.zawamod.zawa.resources.EntityDietManager;
import org.zawamod.zawa.resources.EntityStatsManager;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.world.entity.animal.Orca;
import org.zawamod.zawa.world.entity.animal.OrcaSounds;

/**
 * Standalone extraction entrypoint. Only the orca entity, its attributes and
 * original sounds are registered here. Not used by the full-ZAWA build.
 *
 * The isolated Gradle source set and dependency closure remain pending.
 */
@Mod(Zawa.MOD_ID)
public final class OrcaOnlyMod {
    public OrcaOnlyMod(IEventBus modBus) {
        OrcaOnlyEntities.register(modBus);
        OrcaSounds.register(modBus);
        modBus.addListener(OrcaOnlyMod::registerAttributes);
        NeoForge.EVENT_BUS.addListener(OrcaOnlyMod::registerDataReloadListeners);
    }

    private static void registerDataReloadListeners(AddReloadListenerEvent event) {
        event.addListener(EntityDietManager.INSTANCE);
        event.addListener(EntityStatsManager.INSTANCE);
    }

    private static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(OrcaOnlyEntities.ORCA.get(), Orca.registerOrcaAttributes().build());
    }
}
