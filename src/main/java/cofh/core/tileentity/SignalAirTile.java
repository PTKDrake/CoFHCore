package cofh.core.tileentity;

import cofh.lib.util.helpers.MathHelper;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import static cofh.lib.util.references.CoreReferences.SIGNAL_AIR_TILE;

public class SignalAirTile extends TileEntity implements ITickableTileEntity {

    protected int duration = 200;
    protected int power = 15;

    public SignalAirTile() {

        super(SIGNAL_AIR_TILE);
    }

    @Override
    public void tick() {

        if (level == null) {
            return;
        }
        if (--duration <= 0) {
            this.level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
            this.level.removeBlockEntity(this.worldPosition);
            this.setRemoved();
        }
    }

    public int getDuration() {

        return duration;
    }

    public int getPower() {

        return power;
    }

    public void setDuration(int duration) {

        this.duration = duration;
    }

    public void setPower(int power) {

        this.power = MathHelper.clamp(power, 0, 15);
    }

}
