package tw.darkk6.farmhelper.gui;

import net.minecraft.client.resources.I18n;

public class Lang {
	public static String get(String key){
		return I18n.format(key);
	}
}
