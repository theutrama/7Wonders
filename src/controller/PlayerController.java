package controller;

import model.board.WonderBoard;
import java.util.ArrayList;
import model.card.Card;
import model.player.AI;
import model.player.Difficulty;
import model.player.Player;

public class PlayerController {

	private SevenWondersController swController;
	private WonderBoardController wb;

	public PlayerController(SevenWondersController swController) {
		this.swController=swController;
		this.wb = swController.getWonderBoardController();
	}
	
	public Player createPlayer(String playername,String wonderboard) {
		WonderBoard board = wb.createWonderBoard(wonderboard);
		Player player = new Player(playername, board);
		board.setPlayer(player);
		
		return player;
	}

	public Player getPlayer(String playername) {
		int state = swController.getGame().getCurrentState();
		ArrayList<Player> list =  swController.getGame().getStates().get(state).getPlayers();
		
		for(Player player: list) {
			if(player.getName().equalsIgnoreCase(playername)) {
				return player;
			}
		}
		return null;
	}

	public AI createAI(String playername,String wonderboard,Difficulty difficulty) {
		WonderBoard board = wb.createWonderBoard(wonderboard);
		AI ai = new AI(difficulty, board);
		return ai;
	}

	public void chooseCard(Card card, Player player) {
		player.setChooseCard(card);
	}

}
