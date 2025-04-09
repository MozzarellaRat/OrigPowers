## Orig Powers

Orig Powers aims to recreate the Power features present on the "Orig III" plot of Diamondfire.

> [!IMPORTANT]
> This project is still in its early stages.
> This project may not contain the most optimized code.

<details><summary>Skip to:</summary>

- [Creating a power](#creating-a-power)
- [Registering a power](#registering-a-out-of-plugin-power)
- [Contributing](#contributing)
- [Forking](#forking)

</details>



### TODO:
- [ ] Create a better system to handle events in powers.
- [ ] Add more powers!
- [X] Create a "ticker" to handle power loops.
- [ ] Optimize/Cleanup the PowerManager
- [ ] Use a modulo based timer system instead of a hashmap countdown system to trigger hearbeat loops. 

---
### Creating a power:

To create a custom Power extend the `Power` class, although not required if you'd also like this power to have a loop implement the `PowerTicker` class.

```
public class DummyPower extends Power implements PowerTicker {
    //Listener is implemented by the Super class Power.

	@Override
	public String getName() {
		return "dummy"; //The name should be simple, and easy to remember. 
	}

	@Override
	public String getFancyName() { 
		return ChatColor.GOLD+"Dummy"; //This is your "fancy name", it will go in chat messages.
	}
	
	@Override
	public List<String> getDescriptionName() {
		return Arrays.asList("Your basic testing power.", "If you have this you may have spelled the name of one of your power wrong, you may have also forgotten to register your power.");
	}

	@Override
	public void powerDeactivate(Player p) {
		
	}

	@Override
	public void powerActivate(Player p) {

	}
	
	@EventHandler
	public void playerSwapHands(PlayerSwapHandItemsEvent e) { //Example Event
		Power power = Application.PowerManager.getPower(e.getPlayer());
		if (!(power.getClass().equals(this.getClass()))) return;

		if (getCooldown() <= 0) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("Poof!");
			setCooldown(1);
		}
	}

	@Override
	public int tickDelay() {
		return 60; //Tells the PowerManager to fire you tick method every 60 ticks (3 seconds).
	}

	@Override
	public void tick(Player p) {
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Hello, Dummy power "));
		
	}
}

```
 
### Registering a out of plugin power:

Registering a Custom Power from out of plugin has not been tested, but the methods todo so are available. Using a instance of the JavaPlugin call the `getPM()` method in the main class to get a instance of the `PowerManager`. Then call the method `addPower(String powerName, Supplier<Power> power)` with the proper parameters.

Example:
```
//Sudo code, has not been tested.
public class MyCustomPower extends JavaPlugin {
    @Override
    public void onEnable() {
    	PowerManager pm = Application.getPM(); //Make sure to import Application from OrigPowers.
    	pm.addPower("my_new_power",power::get()); //Make sure the ID string provided is the same as the in `getName()` of your power.   
    }

    @Override
    public void onDisable() {

    }
}
```

### Issues 

If you choose to report a issue include:
* Info on the host environment*
* What triggered the issue ( what were you doing? )
* Screenshots
* The server log*

__"*" = Not required but helpful.__

EXAMPLE:
```
Cooldown not working as expected

Despite indicating a one second cooldown, the ability can be spammed.

[image_4-8-25.png], [log.txt]
```

Make sure to report the issues in the issue tab instead of pull requests.

### Contributing

Please use Generative AI only for problem-solving or brainstorming solutions. Do not use it to generate code for this project.

When creating a pull request follow the basic naming convention below:
- `BUGFIX, FEATURE, or MINOR`: `Name of post`

Example Titles:
```
FEATURE: "Nova" power,
BUGFIX: Incorrect method call in "Application.java",
BUGFIX: PowerTicker may stop registering Powers
MINOR: Updated a power description to include color.
```

In your Pull request make sure to outline what you've changed in a easy to read format.

Exmaple:
```
I've changed the PowerManagers getPower() to return a `Supplier<Power>` instead of `Power`
```

### Forking

When forking the repository add `bungeecord-chat` and `spigot-api` to your classpath, __we do not use any dependency managers__. Use the latest version of PaperMC to test your fork. We recommend using the latest version of Eclipse & Github Desktop to develop the project.