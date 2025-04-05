package pink.rat.Powers;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PowerManager implements Listener {

    private final List<Supplier<Power>> externalPowers = new ArrayList<>();
    private final HashMap<UUID, Power> playerPowers = new HashMap<>();
   
    
    @SafeVarargs
    //After plethora of confirmation this is infact type safe.
    public final void addPower(Supplier<Power>... power) {
        for (Supplier<Power> p : power) externalPowers.add(p);
    }

    public final void addPower(Supplier<Power> power) {
        externalPowers.add(power);
    }
    
    public final Power getPower(Player player) {
        return playerPowers.get(player.getUniqueId());
    }

    public final void printPowerDescription(Player player, Power power) {
        player.sendMessage(ChatColor.ITALIC + " " + ChatColor.GRAY + "Power: " + power.getFancyName());
        for (String description : power.getDescriptionName()) {
            player.sendMessage(" " + ChatColor.GRAY + description);
        }
    }

    public void setPower(Player player, Power power) {
        UUID uuid = player.getUniqueId();
        Power currentPower = playerPowers.get(uuid);

        if (currentPower != null) {
            currentPower.powerDeactivate(player);
            HandlerList.unregisterAll(currentPower); 
        }

        power.powerActivate(player);
        Application.getInstance().getServer().getPluginManager().registerEvents(power, Application.getInstance());
        playerPowers.put(uuid, power);

        printPowerDescription(player, power);
    }

    public boolean setPower(Player player, String powerName) {
    	PowerType p = PowerType.fromString(powerName);
    	if (p != null) {
    		setPower(player, p.get());
    		return true;
    	}
        for (Power power : externalPowers) {
            if (power.getName().equalsIgnoreCase(powerName)) {
                try {
                    Constructor<?> constructor = power.getClass().getDeclaredConstructor();
                    Power newPower = (Power) constructor.newInstance();
                    setPower(player, newPower);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        setPower(player, new DummyPower());
        return false;
    }

    public void removePlayerPower(Player player) {
        UUID uuid = player.getUniqueId();
        Power currentPower = playerPowers.get(uuid);

        if (currentPower != null) {
            currentPower.powerDeactivate(player);
            HandlerList.unregisterAll(currentPower); 
        }

        playerPowers.remove(uuid);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        NamespacedKey key = new NamespacedKey(Application.getInstance(), "orig_power");
        PersistentDataContainer dataContainer = player.getPersistentDataContainer();

        String powerName = dataContainer.get(key, PersistentDataType.STRING);

        if (powerName == null) {
            player.sendMessage("Whoops! We couldn't find a valid power stored for you.");
            setPower(player, new DummyPower());
        } else {
            setPlayerPower(player, powerName);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        NamespacedKey key = new NamespacedKey(Application.getInstance(), "orig_power");
        PersistentDataContainer dataContainer = player.getPersistentDataContainer();

        Power currentPower = playerPowers.get(player.getUniqueId());
        if (currentPower != null) {
            dataContainer.set(key, PersistentDataType.STRING, currentPower.getName());
            removePlayerPower(player);
        }
    }
}