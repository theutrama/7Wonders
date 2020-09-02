package controller;

import model.Game;
import model.ranking.Ranking;

public class SevenWondersController {

	private GameController gameController;

	private PlayerController playerController;

	private CardController cardController;

	private IOController IOController;

	private WonderBoardController wonderBoardController;

	private Game game;

	private Ranking ranking;

	private SoundController soundController;

	public SevenWondersController() {
		this.gameController = new GameController(this);
		this.playerController = new PlayerController(this);
		this.cardController = new CardController(this);
		this.IOController = new IOController(this);
		this.wonderBoardController = new WonderBoardController(this);
	}
	
	public GameController getGameController() {
		return gameController;
	}

	public WonderBoardController getWonderBoardController() {
		return wonderBoardController;
	}

	public CardController getCardController() {
		return cardController;
	}

	public IOController getIOController() {
		return IOController;
	}

	public PlayerController getPlayerController() {
		return playerController;
	}
	
	public SoundController getSoundController() {
		return soundController;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Ranking getRanking() {
		return ranking;
	}
	
	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}
}
