package controller;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import com.sun.media.jfxmedia.logging.Logger;

import application.Main;
import javafx.scene.shape.Rectangle;
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
		assertEquals(cC.generateCardStack(swc.getGame().getCurrentGameState().getPlayers()).size(), 147);
	}
	
	/**
	 * tests two player variant
	 */
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
		assertEquals(swc1.getCardController().generateCardStack(players).size(), 63);
	}
	
	/**
	 * tests three player variant
	 */
	@Test
	public void threePlayersTest() {
		Game game = new Game("testgame4");
		SevenWondersController swc3 = new SevenWondersController();
		swc3.setGame(game);
		Player player01 = swc3.getPlayerController().createPlayer("first", "Alexandria");  
		Player player02 = swc3.getPlayerController().createPlayer("second", "Babylon");
		Player player03 = swc3.getPlayerController().createPlayer("third", "Gizah");
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(player01);
		players.add(player02);
		players.add(player03);
		ArrayList<Card> cards = cC.generateCardStack(players);
		
		game.getStates().add(new GameState(1, 1, players, cards));
		game.setCurrentState(0);
		
		swc3.getCardController().loadAllCards();
		
		assertEquals(swc3.getCardController().generateCardStack(players).size(), 63);
	}
	
	/**
	 * tests four player variant
	 */
	@Test
	public void fourPlayersTest() {
		Game game = new Game("testgame4");
		SevenWondersController swc5 = new SevenWondersController();
		swc5.setGame(game);
		Player player01 = swc5.getPlayerController().createPlayer("first", "Alexandria");  
		Player player02 = swc5.getPlayerController().createPlayer("second", "Babylon");
		Player player03 = swc5.getPlayerController().createPlayer("third", "Gizah");
		Player player04 = swc5.getPlayerController().createPlayer("fourth", "Rhodos");
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(player01);
		players.add(player02);
		players.add(player03);
		players.add(player04);
		ArrayList<Card> cards = cC.generateCardStack(players);
		
		game.getStates().add(new GameState(1, 1, players, cards));
		game.setCurrentState(0);
		
		swc5.getCardController().loadAllCards();
		
		assertEquals(swc5.getCardController().generateCardStack(players).size(), 84);
	}
	
	/**
	 * tests five player variant
	 */
	@Test
	public void fivePlayersTest() {
		Game game = new Game("testgame5");
		SevenWondersController swc5 = new SevenWondersController();
		swc5.setGame(game);
		Player player01 = swc5.getPlayerController().createPlayer("first", "Alexandria");  
		Player player02 = swc5.getPlayerController().createPlayer("second", "Babylon");
		Player player03 = swc5.getPlayerController().createPlayer("third", "Gizah");
		Player player04 = swc5.getPlayerController().createPlayer("fourth", "Rhodos");
		Player player05 = swc5.getPlayerController().createPlayer("fifth", "Olympia");
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(player01);
		players.add(player02);
		players.add(player03);
		players.add(player04);
		players.add(player05);
		ArrayList<Card> cards = cC.generateCardStack(players);
		
		game.getStates().add(new GameState(1, 1, players, cards));
		game.setCurrentState(0);
		
		swc5.getCardController().loadAllCards();
		assertEquals(swc5.getCardController().generateCardStack(players).size(), 105);
	}
	
	@Test
	public void sixPlayersTest() {
		Game game = new Game("testgame6");
		SevenWondersController swc6 = new SevenWondersController();
		swc6.setGame(game);
		Player player01 = swc6.getPlayerController().createPlayer("first", "Alexandria");  
		Player player02 = swc6.getPlayerController().createPlayer("second", "Babylon");
		Player player03 = swc6.getPlayerController().createPlayer("third", "Gizah");
		Player player04 = swc6.getPlayerController().createPlayer("fourth", "Rhodos");
		Player player05 = swc6.getPlayerController().createPlayer("fifth", "Olympia");
		Player player06 = swc6.getPlayerController().createPlayer("fifth", "Ephesos");
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(player01);
		players.add(player02);
		players.add(player03);
		players.add(player04);
		players.add(player05);
		players.add(player06);
		ArrayList<Card> cards = cC.generateCardStack(players);
		
		game.getStates().add(new GameState(1, 1, players, cards));
		game.setCurrentState(0);
		
		swc6.getCardController().loadAllCards();
		assertEquals(swc6.getCardController().generateCardStack(players).size(), 126);
	}
	

	/**
	 * tests getPreviewImage
	 */
	@Test 
	public void getPreviewImageTest(){
		
		Game game = new Game("testgame8");
		SevenWondersController swc8 = new SevenWondersController();
		swc8.setGame(game);
		
		
		
		Player player01 = swc8.getPlayerController().createPlayer("first", "Alexandria");  
		Player player02 = swc8.getPlayerController().createPlayer("second", "Babylon");
		Player player03 = swc8.getPlayerController().createPlayer("third", "Gizah");
		Player player04 = swc8.getPlayerController().createPlayer("fourth", "Rhodos");
		Player player05 = swc8.getPlayerController().createPlayer("fifth", "Olympia");
		Player player06 = swc8.getPlayerController().createPlayer("sixth", "Ephesos");
		Player player07 = swc8.getPlayerController().createPlayer("seventh", "Halikarnassus");
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(player01);
		players.add(player02);
		players.add(player03);
		players.add(player04);
		players.add(player05);
		players.add(player06);
		players.add(player07);
		ArrayList<Card> cards = swc8.getCardController().generateCardStack(players);
		game.getStates().add(new GameState(1, 1, players, cards));
		game.setCurrentState(0);
		
		ArrayList<Card> cardsToCheck = new ArrayList<Card>();
		
		System.out.println(cards.size());
		
		Card card1 = cC.getCard(cards, "lumberyard");
		Card card2 = cC.getCard(cards, "sawmill");
		Card card3 = cC.getCard(cards, "treefarm");
		Card card4 = cC.getCard(cards, "loom1");
		Card card5 = cC.getCard(cards, "altar");
		Card card6 = cC.getCard(cards, "library");
		//red
		Card card7 = cC.getCard(cards, "barracks");
		Card card8 = cC.getCard(cards, "walls");
		Card card9 = cC.getCard(cards, "circus");
		//yellow
		Card card10 = cC.getCard(cards, "arena");
		Card card11 = cC.getCard(cards, "chamberofcommerce");
		Card card12 = cC.getCard(cards, "haven");
		Card card13 = cC.getCard(cards, "lighthouse");
		Card card14 = cC.getCard(cards, "marketplace");
		Card card15 = cC.getCard(cards, "tavern");
		Card card16 = cC.getCard(cards, "westtradingpost");
		//purple
		Card card17 = cC.getCard(cards, "workersguild");
		Card card18 = cC.getCard(cards, "scientistsguild");
		Card card19 = cC.getCard(cards, "shipownersguild");
		Card card20 = cC.getCard(cards, "strategistsguild");
		
		cardsToCheck.add(card1);
		cardsToCheck.add(card2);
		cardsToCheck.add(card3);
		cardsToCheck.add(card4);
		cardsToCheck.add(card5);
		cardsToCheck.add(card6);
		cardsToCheck.add(card7);
		cardsToCheck.add(card8);
		cardsToCheck.add(card9);
		cardsToCheck.add(card10);
		cardsToCheck.add(card11);
		cardsToCheck.add(card12);
		cardsToCheck.add(card13);
		cardsToCheck.add(card14);
		cardsToCheck.add(card15);
		cardsToCheck.add(card16);
		cardsToCheck.add(card17);
		cardsToCheck.add(card18);
		cardsToCheck.add(card19);
		cardsToCheck.add(card20);

		for(Card card: cardsToCheck) {
			try {
				BufferedImage full = ImageIO.read(new File(card.getImage()));
				assertEquals(cC.getSubimage(full, new Rectangle(64, 12, 54, 50)).getHeight() ,cC.getPreviewImage(card).getHeight(), 0);
			} catch (Exception e) {
				System.out.println("hi");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * tests if slot card gets placed correctly
	 */
	@Test
	public void setSlotCardTest() {
		assertFalse(pC.getPlayer("erster").getBoard().isFilled(0));
		cC.setSlotCard(cC.getCard("lumberyard"), pC.getPlayer("erster"), null);
		cC.setSlotCard(cC.getCard("lumberyard"), pC.getPlayer("erster"), null);
		cC.setSlotCard(cC.getCard("lumberyard"), pC.getPlayer("erster"), null);
		assertTrue(pC.getPlayer("erster").getBoard().isFilled(0));
		assertTrue(pC.getPlayer("erster").getBoard().isFilled(1));
		assertTrue(pC.getPlayer("erster").getBoard().isFilled(2));
	}

}
