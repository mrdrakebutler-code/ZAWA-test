package org.zawamod.zawa.world.block.entity;
import net.minecraft.core.NonNullList; import net.minecraft.core.BlockPos; import net.minecraft.network.chat.Component; import net.minecraft.world.entity.player.Inventory; import net.minecraft.world.entity.player.Player; import net.minecraft.world.inventory.AbstractContainerMenu; import net.minecraft.world.item.ItemStack; import net.minecraft.world.level.block.entity.BaseContainerBlockEntity; import net.minecraft.world.level.block.state.BlockState; import net.minecraft.world.ContainerHelper;
public abstract class ZawaContainerBlockEntity extends BaseContainerBlockEntity {
 protected NonNullList<ItemStack> items; protected int progress; protected int totalProgress=200; protected boolean active;
 protected ZawaContainerBlockEntity(net.minecraft.world.level.block.entity.BlockEntityType<?> type,BlockPos pos,BlockState state,int size){super(type,pos,state);items=NonNullList.withSize(size,ItemStack.EMPTY);}
 @Override protected NonNullList<ItemStack> getItems(){return items;} @Override protected void setItems(NonNullList<ItemStack> value){items=value;}
 @Override protected Component getDefaultName(){return Component.translatable("container.zawa."+getBlockState().getBlock().builtInRegistryHolder().key().location().getPath());}
 @Override public ItemStack removeItem(int slot,int amount){ItemStack r=ContainerHelper.removeItem(items,slot,amount);if(!r.isEmpty())setChanged();return r;} @Override public ItemStack removeItemNoUpdate(int slot){ItemStack r=items.get(slot);items.set(slot,ItemStack.EMPTY);return r;}
 @Override public void setItem(int slot,ItemStack stack){items.set(slot,stack);if(stack.getCount()>getMaxStackSize())stack.setCount(getMaxStackSize());setChanged();}
 @Override public boolean stillValid(Player player){return level!=null&&level.getBlockEntity(worldPosition)==this&&player.distanceToSqr(worldPosition.getX()+.5,worldPosition.getY()+.5,worldPosition.getZ()+.5)<=64;}
 @Override public void clearContent(){items.clear();setChanged();}
 public void tickServer(){if(level==null||level.isClientSide)return;if(active&&++progress>=totalProgress){progress=0;active=false;onProcessComplete();setChanged();}}
 protected void onProcessComplete(){}
 @Override protected abstract AbstractContainerMenu createMenu(int id,Inventory inv);
 @Override public void loadAdditional(net.minecraft.nbt.CompoundTag tag,net.minecraft.core.HolderLookup.Provider regs){super.loadAdditional(tag,regs);ContainerHelper.loadAllItems(tag,items);progress=tag.getInt("Progress");totalProgress=Math.max(1,tag.getInt("TotalProgress"));active=tag.getBoolean("Active");}
 @Override protected void saveAdditional(net.minecraft.nbt.CompoundTag tag,net.minecraft.core.HolderLookup.Provider regs){super.saveAdditional(tag,regs);ContainerHelper.saveAllItems(tag,items);tag.putInt("Progress",progress);tag.putInt("TotalProgress",totalProgress);tag.putBoolean("Active",active);}
}
