package org.zawamod.zawa.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ZawaMachineScreen extends AbstractContainerScreen<AbstractContainerMenu> {
    public ZawaMachineScreen(AbstractContainerMenu menu, Inventory inventory, Component title) { super(menu, inventory, title); this.imageWidth=176; this.imageHeight=166; }
    @Override protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int left=leftPos, top=topPos; graphics.fill(left,top,left+imageWidth,top+imageHeight,0xFF2B2B2B); graphics.fill(left+5,top+5,left+171,top+78,0xFF454545);
    }
    @Override protected void renderLabels(GuiGraphics graphics,int mouseX,int mouseY){ graphics.drawString(font,title,8,6,0xFFFFFF,false); graphics.drawString(font,playerInventoryTitle,8,73,0xFFFFFF,false); }
}
