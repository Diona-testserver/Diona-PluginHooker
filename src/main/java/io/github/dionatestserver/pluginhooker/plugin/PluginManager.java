package io.github.dionatestserver.pluginhooker.plugin;

import io.github.dionatestserver.pluginhooker.DionaPluginHooker;
import io.github.dionatestserver.pluginhooker.player.DionaPlayer;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class PluginManager {

    private final Set<DionaPlugin> loadedDionaPlugin = new LinkedHashSet<>();

    public void addPlugin(DionaPlugin dionaPlugin) {
        loadedDionaPlugin.add(dionaPlugin);
    }

    public void removePlugin(DionaPlugin dionaPlugin) {
        loadedDionaPlugin.remove(dionaPlugin);
        for (DionaPlayer dionaPlayer : DionaPluginHooker.getPlayerManager().getPlayers()) {
            if (dionaPlayer.getEnabledDionaPlugins().contains(dionaPlugin)) {
                dionaPlugin.onDisable(dionaPlayer);
                dionaPlayer.getEnabledDionaPlugins().remove(dionaPlugin);
            }
        }
    }

    public void switchPlugins(Player player, Set<DionaPlugin> dionaPlugins) {
        DionaPlayer dionaPlayer = DionaPluginHooker.getPlayerManager().getDionaPlayer(player);
        dionaPlayer.getEnabledDionaPlugins().forEach(dionaPlugin -> dionaPlugin.onDisable(dionaPlayer));
        dionaPlugins.forEach(dionaPlugin -> dionaPlugin.onEnable(dionaPlayer));
        dionaPlayer.setEnabledDionaPlugins(dionaPlugins);
    }
}
