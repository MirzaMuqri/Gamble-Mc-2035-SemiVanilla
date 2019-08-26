package xyz.mukri.gamble.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import xyz.mukri.gamble.Core;

public class TrackFile {
	
	public File file;
	public FileConfiguration config;
	
	public TrackFile() {
		file = new File(Core.getInstance().getDataFolder(), "track.yml");
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public boolean isFileExists() {
		return file.exists();
	}
	
	public void createNewFile() {
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		
		try {
			file.createNewFile();
			
			config.set("win-times", 0);
			config.set("lose-times", 0);
			config.set("color.red", 0);
			config.set("color.black", 0);
			config.set("color.green", 0);
			
			save();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getWinning() {
		return config.getInt("win-times");
	}
	
	public int getLosing() {
		return config.getInt("lose-times");
	}
	
	public int getColor(String color) {
		return config.getInt("color." + color);
	}
	
	
	public void addWinning() {
		config.set("win-times", getWinning() + 1);
	}
	
	public void addLosing() {
		config.set("lose-times", getLosing() + 1);
	}
	
	public void addColor(String color) {
		config.set("color." + color, getColor(color));
	}

}
