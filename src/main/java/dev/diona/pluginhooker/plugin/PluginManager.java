package dev.diona.pluginhooker.plugin;

import dev.diona.pluginhooker.player.DionaPlayer;
import dev.diona.pluginhooker.PluginHooker;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class PluginManager {

    private final Set<Plugin> pluginsToHook = new LinkedHashSet<>();

    public void addPlugin(Plugin plugin) {
        pluginsToHook.add(plugin);
    }

    public void removePlugin(Plugin plugin) {
        if (!pluginsToHook.contains(plugin)) {
            Bukkit.getLogger().warning("Warning: " + plugin.getName() + " is not in the plugin hook list! Ignored!");
            return;
        }
        pluginsToHook.remove(plugin);
        for (DionaPlayer dionaPlayer : PluginHooker.getPlayerManager().getPlayers().values()) {
            dionaPlayer.disablePlugin(plugin);
        }
    }

    public boolean isPluginHooked(Plugin plugin) {
        return pluginsToHook.contains(plugin);
    }

}
