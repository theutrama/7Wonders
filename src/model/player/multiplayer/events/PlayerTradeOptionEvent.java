package model.player.multiplayer.events;

import main.api.events.Event;
import model.card.Card;
import model.player.Player;

@SuppressWarnings("all")
public class PlayerTradeOptionEvent extends Event{

	private Player player;
	private int optionIndex;
	
	public PlayerTradeOptionEvent(Player player, int optionIndex) {
		this.player=player;
		this.optionIndex=optionIndex;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public int getOptionIndex() {
		return this.optionIndex;
	}
}
