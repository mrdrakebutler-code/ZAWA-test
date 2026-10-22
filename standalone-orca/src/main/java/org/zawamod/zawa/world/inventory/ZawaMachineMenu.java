package org.zawamod.zawa.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/** Slot-backed machine menu; uses the actual block inventory server-side. */
public abstract class ZawaMachineMenu extends AbstractContainerMenu {
    private final Container machine;
    protected ZawaMachineMenu(MenuType<?> type, int id, Inventory player, Container machine, int size) {
        super(type, id);
        this.machine = machine;
        checkContainerSize(machine, size);
        machine.startOpen(player.player);
        for (int i = 0; i < size; i++) addSlot(new Slot(machine, i, 8 + (i % 9) * 18, 18 + (i / 9) * 18));
        for (int row = 0; row < 3; row++) for (int col = 0; col < 9; col++)
            addSlot(new Slot(player, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
        for (int col = 0; col < 9; col++) addSlot(new Slot(player, col, 8 + col * 18, 142));
    }
    protected static Container clientContainer(int slots) { return new SimpleContainer(slots); }
    @Override public boolean stillValid(Player player) { return machine.stillValid(player); }
    @Override public void removed(Player player) { super.removed(player); machine.stopOpen(player); }
    @Override public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();
        int machineSlots = machine.getContainerSize();
        if (!moveItemStackTo(stack, index < machineSlots ? machineSlots : 0,
                index < machineSlots ? slots.size() : machineSlots, false)) return ItemStack.EMPTY;
        if (stack.isEmpty()) slot.set(ItemStack.EMPTY); else slot.setChanged();
        return original;
    }
}
