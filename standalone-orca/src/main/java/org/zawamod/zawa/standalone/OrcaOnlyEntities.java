package org.zawamod.zawa.standalone;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.world.entity.animal.Orca;

/**
 * Dedicated orca-only entity registration for the future isolated build.
 * This class is not wired into the existing full-ZAWA entrypoint.
 * The original Orca#getBreedOffspring still refers to ZawaEntities.ORCA;
 * that dependency must be isolated before standalone compilation.
 */
public final class OrcaOnlyEntities {
    private static final DeferredRegister<EntityType<?>> TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Zawa.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<Orca>> ORCA =
            TYPES.register("orca", () -> EntityType.Builder.of(Orca::new, MobCategory.WATER_CREATURE)
                    .sized(2.0F, 1.5F)
                    .clientTrackingRange(10)
                    .build(Zawa.MOD_ID + ":orca"));

    public static void register(IEventBus modBus) {
        TYPES.register(modBus);
    }

    private OrcaOnlyEntities() {}
}
