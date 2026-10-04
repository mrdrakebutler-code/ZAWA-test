package org.zawamod.zawa.world.entity.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.zawamod.zawa.Zawa;

public final class ZawaEnrichmentEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Zawa.MOD_ID);

    private static DeferredHolder<EntityType<?>, EntityType<EnrichmentObjectEntity>> register(String name) {
        return ENTITIES.register(name, () -> EntityType.Builder
                .<EnrichmentObjectEntity>of(EnrichmentObjectEntity::new, MobCategory.MISC)
                .sized(.65F, .65F).clientTrackingRange(10).updateInterval(3)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Zawa.MOD_ID, name))));
    }

    public static final DeferredHolder<EntityType<?>, EntityType<EnrichmentObjectEntity>> BOOMER_BALL = register("boomer_ball");
    public static final DeferredHolder<EntityType<?>, EntityType<EnrichmentObjectEntity>> SCENTED_BALL = register("scented_ball");
    public static final DeferredHolder<EntityType<?>, EntityType<EnrichmentObjectEntity>> APPLE_ICE_TREAT = register("apple_ice_treat");
    public static final DeferredHolder<EntityType<?>, EntityType<EnrichmentObjectEntity>> BEEF_ICE_TREAT = register("beef_ice_treat");

    public static void register(IEventBus bus) { ENTITIES.register(bus); }
    private ZawaEnrichmentEntities() {}
}
