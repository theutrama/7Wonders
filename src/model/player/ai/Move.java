package model.player.ai;

import model.card.Card;

@SuppressWarnings("PMD")

public class Move {

	private Card chosen;
	private Action action;
	private ArtInt player;
	
	public Move(ArtInt player, Card chosen, Action action) {
		this.chosen = chosen;
		this.action = action;
		this.player = player;
	}
	
	public enum Action{
		BUILD,
		PLACE,
		SELL;
	}
}