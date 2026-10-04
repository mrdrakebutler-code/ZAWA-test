package org.zawamod.zawa.world.block.entity;
import net.minecraft.core.BlockPos; import net.minecraft.world.entity.player.Inventory; import net.minecraft.world.inventory.AbstractContainerMenu; import net.minecraft.world.level.block.state.BlockState; import org.zawamod.zawa.world.inventory.BugBoxMenu;
public class BugBoxBlockEntity extends ZawaContainerBlockEntity {
 protected BugBoxBlockEntity(net.minecraft.world.level.block.entity.BlockEntityType<?> type,BlockPos p,BlockState s){super(type,p,s,7);}
 public BugBoxBlockEntity(BlockPos p,BlockState s){this(ZawaBlockEntities.BUG_BOX.get(),p,s);}
 @Override protected AbstractContainerMenu createMenu(int id,Inventory inv){return new BugBoxMenu(id,inv,this,1);}
}
