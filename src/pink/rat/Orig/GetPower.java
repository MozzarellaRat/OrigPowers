package pink.rat.Orig;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pink.rat.Powers.DummyPower;

public class GetPower implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player) {
			Player player = (Player) arg0;
			PowerManager pm = Application.PowerManager;
			Power power = pm.getPower(player);
			if (power == null) {
				power = new DummyPower();
				System.out.println("The command /power was ran but no possible power was found for player: "+player.getName());
			}
			pm.printPowerDescription((Player) arg0, power);
			return true;
		}
		return false;
	}

}
