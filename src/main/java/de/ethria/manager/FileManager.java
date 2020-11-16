package de.ethria.manager;

import de.ethria.Crates;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    File file;

    File folder;

    YamlConfiguration configuration;

    public FileManager(String fileName) {
        this.folder = Crates.getInstance().getDataFolder();

        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }

        this.file = new File(folder, fileName + ".yml");

        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public File getFile() {
        return file;
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
