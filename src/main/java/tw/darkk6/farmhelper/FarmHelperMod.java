package tw.darkk6.farmhelper;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tw.darkk6.farmhelper.config.ConfigMgr;
import tw.darkk6.farmhelper.config.Reference;
import tw.darkk6.farmhelper.plant.PlantManager;

@Mod(modid = Reference.MODID, version = Reference.MOD_VER, name = Reference.MOD_NAME, guiFactory = Reference.GUI_FACTORY, clientSideOnly = true, acceptedMinecraftVersions = Reference.MC_VER , dependencies=Reference.FORGE_VER)
public class FarmHelperMod {
	// GuiScreen
	
	@Instance(Reference.MODID)
	public static FarmHelperMod mod;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		Reference.config = new ConfigMgr(e.getSuggestedConfigurationFile());
		PlantManager.update();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
}
