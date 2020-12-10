package net.herospvp.firewall.monitor;

import net.herospvp.firewall.database.Storage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class Events implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void on(AsyncPlayerPreLoginEvent event) {
        String playerName = event.getName();
        UUID uuid = event.getUniqueId();

        if (!Storage.getOnlinePlayers().contains(playerName) && !Storage.getOnlineUUIDs().contains(uuid)) {
            return;
        }

        event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
        event.setKickMessage(ChatColor.RED + "https://www.youtube.com/watch?v=dQw4w9WgXcQ");

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("firewall"))
                player.sendMessage(ChatColor.RED + playerName +
                        " ha provato ad entrare ma c'era un altro player con lo stesso nome/UUID online.");
        }

        Bukkit.getLogger().warning(playerName + " ha provato ad entrare ma c'era un altro player con lo stesso nome/UUID online.");

    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void on(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName(),
                address = event.getRealAddress().toString(),
                host = event.getRealAddress().getHostAddress();

        if (Storage.getGrantedIPS().contains(address) && Storage.getGrantedIPS().contains(host)) {
            Storage.getOnlinePlayers().add(playerName);
            Storage.getOnlineUUIDs().add(player.getUniqueId());
            return;
        }

        event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        event.setKickMessage(ChatColor.RED + "https://www.youtube.com/watch?v=dQw4w9WgXcQ");

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.hasPermission("firewall"))
                player1.sendMessage(ChatColor.RED + playerName +
                        " ha provato ad entrare dalle porte, e' stato Rick-Rolled.");
        }

        Bukkit.getLogger().warning(playerName + " ha provato ad entrare dalle porte, e' stato Rick-Rolled.");

    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Storage.getOnlinePlayers().remove(player.getName());
        Storage.getOnlineUUIDs().remove(player.getUniqueId());
    }

}
