package controller;

import model.Game;
import model.ranking.Ranking;

public class SevenWondersController {

	private GameController gameController;

	private PlayerController playerController;

	private CardController cardController;

	private IOController iOController;

	private WonderBoardController wonderBoardController;

	private Game game;

	private Ranking ranking;

	private SoundController soundController;

	public GameController getGameController() {
		return null;
	}

	public WonderBoardController getWonderBoardController() {
		return null;
	}

	public CardController getCardController() {
		return null;
	}

	public IOController getIOController() {
		return null;
	}

	public PlayerController getPlayerController() {
		return null;
	}
	
	public Ranking getRanking() {
		return ranking;
	}
	
	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}

}
