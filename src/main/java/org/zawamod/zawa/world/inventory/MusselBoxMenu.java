package org.zawamod.zawa.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

/** MusselBoxMenu container with synchronized inventory slots. */
public class MusselBoxMenu extends ZawaMachineMenu {
    public MusselBoxMenu(int id, Inventory player) { this(id, player, clientContainer(7), 1); }
    public MusselBoxMenu(int id, Inventory player, Container machine, int pages) {
        super(ZawaMenuTypes.MUSSEL_BOX.get(), id, player, machine, 7);
    }
}
