package tw.darkk6.farmhelper;

import java.util.Vector;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tw.darkk6.farmhelper.config.Reference;
import tw.darkk6.farmhelper.gui.Log;
import tw.darkk6.farmhelper.plant.IPlants;
import tw.darkk6.farmhelper.plant.PlantManager;

public class EventHandler {
	
	private boolean rightClickEventLock=false;
	
	//可以用這個事件訂閱設定檔改變事件
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
		if (Reference.MODID.equals(e.getModID())) {
			Reference.config.update();
			PlantManager.update();
		}
	}
	/*
	//這個版本的限制就是必須是滑鼠右鍵點選，有改過控制可能就不行
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onMouseRightClick(MouseEvent e){
		//必須是點右鍵，並且按下的狀態才觸發 button 1=右鍵
		if(!(e.getButton()==1 && e.isButtonstate())) return;
		
		//取得十字準心面對的那一格，如果沒東西，或者點選的不是 Block , 則結束
		Minecraft mc=Minecraft.getMinecraft();
		EntityPlayerSP player=mc.thePlayer;
		if(player==null) return;//避免出錯，檢查一下
		RayTraceResult pointed=player.rayTrace(mc.playerController.getBlockReachDistance(), 1.0F);
		if( pointed==null || pointed.typeOfHit!=RayTraceResult.Type.BLOCK) return;
		
		//應該沒問題可以觸發 onBlockRightClick 了
		this.onBlockRightClick(
				new RightClickBlockEventWarpper(
						pointed.getBlockPos(),
						pointed.sideHit
					)
			);
	}
	*/
	// 1.9.4 移除了 PlayerInteractEvent 的 Action enum , 直接指定需要的 Event
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerInteract(RightClickBlock evt){
		//盡量減少重複觸發的次數
		if(!rightClickEventLock){
			rightClickEventLock=true;
			this.onBlockRightClick(new RightClickBlockEventWarpper(evt));
			rightClickEventLock=false;
		}
	}
	
	
	private void onBlockRightClick(RightClickBlockEventWarpper e){
		//這邊只接收 RightClickBlockEvent , 因此可以不用判斷了
		// Lagecy : if(e.action!=PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;
		//if(!e.isRightClickEvent()) return;
		Minecraft mc=Minecraft.getMinecraft();
		EntityPlayerSP player=mc.thePlayer;
		if(player==null) return;//避免出錯，檢查一下
		
		// Main慣用手拿的東西 , off 副手拿的東西 - 1.9
		ItemStack stackMain=player.getHeldItem(EnumHand.MAIN_HAND);
		ItemStack stackOff=player.getHeldItem(EnumHand.OFF_HAND);
		ItemStack stack=null;//實際要種植的東西
		EnumHand whichHand=null;
		
		//1.9 - 先確認主手拿的東西是否為可種植且設定檔中有啟用 , 如果可以就讓 stack 指向 stackMain
		//      反之，就偵測副手 , 並讓 stack 指向 stackOff 
		if(PlantManager.isPlantEnabled(stackMain) && PlantManager.canPlantAt(player.worldObj, e.pos, stackMain)){
			stack=stackMain;
			whichHand=EnumHand.MAIN_HAND;
		}else if(PlantManager.isPlantEnabled(stackOff) && PlantManager.canPlantAt(player.worldObj, e.pos, stackOff)){
			stack=stackOff;
			whichHand=EnumHand.OFF_HAND;
		}else return; //主副手都不是要重的東西，就結束
		
		//當要種植副手的東西時，要檢查，主手的物品是不是只是被禁用而已
		if(whichHand==EnumHand.OFF_HAND && stackMain!=null){
			//如果 MainHand 的 Item 可以被種在這一格，就代表只是禁用而已，這時候要提示訊息
			if(PlantManager.canPlantAt(player.worldObj, e.pos, stackMain)){
				Log.showMainHandMustNull();
				return;
			}
		}
		
		int count=stack.stackSize;//手上道具的數量
		if(count == 1) return;//如果手上道具只有一個，就不做任何搜尋
		Vector<BlockPos> list=null;
		IPlants plant=PlantManager.get(stack.getItem());
		// 要先判斷該格是否能種植 , 不能種植就不繼續
		if(!plant.canPlantAt(player.worldObj, e.pos)) return;
		list = plant.doBFSSearch(player.worldObj, e.pos, count, Reference.config.fasterBFS);
		
		//==== BFS 搜尋完成，開始種植 =====
		//數量內的點都找完了 , 將有效數量的種下去，跳過 0 是因為  0=作用的那一格
		for(int i=1;i<count && i<list.size();i++){
			//這裡不能使用 itemStack.onItemUse , 那個不會與 Server 通訊 , 物品只是看起來有而已
			// 1.9 , 改用 processRightClickBlock , 1.8.8=>onPlayerRightClick
			mc.playerController.processRightClickBlock(
					player,
					(WorldClient)(player.worldObj), 
					stack, 
					list.get(i),
					e.face,
					player.getPositionVector(),
					whichHand
				);
		}
	}
}
