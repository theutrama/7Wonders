package model.player.multiplayer.events;

import main.api.events.Event;
import model.player.Player;
import model.player.ai.Move.Action;

@SuppressWarnings("all")
public class PlayerActionEvent extends Event{

	private Player player;
	private Action action;
	
	public PlayerActionEvent(Player player, Action action) {
		this.player=player;
		this.action=action;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Action getAction() {
		return this.action;
	}
}
