package tw.darkk6.farmhelper.plant;

import java.util.HashMap;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tw.darkk6.farmhelper.config.Reference;

public class PlantManager {
//============= 以後有新增植物要寫在這裡 =============
	public static HashMap<Item,IPlants> immutableMap;
	public static HashMap<Item,IPlants> map;
	static{
		immutableMap=new HashMap<Item, IPlants>();
		immutableMap.put(Items.beetroot_seeds, new BeetrootSeed());
		immutableMap.put(Items.carrot, new Carrot());
		immutableMap.put(Items.melon_seeds, new MelonSeed());
		immutableMap.put(Items.nether_wart, new NetherWart());
		immutableMap.put(Items.potato, new Potato());
		immutableMap.put(Items.pumpkin_seeds, new PumpkinSeed());
		immutableMap.put(Items.wheat_seeds, new WheatSeed());
	}
	public static void update(){
		if(Reference.config==null) return;
		if(map==null) map=new HashMap<Item, IPlants>();
		else map.clear();
		
		if(Reference.config.beetroot)
			map.put(Items.beetroot_seeds, immutableMap.get(Items.beetroot_seeds));
		if(Reference.config.carrot)
			map.put(Items.carrot, immutableMap.get(Items.carrot));
		if(Reference.config.melon)
			map.put(Items.melon_seeds, immutableMap.get(Items.melon_seeds));
		if(Reference.config.wart)
			map.put(Items.nether_wart, immutableMap.get(Items.nether_wart));
		if(Reference.config.potato)
			map.put(Items.potato, immutableMap.get(Items.potato));
		if(Reference.config.pumpkin)
			map.put(Items.pumpkin_seeds, immutableMap.get(Items.pumpkin_seeds));
		if(Reference.config.wheat)
			map.put(Items.wheat_seeds, immutableMap.get(Items.wheat_seeds));
	}
	
	public static IPlants get(Item item){
		return map.get(item);
	}
	
	// 該物品是否為此 Mod 可用之種植物(不一定有啟用)
	public static boolean isPlantableCrop(ItemStack stack){
		if(stack==null) return false;
		return immutableMap.containsKey(stack.getItem());
	}
	
	// 取得該物品是否在設定檔中有開啟幫忙種植
	public static boolean isPlantEnabled(ItemStack stack){
		if(stack==null) return false;
		return map.containsKey(stack.getItem());
	}
	// 取得該物品是否可以種植，這裡不論設定檔是否有啟用
	public static boolean canPlantAt(World world,BlockPos pos,ItemStack stack){
		if(stack==null) return false;
		if(!immutableMap.containsKey(stack.getItem())) return false;
		IPlants plant=immutableMap.get(stack.getItem());
		return plant.canPlantAt(world, pos);
	}
}
