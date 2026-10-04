package org.zawamod.zawa.world.block.entity;
import net.minecraft.core.BlockPos; import net.minecraft.world.entity.player.Inventory; import net.minecraft.world.inventory.AbstractContainerMenu; import net.minecraft.world.level.block.state.BlockState; import org.zawamod.zawa.world.inventory.MusselBoxMenu;
public class MusselBoxBlockEntity extends BugBoxBlockEntity { public MusselBoxBlockEntity(BlockPos p,BlockState s){super(ZawaBlockEntities.MUSSEL_BOX.get(),p,s);} @Override protected AbstractContainerMenu createMenu(int id,Inventory inv){return new MusselBoxMenu(id,inv,this,1);} }
