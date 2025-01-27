package cofh.core.inventory.container;

import cofh.core.tileentity.TileCoFH;
import cofh.lib.inventory.container.ContainerCoFH;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TileContainer extends ContainerCoFH {

    protected final TileCoFH baseTile;

    public TileContainer(@Nullable ContainerType<?> type, int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player) {

        super(type, windowId, inventory, player);
        TileEntity tile = world.getBlockEntity(pos);
        baseTile = tile instanceof TileCoFH ? (TileCoFH) tile : null;

        if (baseTile != null) {
            baseTile.addPlayerUsing();
        }
    }

    @Override
    protected int getMergeableSlotCount() {

        return baseTile == null ? 0 : baseTile.invSize();
    }

    @Override
    public boolean stillValid(PlayerEntity player) {

        return baseTile == null || baseTile.playerWithinDistance(player, 64D);
    }

    @Override
    public void broadcastChanges() {

        super.broadcastChanges();
        if (baseTile == null) {
            return;
        }
        for (IContainerListener listener : this.containerListeners) {
            baseTile.sendGuiNetworkData(this, listener);
        }
    }

    @Override
    public void setData(int i, int j) {

        super.setData(i, j);

        if (baseTile == null) {
            return;
        }
        baseTile.receiveGuiNetworkData(i, j);
    }

    @Override
    public void removed(PlayerEntity player) {

        if (baseTile != null) {
            baseTile.removePlayerUsing();
        }
        super.removed(player);
    }

}
