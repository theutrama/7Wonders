package controller;

import model.board.WonderBoard;
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
		return player;
	}

	public Player getPlayer(String playername) {
		return null;
	}

	public AI createAI(String playername,String wonderboard,Difficulty difficulty) {
		WonderBoard board = wb.createWonderBoard(wonderboard);
		AI ai = new AI(difficulty, board);
		return ai;
	}

	public void chooseCard(Card card, Player player) {

	}

}
