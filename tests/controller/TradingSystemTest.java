package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import application.Main;
import controller.utils.BuildCapability;
import model.card.Card;
import model.card.CardType;
import model.card.Resource;
import model.card.ResourceType;
import model.player.Player;

/** testing trading system */
public class TradingSystemTest {
	
	/**
	 * creating players and trading with neighbors
	 */
	@Test
	public void testTrading() {
		SevenWondersController controller = new SevenWondersController();
		Main.setSwController(controller);
		Player player1 = controller.getPlayerController().createPlayer("player 1", "Alexandria"), player2 = controller.getPlayerController().createPlayer("player 2", "Olympia"),
				player3 = controller.getPlayerController().createPlayer("player 3", "Rhodos");
		controller.setGame(controller.getGameController().createGame("game 1", createList(player1, player2, player3)));
		
		
		player1 = controller.getPlayerController().getPlayer("player 1");
		player2 = controller.getPlayerController().getPlayer("player 2");
		player3 = controller.getPlayerController().getPlayer("player 3");

		Card card1 = new Card(1, "card 1", "card 1", CardType.BROWN, createList(new Resource(1, ResourceType.ORE)), null, null, null);
		Card card2 = new Card(1, "card 2", "card 2", CardType.BROWN, createList(new Resource(1, ResourceType.GLASS)), null, new String[] { "card 1" }, null);
		Card card3 = new Card(1, "card 3", "card 3", CardType.BROWN, createList(new Resource(1, ResourceType.PAPYRUS)), createList(new Resource(1, ResourceType.ORE)), null, null);

		assertEquals(BuildCapability.OWN_RESOURCE, controller.getPlayerController().canBuild(player1, card1));
		assertEquals(BuildCapability.OWN_RESOURCE, controller.getPlayerController().canBuild(player2, card2));
		assertEquals(BuildCapability.TRADE, controller.getPlayerController().canBuild(player1, card3));

		player1.getBoard().addCard(card3);
		assertTrue(controller.getCardController().hasCard(player1, "card 3"));
		assertEquals(BuildCapability.OWN_RESOURCE, controller.getPlayerController().canBuild(player1, card1));

		assertEquals(BuildCapability.OWN_RESOURCE, controller.getPlayerController().canBuild(player3, card2));
		player3.getBoard().addCard(card1);
		assertTrue(controller.getCardController().hasCard(player3, "card 1"));
		assertEquals(BuildCapability.FREE, controller.getPlayerController().canBuild(player3, card2));
		assertEquals(BuildCapability.OWN_RESOURCE, controller.getPlayerController().hasResources(player3, createList(new Resource(2, ResourceType.ORE))));
		assertEquals(BuildCapability.OWN_RESOURCE, controller.getPlayerController().hasResources(player3, createList(new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.ORE))));
		assertEquals(BuildCapability.NONE, controller.getPlayerController().hasResources(player2, createList(new Resource(2, ResourceType.ORE))));
		player2.addCoins(1);
		assertEquals(BuildCapability.TRADE, controller.getPlayerController().hasResources(player2, createList(new Resource(2, ResourceType.ORE))));
		assertEquals(BuildCapability.TRADE, controller.getPlayerController().hasResources(player2, createList(new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.ORE))));
		assertEquals(BuildCapability.TRADE, controller.getPlayerController().hasResources(player2, createList(new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.WOOD))));
		assertEquals(BuildCapability.TRADE, controller.getPlayerController().hasResources(player2, createList(new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.GLASS))));
		assertEquals(BuildCapability.NONE, controller.getPlayerController().hasResources(player2, createList(new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.CLOTH))));
		player2.addCoins(-2);
		player2.getBoard().addCard(new Card(1, "trade card", "easttradingpost", CardType.YELLOW, null, null, null, null));
		assertEquals(BuildCapability.NONE, controller.getPlayerController().hasResources(player2, createList(new Resource(2, ResourceType.ORE))));
		player2.getBoard().addCard(new Card(1, "trade card", "westtradingpost", CardType.YELLOW, null, null, null, null));
		assertEquals(BuildCapability.TRADE, controller.getPlayerController().hasResources(player2, createList(new Resource(2, ResourceType.ORE))));
		player2.getBoard().getTrade().remove(0);
		player2.getBoard().getTrade().remove(0);
		player2.getBoard().addCard(new Card(1, "trade card", "marketplace", CardType.YELLOW, null, null, null, null));
		player2.addCoins(1);
		assertEquals(BuildCapability.TRADE, controller.getPlayerController().hasResources(player2, createList(new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.GLASS))));

		player3.getBoard().getResources().clear();
		player1.getBoard().addCard(card1);
		player2.addCoins(10);
		assertEquals(BuildCapability.TRADE, controller.getPlayerController().hasResources(player2, createList(new Resource(2, ResourceType.ORE))));
		assertEquals(2, controller.getPlayerController().getTradeOptions(player2, createList(new Resource(1, ResourceType.ORE))).size());

		Card card4 = new Card(1, "card 4", "card 4", CardType.BROWN, createList(new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.WOOD)), null, null, null);
		player2.getBoard().addCard(card4);
		Card card5 = new Card(1, "card 5", "card 5", CardType.BROWN, createList(new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.WOOD)), null, null, null);
		player1.getBoard().addCard(card5);
		assertEquals(BuildCapability.TRADE, controller.getPlayerController().hasResources(player2, createList(new Resource(2, ResourceType.ORE), new Resource(1, ResourceType.WOOD))));
		assertEquals(BuildCapability.NONE, controller.getPlayerController().hasResources(player2, createList(new Resource(50, ResourceType.COINS))));
	}

	/**
	 *  creating players and trading with neighbors 
	 */
	@Test
	public void testTrading2() {
		SevenWondersController controller = SevenWondersFactory.create();
		Player player1 = controller.getPlayerController().getPlayer("erster"), player2 = controller.getPlayerController().getPlayer("zweiter");
		
		assertEquals(BuildCapability.OWN_RESOURCE, controller.getPlayerController().hasResources(player1, createList(new Resource(2, ResourceType.BRICK))));
		assertEquals(BuildCapability.OWN_RESOURCE,
				controller.getPlayerController().hasResources(player2, createList(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.BRICK), new Resource(1, ResourceType.STONE))));
		player2.addCoins(20);
		assertEquals(BuildCapability.TRADE,
				controller.getPlayerController().hasResources(player2, createList(new Resource(2, ResourceType.WOOD), new Resource(4, ResourceType.BRICK), new Resource(2, ResourceType.STONE))));
		assertEquals(BuildCapability.TRADE,
				controller.getPlayerController().hasResources(player2, createList(new Resource(5, ResourceType.BRICK), new Resource(3, ResourceType.STONE))));
		assertEquals(BuildCapability.NONE,
				controller.getPlayerController().hasResources(player2, createList(new Resource(6, ResourceType.BRICK), new Resource(4, ResourceType.STONE))));
	}

	/**
	 * creates a new ArrayList that contains the specified objects
	 * 
	 * @param <T>     type
	 * @param objects list of objects
	 * @return Arraylist that contains all objects
	 */
	@SafeVarargs
	public static <T> ArrayList<T> createList(T... objects) {
		return new ArrayList<>(Arrays.asList(objects));
	}

}
