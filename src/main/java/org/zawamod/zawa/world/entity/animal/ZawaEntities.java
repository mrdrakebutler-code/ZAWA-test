package org.zawamod.zawa.world.entity.animal;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import org.zawamod.zawa.Zawa;

/** Standalone registry: only the orca is added to Minecraft. */
public final class ZawaEntities {
    private ZawaEntities() {}
    public static final DeferredRegister<EntityType<?>> REGISTRAR =
            DeferredRegister.create(Registries.ENTITY_TYPE, Zawa.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<Orca>> ORCA = REGISTRAR.register("orca", () -> EntityType.Builder.of(Orca::new, MobCategory.WATER_CREATURE).sized(2.0F, 1.5F).clientTrackingRange(10).build(Zawa.MOD_ID + ":orca"));

    public static void register(IEventBus bus) { REGISTRAR.register(bus); }
}
