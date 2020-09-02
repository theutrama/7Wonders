package controller;

import model.Game;
import model.GameState;
import model.player.Player;

public class GameController {

	private SevenWondersController swController;

	public GameController(SevenWondersController swController) {
		this.swController=swController;
	}
	
	public Game createGame(String name, Player[] players) {
		return null;
	}

	public GameState createGameRound(int age, int round) {
		return null;
	}

	public void undo(Game game) {

	}

	public void redo(Game game) {

	}

}
