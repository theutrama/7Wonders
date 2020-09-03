package controller;

import model.board.BabylonBoard;
import model.board.RhodosBoard;
import model.board.WonderBoard;
import java.util.ArrayList;
import model.card.Card;
import model.card.ResourceType;
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
	
	public int getMilitaryPoints(Player player) {
		int miliPoints = 0;
		
		if(player.getBoard() instanceof RhodosBoard) {
			miliPoints += ((RhodosBoard) player.getBoard()).getMilitaryPoints();
		}
		
		ArrayList<Card> list = player.getBoard().getMilitary();
		for(Card card: list) {
			miliPoints += card.isProducing(ResourceType.MILITARY);
		}
		return miliPoints;
	}
	
	public int getSciencePoints(Player player) {
		int victory_points = 0;
		int[] amount = new int[] {0,0,0};
		for(int i = 0; i < BabylonBoard.types.length; i++) {
			for(Card card : player.getBoard().getResearch()) {
				if(card.getScienceType() == BabylonBoard.types[i]) {
					amount[i]++;
				}
			}
		}
		
		for(int i = 0; i < amount.length; i++) {
			victory_points += amount[i] * amount[i];
		}
		
		while(amount[0] >= 1 && amount[1] >=1 && amount[2] >=1){
			victory_points += 7;
			amount[0]--;
			amount[1]--;
			amount[2]--;
		}
		
		return victory_points;
	}
}
