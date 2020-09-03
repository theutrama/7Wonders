package controller;

import java.util.ArrayList;

import model.card.Card;
import model.player.Player;

public class PlayerController {

	private SevenWondersController swController;

	public PlayerController(SevenWondersController swController) {
		this.swController=swController;
	}
	
	public Player createPlayer(String playername) {
		return null;
	}

	public Player getPlayer(String playername) {
		int state = swController.getGame().getCurrentState();
		ArrayList<Player> list =  swController.getGame().getStates().get(state).getPlayers();
		
		for(Player player: list) {
			if(player.getName().equals(playername)) {
				return player;
			}
		}
		return null;
	}

	public Player createAI(String playername) {
		return null;
	}

	public void chooseCard(Card card, Player player) {

	}

}
