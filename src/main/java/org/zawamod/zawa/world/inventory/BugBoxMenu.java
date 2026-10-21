package org.zawamod.zawa.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

/** BugBoxMenu container with synchronized inventory slots. */
public class BugBoxMenu extends ZawaMachineMenu {
    public BugBoxMenu(int id, Inventory player) { this(id, player, clientContainer(7), 1); }
    public BugBoxMenu(int id, Inventory player, Container machine, int pages) {
        super(ZawaMenuTypes.BUG_BOX.get(), id, player, machine, 7);
    }
}
