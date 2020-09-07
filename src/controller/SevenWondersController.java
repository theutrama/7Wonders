package controller;

import model.Game;
import model.ranking.Ranking;

public class SevenWondersController {
	private static SevenWondersController instance = null;
	
	public static SevenWondersController getInstance() {
		if(instance == null) instance = new SevenWondersController();
		return instance;
	}
	
	/** Game Controller */
	private GameController gameController;
	/** Player Controller */
	private PlayerController playerController;
	/** Card Controller */
	private CardController cardController;
	/** IOController */
	private IOController IOController;
	/** WonderBoard Controller */
	private WonderBoardController wonderBoardController;
	/** Current Game */
	private Game game;
	/** Game Ranking */
	private Ranking ranking;
	/** Sound Controller */
	private SoundController soundController;
	/**
	 * create new SevenWonders Controller
	 */
	private SevenWondersController() {
		this.wonderBoardController = new WonderBoardController(this);
		this.gameController = new GameController(this);
		this.playerController = new PlayerController(this);
		this.cardController = new CardController(this);
		this.IOController = new IOController(this);
		this.soundController = new SoundController();
		
		this.IOController.loadRanking();
	}
	/**
	 * @return GameController
	 */
	public GameController getGameController() {
		return gameController;
	}
	/**
	 * @return WonderBoard Controller
	 */
	public WonderBoardController getWonderBoardController() {
		return wonderBoardController;
	}
	/**
	 * @return CardController
	 */
	public CardController getCardController() {
		return cardController;
	}
	/**
	 * @return IOController
	 */
	public IOController getIOController() {
		return IOController;
	}
	/**
	 * @return PlayerController
	 */
	public PlayerController getPlayerController() {
		return playerController;
	}
	/**
	 * @return SoundController
	 */
	public SoundController getSoundController() {
		return soundController;
	}
	/**
	 * @return current Game
	 */
	public Game getGame() {
		return game;
	}
	/**
	 * Sets current Game
	 * 
	 * @param game
	 */
	public void setGame(Game game) {
		this.game = game;
	}
	/**
	 * @return ranking
	 */
	public Ranking getRanking() {
		return ranking;
	}
	/**
	 * Sets current Ranking
	 * 
	 * @param ranking
	 */
	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}
}
