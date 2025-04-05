package pink.rat.Orig;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Setpower implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (sender instanceof Player && args.length >= 1) {
    		Player p = (Player) sender;
    		Application.PowerManager.setPower(p, args[0]);
    		return true;
    	}
		return false;
    	
    }

}
