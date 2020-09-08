package controller;

import model.Game;
import model.ranking.Ranking;

/** SevenWonders Controller */
public class SevenWondersController {
	
	/** Game Controller */
	private GameController gameController;
	/** Player Controller */
	private PlayerController playerController;
	/** Card Controller */
	private CardController cardController;
	/** IOController */
	private IOController ioController;
	/** WonderBoard Controller */
	private WonderBoardController wonderBoardController;
	/** Current Game */
	private Game game;
	/** Game Ranking */
	private Ranking ranking;
	/** Sound Controller */
	private SoundController soundController;
	/** instance of SevenWonders Controller */
	private static SevenWondersController instance;
	/**
	 * getter for {@link #instance}
	 * @return instance
	 */
	public static SevenWondersController getInstance() {
		if(instance == null) instance= new SevenWondersController();
		return instance;
	}
	
	/**
	 * create new SevenWonders Controller
	 */
	protected SevenWondersController() {
		this.wonderBoardController = new WonderBoardController();
		this.gameController = new GameController(this);
		this.playerController = new PlayerController(this);
		this.cardController = new CardController(this);
		this.ioController = new IOController(this);
		this.soundController = new SoundController();
		
		this.ioController.loadRanking();
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
		return ioController;
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
