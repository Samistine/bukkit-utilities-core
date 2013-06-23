/*******************************************************************************
 Copyright (c) 2013 James Richardson.

 CommandManager.java is part of BukkitUtilities.

 BukkitUtilities is free software: you can redistribute it and/or modify it
 under the terms of the GNU General Public License as published by the Free
 Software Foundation, either version 3 of the License, or (at your option) any
 later version.

 BukkitUtilities is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with
 BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.utilities.command;

import java.util.*;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.PluginDescriptionFile;

import name.richardson.james.bukkit.utilities.colours.ColourScheme;
import name.richardson.james.bukkit.utilities.command.argument.Argument;
import name.richardson.james.bukkit.utilities.command.argument.CommandArgument;
import name.richardson.james.bukkit.utilities.localisation.LocalisedCoreColourScheme;
import name.richardson.james.bukkit.utilities.localisation.PluginResourceBundle;

public class CommandManager implements TabExecutor {

	private final Argument argument;
	private final Map<String, Command> commands = new LinkedHashMap<String, Command>();
	private final Command helpCommand;
	private final ResourceBundle resourceBundle = PluginResourceBundle.getBundle(CommandManager.class);

	private final LocalisedCoreColourScheme localisedColourScheme = new LocalisedCoreColourScheme(resourceBundle);

	public CommandManager(HelpCommand helpCommand) {
		this.argument = new CommandArgument();
		this.helpCommand = helpCommand;
	}

	public void addCommand(final Command command) {
		this.commands.put(command.getName(), command);
		CommandArgument.setCommands(this.commands.keySet());
	}

	public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command cmd, final String label, final String[] args) {
		final List<String> arguments = new LinkedList<String>(Arrays.asList(args));
		if (arguments.isEmpty()) {
			this.helpCommand.execute(arguments, sender);
		} else {
			if (this.commands.containsKey(arguments.get(0).toLowerCase())) {
				final Command command = this.commands.get(arguments.get(0).toLowerCase());
				arguments.remove(0);
				if (command.isAuthorized(sender)) {
					command.execute(arguments, sender);
				} else {
					sender.sendMessage(this.localisedColourScheme.format(ColourScheme.Style.ERROR, "not-allowed"));
				}
			} else if (arguments.get(0).contentEquals(this.helpCommand.getName())) {
				arguments.remove(0);
				this.helpCommand.execute(arguments, sender);
			} else {
				this.helpCommand.execute(arguments, sender);
			}
		}
		return true;
	}

	public List<String> onTabComplete(final CommandSender sender, final org.bukkit.command.Command cmd, final String label, final String[] args) {
		final List<String> arguments = new ArrayList<String>(Arrays.asList(args));
		final Command command = this.commands.get(arguments.get(0));
		if (command != null) {
			if (command.isAuthorized(sender)) {
				arguments.remove(0);
				return command.onTabComplete(arguments, sender);
			} else {
				return new ArrayList<String>();
			}
		} else {
			return new ArrayList<String>(this.argument.getMatches(arguments.get(0)));
		}
	}

}
