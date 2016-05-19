package tw.darkk6.farmhelper.config;

import java.io.File;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import tw.darkk6.farmhelper.gui.Lang;


public class ConfigMgr {
	private String nameWheat = (new ItemStack(Item.getByNameOrId(Reference.WHEAT))).getDisplayName();
	private String namePumpkin = (new ItemStack(Item.getByNameOrId(Reference.PUMPKIN))).getDisplayName();
	private String nameMelon = (new ItemStack(Item.getByNameOrId(Reference.MELON))).getDisplayName();
	private String nameCarrot = (new ItemStack(Item.getByNameOrId(Reference.CARROT))).getDisplayName();
	private String namePotato = (new ItemStack(Item.getByNameOrId(Reference.POTATO))).getDisplayName();
	private String nameWart = (new ItemStack(Item.getByNameOrId(Reference.WART))).getDisplayName();
	private String nameBeet = (new ItemStack(Item.getByNameOrId(Reference.BEETROOT))).getDisplayName();
	
	private Configuration cfg = null;

	public boolean wheat=true, pumpkin=true, melon=true, carrot=true, potato=true, wart=true, beetroot=true;
	public boolean fasterBFS=false;

	public ConfigMgr(File file) {
		cfg = new Configuration(file);
		reload();
	}

	public Configuration getConfig() {
		return cfg;
	}

	public void update() {
		cfg.save();
		reload();
	}
	
	public void save() {
		cfg.save();
	}

	private void reload() {
		cfg.load();
		
		cfg.setCategoryComment(Configuration.CATEGORY_GENERAL,Lang.get("farmhelper.setting.general.comment"));
		
		fasterBFS = cfg.getBoolean(Lang.get("farmhelper.setting.fasterbfs"), Configuration.CATEGORY_GENERAL, fasterBFS, Lang.get("farmhelper.setting.fasterbfs.comment"));
		wheat = cfg.getBoolean(Lang.get("farmhelper.setting.wheatseed"), Configuration.CATEGORY_GENERAL, wheat, nameWheat);
		pumpkin = cfg.getBoolean(Lang.get("farmhelper.setting.pumpkin"), Configuration.CATEGORY_GENERAL, pumpkin, namePumpkin);
		melon = cfg.getBoolean(Lang.get("farmhelper.setting.melon"), Configuration.CATEGORY_GENERAL, melon, nameMelon);
		carrot = cfg.getBoolean(Lang.get("farmhelper.setting.carrot"), Configuration.CATEGORY_GENERAL, carrot, nameCarrot);
		potato = cfg.getBoolean(Lang.get("farmhelper.setting.potato"), Configuration.CATEGORY_GENERAL, potato, namePotato);
		wart = cfg.getBoolean(Lang.get("farmhelper.setting.netherwart"), Configuration.CATEGORY_GENERAL, wart, nameWart);
		beetroot = cfg.getBoolean(Lang.get("farmhelper.setting.beetroot"), Configuration.CATEGORY_GENERAL, beetroot, nameBeet);

		if (cfg.hasChanged())
			cfg.save();
	}
}
