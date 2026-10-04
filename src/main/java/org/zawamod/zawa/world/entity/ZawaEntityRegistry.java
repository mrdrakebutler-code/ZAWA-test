package org.zawamod.zawa.world.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * NeoForge replacement for the original Forge ZawaEntityRegistry.
 *
 * This class intentionally preserves the original builder shape while using
 * NeoForge DeferredRegister/DeferredHolder types. Individual ZAWA entity
 * classes are being recovered from the supplied 1.20.1 bytecode separately.
 */
public final class ZawaEntityRegistry {
    private final String modId;
    private final DeferredRegister<EntityType<?>> entityRegistrar;
    private final Map<DeferredHolder<EntityType<?>, ? extends EntityType<?>>, Supplier<AttributeSupplier.Builder>> attributes = new LinkedHashMap<>();

    public ZawaEntityRegistry(String modId) {
        this.modId = modId;
        this.entityRegistrar = DeferredRegister.create(net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE, modId);
    }

    public void register(IEventBus modBus) {
        entityRegistrar.register(modBus);
    }

    public <T extends Mob> MobBuilder<T> builder(EntityType.EntityFactory<T> factory, MobCategory category) {
        return new MobBuilder<>(factory, category);
    }

    public <T extends Entity> EntityBuilder<T> entityBuilder(EntityType.EntityFactory<T> factory, MobCategory category) {
        return new EntityBuilder<>(factory, category);
    }

    public class EntityBuilder<T extends Entity> {
        private final EntityType.EntityFactory<T> factory;
        private final MobCategory category;
        private Consumer<EntityType.Builder<T>> builderConsumer;
        private Supplier<AttributeSupplier.Builder> attributeSupplier;

        private EntityBuilder(EntityType.EntityFactory<T> factory, MobCategory category) {
            this.factory = factory;
            this.category = category;
        }

        public EntityBuilder<T> data(Consumer<EntityType.Builder<T>> consumer) {
            this.builderConsumer = consumer;
            return this;
        }

        public EntityBuilder<T> attributes(Supplier<AttributeSupplier.Builder> supplier) {
            this.attributeSupplier = supplier;
            return this;
        }

        public DeferredHolder<EntityType<?>, EntityType<T>> build(String id) {
            DeferredHolder<EntityType<?>, EntityType<T>> holder = entityRegistrar.register(id, () -> {
                EntityType.Builder<T> builder = EntityType.Builder.of(factory, category);
                if (builderConsumer != null) builderConsumer.accept(builder);
                return builder.build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(modId, id)));
            });
            if (attributeSupplier != null) attributes.put(holder, attributeSupplier);
            return holder;
        }
    }

    public final class MobBuilder<T extends Mob> extends EntityBuilder<T> {
        private MobBuilder(EntityType.EntityFactory<T> factory, MobCategory category) {
            super(factory, category);
        }
    }

    /** Register recovered mob default attributes on the NeoForge mod event bus. */
    public void registerAttributes(EntityAttributeCreationEvent event) {
        attributes.forEach((holder, supplier) -> event.put(holder, supplier.get().build()));
    }

}
