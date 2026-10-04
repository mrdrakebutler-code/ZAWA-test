package org.zawamod.zawa.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;

/** Reconstructed client encyclopedia screen used by the ZAWA Data Book. */
public class DataBookScreen extends Screen {
    private final Entity entity;
    private final Level level;

    public DataBookScreen(Entity entity, Level level) {
        super(Component.translatable("item.zawa.data_book"));
        this.entity = entity;
        this.level = level;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        int left = (this.width - 300) / 2;
        int top = (this.height - 180) / 2;
        graphics.fill(left, top, left + 300, top + 180, 0xCC202020);
        graphics.drawString(this.font, this.title, left + 12, top + 10, 0xFFFFFF);
        if (entity instanceof ZawaBaseEntity zawa) {
            graphics.drawString(this.font, Component.literal("Variant: " + zawa.getVariant()), left + 12, top + 42, 0xE0E0E0);
            graphics.drawString(this.font, Component.literal("Gender: " + zawa.getGender().getLocalizedName().getString()), left + 12, top + 58, 0xE0E0E0);
            graphics.drawString(this.font, Component.literal("Hunger: " + zawa.getHunger().getValue()), left + 12, top + 74, 0xE0E0E0);
            graphics.drawString(this.font, Component.literal("Thirst: " + zawa.getThirst().getValue()), left + 12, top + 90, 0xE0E0E0);
            graphics.drawString(this.font, Component.literal("Enrichment: " + zawa.getEnrichment().getValue()), left + 12, top + 106, 0xE0E0E0);
        } else if (entity != null) {
            graphics.drawString(this.font, entity.getDisplayName(), left + 12, top + 45, 0xE0E0E0);
        } else {
            graphics.drawString(this.font, Component.literal("ZAWA Encyclopedia"), left + 12, top + 45, 0xE0E0E0);
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
