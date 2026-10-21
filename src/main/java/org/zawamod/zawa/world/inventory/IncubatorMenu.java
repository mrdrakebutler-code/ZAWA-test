package org.zawamod.zawa.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

/** IncubatorMenu container with synchronized inventory slots. */
public class IncubatorMenu extends ZawaMachineMenu {
    public IncubatorMenu(int id, Inventory player) { this(id, player, clientContainer(3), new net.minecraft.world.inventory.SimpleContainerData(2)); }
    public IncubatorMenu(int id, Inventory player, Container machine, net.minecraft.world.inventory.ContainerData data) {
        super(ZawaMenuTypes.INCUBATOR.get(), id, player, machine, 3);
        addDataSlots(data);
    }
}
