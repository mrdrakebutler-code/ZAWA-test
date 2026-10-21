package org.zawamod.zawa.world.entity.animal;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.zawamod.zawa.Zawa;

/** Original orca vocalizations supplied with ZAWA's assets. */
public final class OrcaSounds {
    private static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(Registries.SOUND_EVENT, Zawa.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> AMBIENT = register("orca_ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> HURT = register("orca_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ATTACKING = register("orca_attacking");
    public static final DeferredHolder<SoundEvent, SoundEvent> BROADCAST = register("orca_broadcast");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(
                ResourceLocation.fromNamespaceAndPath(Zawa.MOD_ID, name)));
    }

    public static void register(IEventBus bus) { SOUNDS.register(bus); }

    private OrcaSounds() {}
}
