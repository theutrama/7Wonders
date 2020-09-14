package model.player.multiplayer.events;

import main.api.events.Event;
import model.player.Player;
import model.player.ai.Move.Action;

/** ActionEvent on Player */
@SuppressWarnings("all")
public class PlayerActionEvent extends Event{

	/** the player */
	private Player player;
	/** the action */
	private Action action;
	
	/**
	 * create new PlayerActionEvent
	 * @param player	the player
	 * @param action	the action
	 */
	public PlayerActionEvent(Player player, Action action) {
		this.player=player;
		this.action=action;
	}
	
	/**
	 * getter for {@link #player}
	 * @return player
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * getter for {@link #action}
	 * @return action
	 */
	public Action getAction() {
		return this.action;
	}
}
