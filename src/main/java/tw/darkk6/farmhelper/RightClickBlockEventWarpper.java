package tw.darkk6.farmhelper;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

public class RightClickBlockEventWarpper {
	public PlayerInteractEvent rawEvent;
	public BlockPos pos;
	public EnumFacing face;//若不知道可以是 null
	
	private boolean isDummy=false;
	
	public RightClickBlockEventWarpper(PlayerInteractEvent event){
		this.rawEvent=event;
		this.pos=event.getPos();
		this.face=event.getFace();
	}
	public RightClickBlockEventWarpper(BlockPos pos,EnumFacing face){
		this.rawEvent=null;
		this.pos = pos;
		this.face = face;
		isDummy=true;
	}

	public boolean isRightClickEvent(){
		return rawEvent instanceof RightClickBlock ||
				( rawEvent==null && isDummy )
				;
	}
}
