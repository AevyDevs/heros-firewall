package net.herospvp.firewall;

import net.herospvp.firewall.database.Storage;
import net.herospvp.firewall.monitor.Events;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        for (String ip : getConfig().getStringList("grant-from")) {
            Storage.getGrantedIPS().add(ip);
        }

        getLogger().info("Granted IPS: " + Storage.getGrantedIPS().toString());

        for (Player player : getServer().getOnlinePlayers()) {
            Storage.getOnlineUUIDs().add(player.getUniqueId());
            Storage.getOnlinePlayers().add(player.getName());
        }

        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    @Override
    public void onDisable() {

    }

}
