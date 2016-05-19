package tw.darkk6.farmhelper.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import tw.darkk6.farmhelper.config.Reference;

public class FarmHelperGuiConfig extends GuiConfig {
	public FarmHelperGuiConfig(GuiScreen parent) {
		super(parent,
			new ConfigElement(
				Reference.config.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
				Reference.MODID,
				false,//需要重新進入世界 ?
				false,//需要重新啟動 MC ?
				Lang.get("farmhelper.setting.title")//標題
			);
		// 設定標題 2 顯示文字
		// GuiConfig.getAbridgedConfigPath() 可以把檔案路徑改成  .minecraft/ 底下的對應路徑呈現
		this.titleLine2 = GuiConfig.getAbridgedConfigPath(Reference.config.getConfig().toString());
	}
	
	
	/**
	 * 如果 Override ，可以偵測 button id ，當儲存按鈕按下時更新資料 , 但要先自己知道按鈕 id 是什麼
	 * 或者建議使用 @@SubscribeEvent ConfigChangedEvent.OnConfigChangedEvent 事件來判斷
	*/
	@Override
	protected void actionPerformed(GuiButton button){
		super.actionPerformed(button);
		// button.id = 2000 ===> "完成"
	}
}
