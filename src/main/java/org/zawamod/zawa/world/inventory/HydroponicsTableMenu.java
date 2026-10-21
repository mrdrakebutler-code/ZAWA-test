package org.zawamod.zawa.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

/** HydroponicsTableMenu container with synchronized inventory slots. */
public class HydroponicsTableMenu extends ZawaMachineMenu {
    public HydroponicsTableMenu(int id, Inventory player) { this(id, player, clientContainer(3)); }
    public HydroponicsTableMenu(int id, Inventory player, Container machine) {
        super(ZawaMenuTypes.HYDROPONICS_TABLE.get(), id, player, machine, 3);
    }
}
