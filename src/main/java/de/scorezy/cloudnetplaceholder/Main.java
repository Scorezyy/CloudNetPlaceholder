package de.scorezy.cloudnetplaceholder;

import eu.cloudnetservice.driver.inject.InjectionLayer;
import eu.cloudnetservice.driver.registry.ServiceRegistry;
import eu.cloudnetservice.driver.service.ServiceId;
import eu.cloudnetservice.driver.service.ServiceInfoSnapshot;
import eu.cloudnetservice.modules.bridge.BridgeServiceHelper;
import eu.cloudnetservice.modules.bridge.player.PlayerManager;
import eu.cloudnetservice.wrapper.configuration.WrapperConfiguration;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new CloudNetExpansion().register();
            getLogger().info("CloudNet PlaceholderAPI Erweiterung wurde registriert!");
        } else {
            getLogger().warning("PlaceholderAPI nicht gefunden! Erweiterung wird nicht geladen.");
        }
    }

    public class CloudNetExpansion extends PlaceholderExpansion {

        @Override
        public boolean persist() {
            return true;
        }

        @Override
        public boolean canRegister() {
            return true;
        }

        @Override
        public String getAuthor() {
            return "scorezy";
        }

        @Override
        public String getIdentifier() {
            return "cloudnet";
        }

        @Override
        public String getVersion() {
            return "1.0";
        }

        @Override
        public String onPlaceholderRequest(Player player, String identifier) {
            if (identifier.equals("servername")) {
                ServiceId serviceId = InjectionLayer.ext().instance(WrapperConfiguration.class)
                    .serviceInfoSnapshot().serviceId();
              String taskName = serviceId.taskName();
              int serviceNumber = serviceId.taskServiceId();

              return taskName + "-" + serviceNumber;
            }


            if (identifier.equals("playercount")) {
                ServiceRegistry serviceRegistry = InjectionLayer.ext().instance(ServiceRegistry.class);
                PlayerManager playerManager = serviceRegistry.firstProvider(PlayerManager.class);
                int onlinePlayerCount = playerManager.onlineCount();
                return String.valueOf(onlinePlayerCount);
            }

            return null;
        }
    }
}
