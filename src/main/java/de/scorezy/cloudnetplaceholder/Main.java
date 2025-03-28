package de.scorezy.cloudnetplaceholder;

import de.dytanic.cloudnet.driver.service.ServiceId;
import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import de.dytanic.cloudnet.wrapper.Wrapper;
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
                ServiceId serviceId = Wrapper.getInstance().getServiceId();
                if (serviceId != null) {
                    String taskName = serviceId.getTaskName();
                    int serviceNumber = serviceId.getTaskServiceId();

                    return taskName + "-" + serviceNumber;
                }
                return "Unknown";
            }


            if (identifier.equals("playercount")) {
                int onlinePlayerCount = BridgePlayerManager.getInstance().getOnlineCount();
                return String.valueOf(onlinePlayerCount);
            }

            return null;
        }
    }
}
