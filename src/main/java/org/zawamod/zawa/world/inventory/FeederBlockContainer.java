package org.zawamod.zawa.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

/** FeederBlockContainer container with synchronized inventory slots. */
public class FeederBlockContainer extends ZawaMachineMenu {
    public FeederBlockContainer(int id, Inventory player) { this(id, player, clientContainer(1)); }
    public FeederBlockContainer(int id, Inventory player, Container machine) {
        super(ZawaMenuTypes.FEEDER_BLOCK.get(), id, player, machine, 1);
    }
}
