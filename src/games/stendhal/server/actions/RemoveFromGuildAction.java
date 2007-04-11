/*
 * CreateGuildAction.java
 *@author timothyb89
 *Puts player in a guild.
 */

package games.stendhal.server.actions;

import org.apache.log4j.Logger;

import marauroa.common.Log4J;
import marauroa.common.game.RPAction;

import games.stendhal.server.StendhalRPRuleProcessor;
import games.stendhal.server.entity.player.Player;

/**
 * Process guild actions from client. (Remove)
 */
public class RemoveFromGuildAction implements ActionListener {

	/**
	 * Logger.
	 */
	private static final Logger logger = Log4J.getLogger(CreateGuildAction.class);

	/**
	 * Registers action.
	 */
	public static void register() {
		StendhalRPRuleProcessor.register("guildremove", new RemoveFromGuildAction());
	}

	/**
	 * Handle the action.
	 *
	 * @param	player		The player.
	 * @param	action		The action.
	 */
	protected void removeFromGuild(Player player, RPAction action) {
		Log4J.startMethod(logger, "removeFromGuild");

		if (player.get("guild") != null) {
		    player.remove("guild");//resets guild
		    player.remove("description"); //resets description
		    player.sendPrivateText("You have been removed from your old guild.");
		}
		else {
		    player.sendPrivateText("You are not in a guild!");
		}
                
		player.update();
		player.notifyWorldAboutChanges();

		Log4J.finishMethod(logger, "removeFromGuild");
	}

	/**
	 * Handle client action.
	 *
	 * @param	player		The player.
	 * @param	action		The action.
	 */
	public void onAction(Player player, RPAction action) {
		if (action.get("type").equals("removeFromGuild")) {
			removeFromGuild(player, action);
		}
	}
}
