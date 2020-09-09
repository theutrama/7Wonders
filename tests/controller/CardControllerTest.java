package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import application.Main;
import model.Game;
import model.GameState;
import model.card.Card;
import model.player.Player;
import model.ranking.Ranking;

/** tests Card Controller */
public class CardControllerTest {
	
	private SevenWondersController swc;
	private GameController gC;
	private PlayerController pC;
	private CardController cC;
	private WonderBoardController wbc;
	
	/**
	 * Setup test methods
	 */
	@Before
	public void setUp() {
		
		swc = SevenWondersFactory.create();
		gC = swc.getGameController();
		cC = swc.getCardController();
		pC = swc.getPlayerController();
		wbc = swc.getWonderBoardController();
		Main.TEST = true;
	}
	
	/** tests if first player has card */

	@Test
	public void createHasCardTest() {
		assertEquals(true,cC.hasCard(swc.getPlayerController().getPlayer("erster"), "library"));
		assertEquals(false,cC.hasCard(swc.getPlayerController().getPlayer("erster"), "academy"));
	}
	
	/** tests if first player can sell cards */
	@Test
	public void createSellCardTest() {
		//Wenn bei getCard ein falscher name angegeben wird kommt null zurück 
		cC.sellCard(cC.getCard(pC.getPlayer("erster").getHand(), "lodge"), pC.getPlayer("erster"));
		assertEquals(pC.getPlayer("erster").getCoins(),6);
		cC.sellCard(cC.getCard(pC.getPlayer("erster").getHand(), "study"), pC.getPlayer("erster"));
		assertEquals(pC.getPlayer("erster").getCoins(),9);
		
	}
	
	/**
	 * tests if card gets placed correctly
	 */
	@Test
	public void placeCardTest() {
		int countScienceCards = pC.getPlayer("erster").getBoard().getResearch().size();
		Card randomCard = cC.getCard("library");
		cC.placeCard(randomCard, pC.getPlayer("erster"), null);
		assertEquals(pC.getPlayer("erster").getBoard().getResearch().get(countScienceCards), randomCard);
	}
	
	/**
	 * tests if cards are getting created correctly
	 */
	@Test
	public void loadAllCardTest() {
		assertEquals(cC.loadAllCards().size(), 78);
	}
	
	/**
	 * tests if cards are getting loaded correctly
	 */
	@Test
	public void generateCardStackTest() {
		assertEquals(cC.generateCardStack(swc.getGame().getCurrentGameState().getPlayers()).size(), 216);
	}
	
	@Test
	public void twoPlayersTest() {
		Game game = new Game("testgame2");
		SevenWondersController swc1 = new SevenWondersController();
		swc1.setGame(game);
		Player player01 = swc1.getPlayerController().createPlayer("first", "Alexandria");  
		Player player02 = swc1.getPlayerController().createPlayer("second", "Babylon");
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(player01);
		players.add(player02);
		ArrayList<Card> cards = cC.generateCardStack(players);
		game.getStates().add(new GameState(1, 1, players, cards));
		game.setCurrentState(0);
		
		swc1.getCardController().loadAllCards();
		assertEquals(swc1.getCardController().generateCardStack(players).size(), 136);
	}
	


}
