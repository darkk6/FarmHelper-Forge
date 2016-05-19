package tw.darkk6.farmhelper;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class InteractEventWarpper {
	public PlayerInteractEvent.Action action;
	public BlockPos pos;
	public EnumFacing face;//若不知道可以是 null
	
	public InteractEventWarpper(PlayerInteractEvent event){
		this.action=event.getAction();
		this.pos=event.getPos();
		this.face=event.getFace();
	}
	public InteractEventWarpper(PlayerInteractEvent.Action action,BlockPos pos,EnumFacing face){
		this.action = action;
		this.pos = pos;
		this.face = face;
	}
	
}
