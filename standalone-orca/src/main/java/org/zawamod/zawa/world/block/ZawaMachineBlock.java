package org.zawamod.zawa.world.block;

import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import org.zawamod.zawa.world.block.entity.ZawaContainerBlockEntity;

public class ZawaMachineBlock extends BaseEntityBlock {
    private final Supplier<? extends BlockEntityType<?>> type;

    public ZawaMachineBlock(BlockBehaviour.Properties properties, Supplier<? extends BlockEntityType<?>> type) {
        super(properties);
        this.type = type;
    }

    @Override public MapCodec<? extends BaseEntityBlock> codec() { return simpleCodec(p -> new ZawaMachineBlock(p, type)); }

    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        BlockEntityType<?> t = type.get();
        if (t == null) return null;
        return t.create(pos, state);
    }

    @Override public <T extends BlockEntity> BlockEntityTicker<T> getTicker(net.minecraft.world.level.Level level, BlockState state, BlockEntityType<T> requested) {
        return level.isClientSide ? null : (requested == type.get() ? (l,p,s,be) -> { if (be instanceof ZawaContainerBlockEntity z) z.tickServer(); } : null);
    }

    @Override public RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }

    @Override public InteractionResult useWithoutItem(BlockState state, net.minecraft.world.level.Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof MenuProvider provider) serverPlayer.openMenu(provider);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

}
