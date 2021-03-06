package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import application.Main;
import controller.sound.Sound;
import controller.utils.TradeOption;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.Collections;

import model.GameState;
import model.board.WonderBoard;
import model.card.Card;
import model.card.CardType;
import model.card.Effect;
import model.card.EffectType;
import model.card.Resource;
import model.card.ResourceType;
import model.player.Player;

/** The Card-Controller controls the ingame cards. */
//@SuppressWarnings("PMD")
public class CardController {
	private ArrayList<Card> loadedCards = new ArrayList<Card>();
	/** HashMap for frequency of cards */
	private Map<String, int[]> countCards = new HashMap<>();
	/** SevenWonders Controller */
	private SevenWondersController swController;

	/**
	 * create new Card Controller
	 * 
	 * @param swController SevenWonders Controller
	 */
	public CardController(SevenWondersController swController) {
		this.swController = swController;
		loadCards();
		loadAllCards();
	}

	/**
	 * Loading all ingame cards with their parameters and frequency Matrix-scheme: amount of players when another card needs to be created
	 */
	public void loadCards() {
		countCards.put("academy", new int[] { 3, 7, 0 });
		countCards.put("altar", new int[] { 3, 5, 0 });
		countCards.put("apothecary", new int[] { 3, 5, 0 });
		countCards.put("aqueduct", new int[] { 3, 7, 0 });
		countCards.put("archeryrange", new int[] { 3, 6, 0 });
		countCards.put("arena", new int[] { 3, 5, 7 });
		countCards.put("arsenal", new int[] { 3, 4, 7 });
		countCards.put("barracks", new int[] { 3, 5, 0 });
		countCards.put("baths", new int[] { 3, 7, 0 });
		countCards.put("bazar", new int[] { 4, 7, 0 });
		countCards.put("brickyard", new int[] { 3, 4, 0 });
		countCards.put("buildersguild", new int[] { 0, 0, 0 });
		countCards.put("caravansery", new int[] { 3, 5, 6 });
		countCards.put("chamberofcommerce", new int[] { 4, 6, 0 });
		countCards.put("circus", new int[] { 4, 5, 6 });
		countCards.put("claypit", new int[] { 3, 0, 0 });
		countCards.put("claypool", new int[] { 3, 5, 0 });
		countCards.put("courthouse", new int[] { 3, 5, 0 });
		countCards.put("craftsmensguild", new int[] { 0, 0, 0 });
		countCards.put("dispensary", new int[] { 3, 4, 0 });
		countCards.put("easttradingpost", new int[] { 3, 7, 0 });
		countCards.put("excavation", new int[] { 4, 0, 0 });
		countCards.put("forestcave", new int[] { 5, 0, 0 });
		countCards.put("fortifications", new int[] { 3, 7, 0 });
		countCards.put("forum", new int[] { 3, 6, 7 });
		countCards.put("foundry", new int[] { 3, 4, 0 });
		countCards.put("gardens", new int[] { 3, 4, 0 });
		countCards.put("glassworks1", new int[] { 3, 6, 0 });
		countCards.put("glassworks2", new int[] { 3, 5, 0 });
		countCards.put("guardtower", new int[] { 3, 4, 0 });
		countCards.put("haven", new int[] { 3, 4, 0 });
		countCards.put("laboratory", new int[] { 3, 5, 0 });
		countCards.put("library", new int[] { 3, 6, 0 });
		countCards.put("lighthouse", new int[] { 3, 6, 0 });
		countCards.put("lodge", new int[] { 3, 6, 0 });
		countCards.put("loom1", new int[] { 3, 6, 0 });
		countCards.put("loom2", new int[] { 3, 5, 0 });
		countCards.put("lumberyard", new int[] { 3, 4, 0 });
		countCards.put("magistratesguild", new int[] { 0, 0, 0 });
		countCards.put("marketplace", new int[] { 3, 6, 0 });
		countCards.put("mine", new int[] { 6, 0, 0 });
		countCards.put("observatory", new int[] { 3, 7, 0 });
		countCards.put("orevein", new int[] { 3, 4, 0 });
		countCards.put("palace", new int[] { 3, 7, 0 });
		countCards.put("pantheon", new int[] { 3, 6, 0 });
		countCards.put("pawnshop", new int[] { 4, 7, 0 });
		countCards.put("philosophersguild", new int[] { 0, 0, 0 });
		countCards.put("press1", new int[] { 3, 6, 0 });
		countCards.put("press2", new int[] { 3, 5, 0 });
		countCards.put("quarry", new int[] { 3, 4, 0 });
		countCards.put("sawmill", new int[] { 3, 4, 0 });
		countCards.put("school", new int[] { 3, 7, 0 });
		countCards.put("scientistsguild", new int[] { 0, 0, 0 });
		countCards.put("scriptorium", new int[] { 3, 4, 0 });
		countCards.put("senate", new int[] { 3, 5, 0 });
		countCards.put("shipownersguild", new int[] { 0, 0, 0 });
		countCards.put("siegeworkshop", new int[] { 3, 5, 0 });
		countCards.put("spiesguild", new int[] { 0, 0, 0 });
		countCards.put("stables", new int[] { 3, 5, 0 });
		countCards.put("statue", new int[] { 3, 7, 0 });
		countCards.put("stockade", new int[] { 3, 7, 0 });
		countCards.put("stonepit", new int[] { 3, 5, 0 });
		countCards.put("strategistsguild", new int[] { 0, 0, 0 });
		countCards.put("study", new int[] { 3, 5, 0 });
		countCards.put("tavern", new int[] { 4, 5, 7 });
		countCards.put("temple", new int[] { 3, 6, 0 });
		countCards.put("theater", new int[] { 3, 6, 0 });
		countCards.put("timberyard", new int[] { 3, 0, 0 });
		countCards.put("townhall", new int[] { 3, 5, 6 });
		countCards.put("tradersguild", new int[] { 0, 0, 0 });
		countCards.put("trainingground", new int[] { 4, 6, 7 });
		countCards.put("treefarm", new int[] { 6, 0, 0 });
		countCards.put("university", new int[] { 3, 4, 0 });
		countCards.put("vineyard", new int[] { 3, 6, 0 });
		countCards.put("walls", new int[] { 3, 7, 0 });
		countCards.put("westtradingpost", new int[] { 3, 7, 0 });
		countCards.put("workersguild", new int[] { 0, 0, 0 });
		countCards.put("workshop", new int[] { 3, 7, 0 });
	}

	/**
	 * does something unimportant
	 * 
	 * @return list of cards
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Card> loadAllCards() {
		if (!loadedCards.isEmpty())
			return (ArrayList<Card>) loadedCards.clone();
		ArrayList<Card> cards = loadedCards;

		cards.add(new Card(ResourceType.COMPASS, 3, "Akademie", "academy", CardType.GREEN, null, addRArray(new Resource(3, ResourceType.STONE), new Resource(1, ResourceType.GLASS)),
				new String[] { "school" }, null));
		cards.add(new Card(1, "Altar", "altar", CardType.BLUE, null, null, null, addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addVictoryPoints(2); })), 2));
		cards.add(new Card(ResourceType.COMPASS, 1, "Apotheke", "apothecary", CardType.GREEN, null, addRArray(new Resource(1, ResourceType.CLOTH)), null, null));

		cards.add(new Card(2, "Aquaedukt", "aqueduct", CardType.BLUE, null, addRArray(new Resource(3, ResourceType.STONE)), new String[] { "baths" },
				addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addVictoryPoints(5); })), 5));
		cards.add(new Card(2, "Schiessplatz", "archeryrange", CardType.RED, addRArray(new Resource(2, ResourceType.MILITARY)),
				addRArray(new Resource(2, ResourceType.WOOD), new Resource(1, ResourceType.ORE)), new String[] { "workshop" }, null));
		cards.add(new Card(3, "Arena", "arena", CardType.YELLOW, null, addRArray(new Resource(2, ResourceType.STONE), new Resource(1, ResourceType.ORE)), new String[] { "dispensary" },
				addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addCoins(3 * (player.getBoard().nextSlot() == -1 ? 3 : player.getBoard().nextSlot())); }),
						new Effect(EffectType.AT_MATCH_END, (player, state, twoPlayers) -> { player.addVictoryPoints(player.getBoard().nextSlot() == -1 ? 3 : player.getBoard().nextSlot()); }))));
		cards.add(new Card(3, "Waffenlager", "arsenal", CardType.RED, addRArray(new Resource(3, ResourceType.MILITARY)),
				addRArray(new Resource(2, ResourceType.WOOD), new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.CLOTH)), null, null));
		cards.add(new Card(1, "Kaserne", "barracks", CardType.RED, addRArray(new Resource(1, ResourceType.MILITARY)), addRArray(new Resource(1, ResourceType.ORE)), null, null));
		cards.add(new Card(1, "Baeder", "baths", CardType.BLUE, null, addRArray(new Resource(1, ResourceType.STONE)), null,
				addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addVictoryPoints(3); })), 3));
		cards.add(new Card(2, "Basar", "bazar", CardType.YELLOW, null, null, null, addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> {
			Player left = Main.getSWController().getPlayerController().getLeftNeighbour(state, player);
			Player right = Main.getSWController().getPlayerController().getRightNeighbour(state, player);

			int count = 0;
			for (Card el : left.getBoard().getResources())
				if (el.getType() == CardType.GRAY)
					count++;
			if (!twoPlayers) {
				for (Card el : right.getBoard().getResources())
					if (el.getType() == CardType.GRAY)
						count++;
			}
			for (Card el : player.getBoard().getResources())
				if (el.getType() == CardType.GRAY)
					count++;
			player.addCoins(2 * count);
		}))));
		cards.add(new Card(2, "Ziegelbrennerei", "brickyard", CardType.BROWN, addRArray(new Resource(2, ResourceType.BRICK)), addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(3, "Gilde der Baumeister", "buildersguild", CardType.PURPLE, null,
				addRArray(new Resource(2, ResourceType.BRICK), new Resource(2, ResourceType.STONE), new Resource(1, ResourceType.GLASS)), null,
				addEArray(new Effect(EffectType.AT_MATCH_END, (player, state, twoPlayers) -> {
					Player left = Main.getSWController().getPlayerController().getLeftNeighbour(state, player);
					Player right = Main.getSWController().getPlayerController().getRightNeighbour(state, player);

					int purple1 = left.getBoard().nextSlot();
					int purple2 = twoPlayers ? 0 : right.getBoard().nextSlot();
					int purple3 = player.getBoard().nextSlot();
					purple1 = purple1 == -1 ? 3 : purple1;
					purple2 = purple2 == -1 ? 3 : purple2;
					purple3 = purple3 == -1 ? 3 : purple3;
					player.addVictoryPoints(purple1 + purple2 + purple3);
				}))));
		cards.add(new Card(2, "Karawanserei", "caravansery", CardType.YELLOW,
				addRArray(new Resource(1, ResourceType.BRICK), new Resource(1, ResourceType.STONE), new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.WOOD)),
				addRArray(new Resource(2, ResourceType.WOOD)), new String[] { "marketplace" }, null));
		cards.add(new Card(3, "Handelskammer", "chamberofcommerce", CardType.YELLOW, null, addRArray(new Resource(2, ResourceType.BRICK), new Resource(1, ResourceType.PAPYRUS)), null,

				addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> {
					int count = 0;
					for (Card el : player.getBoard().getResources())
						if (el.getType() == CardType.GRAY)
							count++;
					player.addCoins(2 * count);
				}), new Effect(EffectType.AT_MATCH_END, (player, state, twoPlayers) -> {
					int count = 0;
					for (Card el : player.getBoard().getResources())
						if (el.getType() == CardType.GRAY)
							count++;
					player.addVictoryPoints(2 * count);
				}))));
		cards.add(new Card(3, "Zirkus", "circus", CardType.RED, addRArray(new Resource(3, ResourceType.MILITARY)), addRArray(new Resource(3, ResourceType.STONE), new Resource(1, ResourceType.ORE)),
				new String[] { "trainingground" }, null));
		cards.add(new Card(1, "Tongrube", "claypit", CardType.BROWN, addRArray(new Resource(1, ResourceType.BRICK), new Resource(1, ResourceType.ORE)), addRArray(new Resource(1, ResourceType.COINS)),
				null, null));
		cards.add(new Card(1, "Ziegelei", "claypool", CardType.BROWN, addRArray(new Resource(1, ResourceType.BRICK)), null, null, null));
		cards.add(new Card(2, "Gericht", "courthouse", CardType.BLUE, null, addRArray(new Resource(2, ResourceType.BRICK), new Resource(1, ResourceType.CLOTH)), new String[] { "scriptorium" },
				addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addVictoryPoints(4); })), 4));
		cards.add(new Card(3, "Gilde der Kuenstler", "craftsmensguild", CardType.PURPLE, null, addRArray(new Resource(2, ResourceType.STONE), new Resource(2, ResourceType.ORE)), null,
				addEArray(new Effect(EffectType.AT_MATCH_END, (player, state, twoPlayers) -> {
					Player left = Main.getSWController().getPlayerController().getLeftNeighbour(state, player);
					Player right = Main.getSWController().getPlayerController().getRightNeighbour(state, player);

					int count = 0;
					for (Card el : left.getBoard().getResources())
						if (el.getType() == CardType.GRAY)
							count++;
					if (!twoPlayers) {
						for (Card el : right.getBoard().getResources())
							if (el.getType() == CardType.GRAY)
								count++;
					}
					player.addVictoryPoints(2 * count);
				}))));
		cards.add(new Card(ResourceType.COMPASS, 2, "Arzneiausgabe", "dispensary", CardType.GREEN, null, addRArray(new Resource(2, ResourceType.ORE), new Resource(1, ResourceType.GLASS)),
				new String[] { "apothecary" }, null));
		cards.add(new Card(1, "Kontor Ost", "easttradingpost", CardType.YELLOW, null, null, null, null));
		cards.add(new Card(1, "Ausgrabungsstaette", "excavation", CardType.BROWN, addRArray(new Resource(1, ResourceType.BRICK), new Resource(1, ResourceType.STONE)),
				addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(1, "Waldhoehle", "forestcave", CardType.BROWN, addRArray(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.ORE)),
				addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(3, "Verteidigungsanlage", "fortifications", CardType.RED, addRArray(new Resource(3, ResourceType.MILITARY)),
				addRArray(new Resource(3, ResourceType.ORE), new Resource(1, ResourceType.STONE)), new String[] { "walls" }, null));
		cards.add(new Card(2, "Forum", "forum", CardType.YELLOW, addRArray(new Resource(1, ResourceType.GLASS), new Resource(1, ResourceType.CLOTH), new Resource(1, ResourceType.PAPYRUS)),
				addRArray(new Resource(2, ResourceType.BRICK)), new String[] { "easttradingpost", "westtradingpost" }, null));
		cards.add(new Card(2, "Giesserei", "foundry", CardType.BROWN, addRArray(new Resource(2, ResourceType.ORE)), addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(3, "Gaerten", "gardens", CardType.BLUE, null, addRArray(new Resource(2, ResourceType.BRICK), new Resource(1, ResourceType.WOOD)), new String[] { "statue" },
				addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addVictoryPoints(5); })), 5));
		cards.add(new Card(1, "Glashuette", "glassworks1", CardType.GRAY, addRArray(new Resource(1, ResourceType.GLASS)), null, null, null));
		cards.add(new Card(2, "Glashuette", "glassworks2", CardType.GRAY, addRArray(new Resource(1, ResourceType.GLASS)), null, null, null));
		cards.add(new Card(1, "Wachturm", "guardtower", CardType.RED, addRArray(new Resource(1, ResourceType.MILITARY)), addRArray(new Resource(1, ResourceType.BRICK)), null, null));
		cards.add(new Card(3, "Hafen", "haven", CardType.YELLOW, null, addRArray(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.CLOTH)),
				new String[] { "forum" }, addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> {
					int count = 0;
					for (Card el : player.getBoard().getResources())
						if (el.getType() == CardType.BROWN)
							count++;
					player.addCoins(count);
				}), new Effect(EffectType.AT_MATCH_END, (player, state, twoPlayers) -> {
					int count = 0;
					for (Card el : player.getBoard().getResources())
						if (el.getType() == CardType.BROWN)
							count++;
					player.addVictoryPoints(count);
				}))));
		cards.add(new Card(ResourceType.GEAR, 2, "Laboratorium", "laboratory", CardType.GREEN, null, addRArray(new Resource(2, ResourceType.BRICK), new Resource(1, ResourceType.PAPYRUS)),
				new String[] { "workshop" }, null));
		cards.add(new Card(ResourceType.TABLET, 2, "Bibliothek", "library", CardType.GREEN, null, addRArray(new Resource(2, ResourceType.STONE), new Resource(1, ResourceType.CLOTH)),
				new String[] { "scriptorium" }, null));
		cards.add(new Card(3, "Leuchtturm", "lighthouse", CardType.YELLOW, null, addRArray(new Resource(1, ResourceType.STONE), new Resource(1, ResourceType.GLASS)), new String[] { "caravansery" },
				addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addCoins(player.getBoard().getTrade().size()); }),
						new Effect(EffectType.AT_MATCH_END, (player, state, twoPlayers) -> { player.addVictoryPoints(player.getBoard().getTrade().size()); }))));
		cards.add(new Card(ResourceType.COMPASS, 3, "Loge", "lodge", CardType.GREEN, null,
				addRArray(new Resource(2, ResourceType.BRICK), new Resource(1, ResourceType.PAPYRUS), new Resource(1, ResourceType.CLOTH)), new String[] { "dispensary" }, null));
		cards.add(new Card(1, "Webstuhl", "loom1", CardType.GRAY, addRArray(new Resource(1, ResourceType.CLOTH)), null, null, null));
		cards.add(new Card(2, "Webstuhl", "loom2", CardType.GRAY, addRArray(new Resource(1, ResourceType.CLOTH)), null, null, null));
		cards.add(new Card(1, "Holzplatz", "lumberyard", CardType.BROWN, addRArray(new Resource(1, ResourceType.WOOD)), null, null, null));
		cards.add(new Card(3, "Gilde der Beamten", "magistratesguild", CardType.PURPLE, null,
				addRArray(new Resource(3, ResourceType.WOOD), new Resource(1, ResourceType.STONE), new Resource(1, ResourceType.CLOTH)), null,
				addEArray(new Effect(EffectType.AT_MATCH_END, (player, state, twoPlayers) -> {
					Player left = Main.getSWController().getPlayerController().getLeftNeighbour(state, player);
					Player right = Main.getSWController().getPlayerController().getRightNeighbour(state, player);

					int count = 0;
					count += left.getBoard().getCivil().size();
					if (!twoPlayers) {
						count += right.getBoard().getCivil().size();
					}
					player.addVictoryPoints(count);
				}))));
		cards.add(new Card(1, "Markt", "marketplace", CardType.YELLOW, null, null, null, null));
		cards.add(new Card(1, "Mine", "mine", CardType.BROWN, addRArray(new Resource(1, ResourceType.STONE), new Resource(1, ResourceType.ORE)), addRArray(new Resource(1, ResourceType.COINS)), null,
				null));
		cards.add(new Card(ResourceType.GEAR, 3, "Observatorium", "observatory", CardType.GREEN, null,
				addRArray(new Resource(2, ResourceType.ORE), new Resource(1, ResourceType.GLASS), new Resource(1, ResourceType.CLOTH)), new String[] { "laboratory" }, null));
		cards.add(new Card(1, "Erzbergwerk", "orevein", CardType.BROWN, addRArray(new Resource(1, ResourceType.ORE)), null, null, null));
		cards.add(new Card(3, "Palast", "palace", CardType.BLUE, null,
				addRArray(new Resource(1, ResourceType.STONE), new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.BRICK),
						new Resource(1, ResourceType.GLASS), new Resource(1, ResourceType.PAPYRUS), new Resource(1, ResourceType.CLOTH)),
				null, addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addVictoryPoints(8); })), 8));
		cards.add(new Card(3, "Pantheon", "pantheon", CardType.BLUE, null,
				addRArray(new Resource(1, ResourceType.ORE), new Resource(2, ResourceType.BRICK), new Resource(1, ResourceType.GLASS), new Resource(1, ResourceType.PAPYRUS),
						new Resource(1, ResourceType.CLOTH)),
				new String[] { "temple" }, addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addVictoryPoints(7); })), 7));
		cards.add(new Card(1, "Pfandhaus", "pawnshop", CardType.BLUE, null, null, null, addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addVictoryPoints(3); })),
				3));
		cards.add(new Card(3, "Gilde der Philosophen", "philosophersguild", CardType.PURPLE, null,
				addRArray(new Resource(3, ResourceType.BRICK), new Resource(1, ResourceType.CLOTH), new Resource(1, ResourceType.PAPYRUS)), null,
				addEArray(new Effect(EffectType.AT_MATCH_END, (player, state, twoPlayers) -> {
					Player left = Main.getSWController().getPlayerController().getLeftNeighbour(state, player);
					int leftV = left.getBoard().getResearch().size();
					int rightV = twoPlayers ? 0 : Main.getSWController().getPlayerController().getRightNeighbour(state, player).getBoard().getResearch().size();
					player.addVictoryPoints(leftV + rightV);
				}))));
		cards.add(new Card(1, "Presse", "press1", CardType.GRAY, addRArray(new Resource(1, ResourceType.PAPYRUS)), null, null, null));
		cards.add(new Card(2, "Presse", "press2", CardType.GRAY, addRArray(new Resource(1, ResourceType.PAPYRUS)), null, null, null));
		cards.add(new Card(2, "Bildhauerei", "quarry", CardType.BROWN, addRArray(new Resource(2, ResourceType.STONE)), addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(2, "Saegewerk", "sawmill", CardType.BROWN, addRArray(new Resource(2, ResourceType.WOOD)), addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(ResourceType.TABLET, 2, "Schule", "school", CardType.GREEN, null, addRArray(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.PAPYRUS)), null, null));
		cards.add(new Card(3, "Gilde der Wissenschaftler", "scientistsguild", CardType.PURPLE, null,
				addRArray(new Resource(2, ResourceType.WOOD), new Resource(2, ResourceType.ORE), new Resource(1, ResourceType.PAPYRUS)), null, null)); // effect in playerController
		cards.add(new Card(ResourceType.TABLET, 1, "Skriptorium", "scriptorium", CardType.GREEN, null, addRArray(new Resource(1, ResourceType.PAPYRUS)), null, null));
		cards.add(new Card(3, "Senat", "senate", CardType.BLUE, null, addRArray(new Resource(2, ResourceType.WOOD), new Resource(1, ResourceType.STONE), new Resource(1, ResourceType.ORE)),
				new String[] { "library" }, addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addVictoryPoints(6); })), 6));
		cards.add(new Card(3, "Gilde der Reeder", "shipownersguild", CardType.PURPLE, null,
				addRArray(new Resource(3, ResourceType.WOOD), new Resource(1, ResourceType.GLASS), new Resource(1, ResourceType.PAPYRUS)), null, addEArray(new Effect(EffectType.AT_MATCH_END,
						(player, state, twoPlayers) -> { player.addVictoryPoints(player.getBoard().getResources().size() + player.getBoard().getGuilds().size()); }))));
		cards.add(new Card(3, "Belagerungsmaschinen", "siegeworkshop", CardType.RED, addRArray(new Resource(3, ResourceType.MILITARY)),
				addRArray(new Resource(3, ResourceType.BRICK), new Resource(1, ResourceType.WOOD)), new String[] { "laboratory" }, null));
		cards.add(new Card(3, "Gilde der Spione", "spiesguild", CardType.PURPLE, null, addRArray(new Resource(3, ResourceType.BRICK), new Resource(1, ResourceType.GLASS)), null,
				addEArray(new Effect(EffectType.AT_MATCH_END, (player, state, twoPlayers) -> {
					Player left = Main.getSWController().getPlayerController().getLeftNeighbour(state, player);

					int leftV = left.getBoard().getMilitary().size();
					int rightV = twoPlayers ? 0 : Main.getSWController().getPlayerController().getRightNeighbour(state, player).getBoard().getMilitary().size();
					player.addVictoryPoints(leftV + rightV);
				}))));
		cards.add(new Card(2, "Staelle", "stables", CardType.RED, addRArray(new Resource(2, ResourceType.MILITARY)),
				addRArray(new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.BRICK), new Resource(1, ResourceType.WOOD)), new String[] { "apothecary" }, null));
		cards.add(new Card(2, "Statue", "statue", CardType.BLUE, null, addRArray(new Resource(1, ResourceType.WOOD), new Resource(2, ResourceType.ORE)), new String[] { "theater" },
				addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addVictoryPoints(4); })), 4));
		cards.add(new Card(1, "Befestigungsanlage", "stockade", CardType.RED, addRArray(new Resource(1, ResourceType.MILITARY)), addRArray(new Resource(1, ResourceType.WOOD)), null, null));
		cards.add(new Card(1, "Steinbruch", "stonepit", CardType.BROWN, addRArray(new Resource(1, ResourceType.STONE)), null, null, null));
		cards.add(new Card(3, "Gilde der Strategen", "strategistsguild", CardType.PURPLE, null,
				addRArray(new Resource(2, ResourceType.ORE), new Resource(1, ResourceType.STONE), new Resource(1, ResourceType.CLOTH)), null,
				addEArray(new Effect(EffectType.AT_MATCH_END, (player, state, twoPlayers) -> {
					Player left = Main.getSWController().getPlayerController().getLeftNeighbour(state, player);
					int leftV = left.getLosePoints();
					int rightV = twoPlayers ? 0 : Main.getSWController().getPlayerController().getRightNeighbour(state, player).getLosePoints();
					player.addVictoryPoints(leftV + rightV);
				}))));
		cards.add(new Card(ResourceType.GEAR, 3, "Studierzimmer", "study", CardType.GREEN, null,
				addRArray(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.PAPYRUS), new Resource(1, ResourceType.CLOTH)), new String[] { "school" }, null));
		cards.add(new Card(1, "Taverne", "tavern", CardType.YELLOW, null, null, null, addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> player.addCoins(5)))));
		cards.add(new Card(2, "Temple", "temple", CardType.BLUE, null, addRArray(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.BRICK), new Resource(1, ResourceType.GLASS)),
				new String[] { "altar" }, addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addVictoryPoints(3); })), 3));
		cards.add(new Card(1, "Theater", "theater", CardType.BLUE, null, null, null, addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addVictoryPoints(2); })), 2));
		cards.add(new Card(1, "Forstwirtschaft", "timberyard", CardType.BROWN, addRArray(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.STONE)),
				addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(3, "Rathaus", "townhall", CardType.BLUE, null, addRArray(new Resource(2, ResourceType.STONE), new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.GLASS)), null,
				addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> { player.addVictoryPoints(6); })), 6));
		cards.add(new Card(3, "Gilde der Haendler", "tradersguild", CardType.PURPLE, null,
				addRArray(new Resource(1, ResourceType.CLOTH), new Resource(1, ResourceType.PAPYRUS), new Resource(1, ResourceType.GLASS)), null,
				addEArray(new Effect(EffectType.AT_MATCH_END, (player, state, twoPlayers) -> {
					Player left = Main.getSWController().getPlayerController().getLeftNeighbour(state, player);
					int leftV = left.getBoard().getTrade().size();
					int rightV = twoPlayers ? 0 : Main.getSWController().getPlayerController().getRightNeighbour(state, player).getBoard().getTrade().size();
					player.addVictoryPoints(leftV + rightV);
				}))));
		cards.add(new Card(2, "Trainingsgelaende", "trainingground", CardType.RED, addRArray(new Resource(2, ResourceType.MILITARY)),
				addRArray(new Resource(2, ResourceType.ORE), new Resource(1, ResourceType.WOOD)), null, null));
		cards.add(new Card(1, "Baumschule", "treefarm", CardType.BROWN, addRArray(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.BRICK)),
				addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(ResourceType.TABLET, 3, "Universitaet", "university", CardType.GREEN, null,
				addRArray(new Resource(2, ResourceType.WOOD), new Resource(1, ResourceType.PAPYRUS), new Resource(1, ResourceType.GLASS)), new String[] { "library" }, null));
		cards.add(new Card(2, "Weinberg", "vineyard", CardType.YELLOW, null, null, null, addEArray(new Effect(EffectType.WHEN_PLAYED, (player, state, twoPlayers) -> {
			Player left = Main.getSWController().getPlayerController().getLeftNeighbour(state, player);
			int count = 0;
			for (Card el : left.getBoard().getResources())
				if (el.getType() == CardType.BROWN)
					count++;
			if (!twoPlayers) {
				Player right = Main.getSWController().getPlayerController().getRightNeighbour(state, player);
				for (Card el : right.getBoard().getResources())
					if (el.getType() == CardType.BROWN)
						count++;
			}
			for (Card el : player.getBoard().getResources())
				if (el.getType() == CardType.BROWN)
					count++;
			player.addCoins(count);
		}))));
		cards.add(new Card(2, "Mauern", "walls", CardType.RED, addRArray(new Resource(2, ResourceType.MILITARY)), addRArray(new Resource(3, ResourceType.STONE)), null, null));
		cards.add(new Card(1, "Kontor West", "westtradingpost", CardType.YELLOW, null, null, null, null));
		cards.add(new Card(3, "Gilde der Arbeiter", "workersguild", CardType.PURPLE, null,
				addRArray(new Resource(2, ResourceType.ORE), new Resource(1, ResourceType.BRICK), new Resource(1, ResourceType.STONE), new Resource(1, ResourceType.WOOD)), null,
				addEArray(new Effect(EffectType.AT_MATCH_END, (player, state, twoPlayers) -> {
					Player left = Main.getSWController().getPlayerController().getLeftNeighbour(state, player);
					int count = 0;
					for (Card el : left.getBoard().getResources())
						if (el.getType() == CardType.BROWN)
							count++;
					if (!twoPlayers) {
						Player right = Main.getSWController().getPlayerController().getRightNeighbour(state, player);
						for (Card el : right.getBoard().getResources())
							if (el.getType() == CardType.BROWN)
								count++;
					}
					player.addVictoryPoints(count);
				}))));
		cards.add(new Card(ResourceType.GEAR, 1, "Werkstatt", "workshop", CardType.GREEN, null, addRArray(new Resource(1, ResourceType.GLASS)), null, null));
		addDescriptions(cards);

		return cards;
	}

	/**
	 * generates CardStack for given number of players
	 * 
	 * @param players list of all players
	 * @return cards list of all necessary cards for players
	 */
	public ArrayList<Card> generateCardStack(ArrayList<Player> players) {
		return generateCardStack(players.size());
	}

	/**
	 * generates CardStack for given number of players
	 * 
	 * @param playersize players amount
	 * @return cards list of all necessary cards for players
	 */
	public ArrayList<Card> generateCardStack(int playersize) {
		ArrayList<Card> cards = loadAllCards();

		// Clone cards depending on player number
		final int TWO = 2;
		if (playersize == TWO)
			playersize++;
		ArrayList<Card> toadd = new ArrayList<Card>();
		ArrayList<Card> guilds = new ArrayList<Card>();

		for (int i = 0; i < cards.size(); i++) {
			int[] sizes = countCards.get(cards.get(i).getInternalName());

			if (sizes[0] == 0) {
				guilds.add(cards.get(i));
				continue;
			}
			if (sizes[0] <= playersize)
				toadd.add(cards.get(i));
			if (sizes[1] == 0)
				continue;
			if (sizes[1] <= playersize)
				toadd.add(new Card(cards.get(i)));
			if (sizes[2] == 0)
				continue;
			if (sizes[2] <= playersize)
				toadd.add(new Card(cards.get(i)));
		}

		// Add Guild Cards
		Collections.shuffle(guilds);
		for (int i = 0; i < playersize + 2; i++)
			toadd.add(guilds.get(i));

		cards = toadd;

		// shuffle cards
		Collections.shuffle(cards);

		return cards;
	}

	/**
	 * adds descriptions to cards in list
	 * 
	 * @param cards list of cards
	 */
	private void addDescriptions(ArrayList<Card> cards) {
		for (Card card : cards) {
			StringBuilder des = new StringBuilder(card.getName() + "\n\n");
			if (card.getRequired() == null)
				des.append("Kosten: keine\n");
			else
				des.append("Kosten: " + resToString(card.getRequired()));

			switch (card.getType()) {
			case GRAY:
			case BROWN:
				des.append("\r\nProduziert: " + resToString(card.getProducing()));
				break;
			case RED:
				des.append("\r\nMilitaer: " + resToString(card.getProducing()));
				break;
			case BLUE:
				des.append("\r\n+" + card.getvPoints() + " Siegpunkte");
				break;
			case GREEN:
				des.append("\r\nHinweis: Die Forschungsgebaeude bringen auf zwei verschiedene Weisen Siegpunkte\r\n" + "ein: Es gibt Punkte fuer Gruppen identischer Symbole und fuer jeweils drei\r\n"
						+ "verschiedene Symbole.\r\n" + "Die Siegpunkte, die der Spieler auf diese beiden Weisen erhaelt,\r\n" + "werden addiert.");
				break;
			case PURPLE:
				des.append("\r\n");
				switch (card.getInternalName()) {
				case "buildersguild":
					des.append("1 Siegpunkt pro\r\n" + "Bauabschnitt der Weltwunder, die in den\r\n" + "beiden Nachbarstaedten UND in der eigenen\r\n" + "Stadt vollendet worden sind.");
					break;
				case "craftsmensguild":
					des.append("1 Siegpunkt fuer jede graue\r\n" + "Karte in den beiden Nachbarstaedten.");
					break;
				case "magistratesguild":
					des.append("1 Siegpunkt fuer jede blaue\r\n" + "Karte in den beiden Nachbarstaedten.");
					break;
				case "philosophersguild":
					des.append("1 Siegpunkt fuer jede\r\n" + "gruene Karte in den beiden Nachbarstaedten.");
					break;
				case "scientistsguild":
					des.append("Der Spieler profi\r\n" + "tiert nach seiner Wahl von einem der drei\r\n" + "Forschungssymbole.");
					break;
				case "shipownersguild":
					des.append("1 Siegpunkt fuer jede braune, graue\r\n" + "und violette Karte in der eigenen Stadt.\r\n" + "Hinweis: Die Gilde der Reeder selbst zaehlt dabei auch mit.");
					break;
				case "spiesguild":
					des.append("1 Siegpunkt fuer jede rote\r\n" + "Karte in den beiden Nachbarstaedten.");
					break;
				case "strategistsguild":
					des.append("1 Siegpunkt fuer jeden Niederlage-\r\n" + "Marker in den Nachbarstaedten.");
					break;
				case "tradersguild":
					des.append("1 Siegpunkt fuer jede gelbe\r\n" + "Karte in den beiden Nachbarstaedten.");
					break;
				case "workersguild":
					des.append("1 Siegpunkt fuer jede braune\r\n" + "Karte in den beiden Nachbarstaedten.");
					break;
				default:
					break;
				}
				break;
			case YELLOW:
				des.append("\r\n");
				switch (card.getInternalName()) {
				case "arena":
					des.append("Diese Karte bringt 3 Muenzen je Bauabschnitt des Weltwunders,\r\n" + "der bereits vollendet ist, wenn die Karte ins Spiel kommt\r\n"
							+ "(also 3, 6, 9 oder 12 Muenzen). Am Ende der Partie bringt\r\n" + "die Karte 1 Siegpunkt pro Bauabschnitt des Weltwunders\r\n"
							+ "ein, der im Laufe der Partie vollendet worden ist (also 1, 2,\r\n" + "3 oder 4 Siegpunkte).");
					break;
				case "bazar":
					des.append("Diese Karte bringt 2 Muenzen fuer jede graue Karte in\r\n" + "den Staedten des Spielers UND seiner beiden Nachbarn.\r\n"
							+ "Hinweis: Dabei zaehlen auch Gebaeude grauer Karten,\r\n" + "die in derselben Runde wie der Basar in den\r\n" + "Nachbarstaedten gebaut worden sind.");
					break;
				case "caravansery":
					des.append("Die Karte produziert jede Runde nach Wahl des\r\n" + "Spielers einen der vier abgebildeten Rohstoffe.\r\n" + "Hinweis: Diesen Ressourcen koennen die Nachbarstaedte\r\n"
							+ "nicht kaufen.");
					break;
				case "chamberofcommerce":
					des.append("Die Karte bringt 1 Muenze fuer jede graue Karte, die sich in der\r\n" + "Stadt des Spielers befi ndet, wenn die Karte ins Spiel gebracht\r\n"
							+ "wird. Am Ende der Partie bringt die Karte 2 Siegpunkte fuer jede\r\n" + "graue Karte in der Stadt des Spielers.");
					break;
				case "easttradingpost":
					des.append("Wenn der Spieler diese Karte ins Spiel gebracht\r\n" + "hat, zahlt er ab der naechsten Runde nur noch\r\n" + "1 statt 2 Muenzen, wenn er von seinem rechten\r\n"
							+ "Nachbarn Rohstoffe kauft.");
					break;
				case "forum":
					des.append("Die Karte produziert jede Runde nach Wahl des\r\n" + "Spielers eines der drei Manufakturprodukte.\r\n" + "Hinweis: Diesen Ressourcen koennen die Nachbarstaedte\r\n"
							+ "nicht kaufen.");
					break;
				case "haven":
					des.append("Die Karte bringt 1 Muenze fuer jede braune Karte, die sich in der\r\n" + "Stadt des Spielers befi ndet, wenn die Karte ins Spiel gebracht\r\n"
							+ "wird. Am Ende der Partie bringt die Karte 1 Siegpunkt fuer jede\r\n" + "braune Karte in der Stadt des Spielers.");
					break;
				case "lighthouse":
					des.append("Die Karte bringt 1 Muenze fuer jede gelbe Karte, die sich in der\r\n" + "Stadt des Spielers befi ndet, wenn die Karte ins Spiel gebracht\r\n"
							+ "wird. Am Ende der Partie bringt die Karte 1 Siegpunkt fuer jede\r\n" + "gelbe Karte in der Stadt des Spielers.");
					break;
				case "marketplace":
					des.append("Wenn der Spieler diese Karte ins Spiel gebracht\r\n" + "hat, zahlt er ab der naechsten Runde nur noch\r\n" + "1 statt 2 Muenzen, wenn er von einem seiner\r\n"
							+ "beiden Nachbarn Manufakturprodukte kauft.");
					break;
				case "tavern":
					des.append("Die Karte bringt 5 Muenzen ein. Der Spieler\r\n" + "erhaelt die Muenzen aus dem allgemeinen Vorrat, sobald er die Karte\r\n" + "ins Spiel bringt.");
					break;
				case "vineyard":
					des.append("Diese Karte bringt 1 Muenze fuer jede braune Karte in\r\n" + "den Staedten des Spielers UND seiner beiden Nachbarn.\r\n"
							+ "Hinweis: Dabei zaehlen auch Gebaeude brauner Karten,\r\n" + "die in derselben Runde wie die Presse in den\r\n" + "Nachbarstaedten gebaut worden sind.");
					break;
				case "westtradingpost":
					des.append("Wenn der Spieler diese Karte ins Spiel gebracht\r\n" + "hat, zahlt er ab der naechsten Runde nur noch\r\n" + "1 statt 2 Muenzen, wenn er von seinem linken\r\n"
							+ "Nachbarn Rohstoffe kauft.");
					break;
				default:
					break;
				}
				break;
			default:
				return;
			}

			// Getting dependencies
			ArrayList<String> dep = new ArrayList<String>();
			for (Card comp : cards) {
				if (comp.getDependencies() == null)
					continue;
				if (Arrays.asList(comp.getDependencies()).contains(card.getInternalName()))
					dep.add(comp.getName());
			}
			if (!dep.isEmpty()) {
				des.append("\nKostenloses Bauen: ");
				for (int i = 0; i < dep.size(); i++) {
					des.append(dep.get(i));
					if (i != dep.size() - 1) {
						des.append(",");
					}
				}
			}

			card.setDescription(des.toString());
		}
	}

	/**
	 * returns descriptions for the resources of a card
	 * 
	 * @param resources list of resources
	 * @return string string for description of resources
	 */
	private static String resToString(ArrayList<Resource> resources) {
		StringBuilder string = new StringBuilder();
		final int ONE = 1;
		for (int i = 0; i < resources.size(); i++) {
			Resource res = resources.get(i);
			switch (res.getType()) {
			// WOOD, BRICK, ORE, STONE, PAPYRUS, GLASS, CLOTH, COINS, MILITARY
			case WOOD:
				string.append("" + res.getQuantity() + " " + "Holz");
				break;
			case BRICK:
				string.append("" + res.getQuantity() + " " + "Ziegel");
				break;
			case CLOTH:
				string.append("" + res.getQuantity() + " " + "Stoff");
				break;
			case COINS:
				if (res.getQuantity() == ONE)
					string.append("" + res.getQuantity() + " " + "Muenze");
				else
					string.append("" + res.getQuantity() + " " + "Muenzen");
				break;
			case GLASS:
				string.append("" + res.getQuantity() + " " + "Glas");
				break;
			case MILITARY:
				if (res.getQuantity() == ONE)
					string.append("" + res.getQuantity() + " " + "Schild");
				else
					string.append("" + res.getQuantity() + " " + "Schilder");
				break;
			case ORE:
				string.append("" + res.getQuantity() + " " + "Erz");
				break;
			case PAPYRUS:
				string.append("" + res.getQuantity() + " " + "Papyros");
				break;
			case STONE:
				if (res.getQuantity() == ONE)
					string.append("" + res.getQuantity() + " " + "Stein");
				else
					string.append("" + res.getQuantity() + " " + "Steine");
				break;
			default:
				break;
			}
			if (i != resources.size() - 1)
				string.append(", ");
		}
		return string.toString();
	}

	/**
	 * returns true if player has card
	 * 
	 * @param player   chosen player
	 * @param cardname name of card
	 * @return boolean true if player has card
	 */
	public boolean hasCard(Player player, String cardname) {
		WonderBoard board = player.getBoard();

		for (Card card : board.getResources())
			if (card.getInternalName().equalsIgnoreCase(cardname))
				return true;
		for (Card card : board.getCivil())
			if (card.getInternalName().equalsIgnoreCase(cardname))
				return true;
		for (Card card : board.getGuilds())
			if (card.getInternalName().equalsIgnoreCase(cardname))
				return true;
		for (Card card : board.getMilitary())
			if (card.getInternalName().equalsIgnoreCase(cardname))
				return true;
		for (Card card : board.getResearch())
			if (card.getInternalName().equalsIgnoreCase(cardname))
				return true;
		for (Card card : board.getTrade())
			if (card.getInternalName().equalsIgnoreCase(cardname))
				return true;

		return false;
	}

	/**
	 * adds resources
	 * 
	 * @param ressource resources to be added
	 * @return array list with resources
	 */
	private ArrayList<Resource> addRArray(Resource... ressource) {
		ArrayList<Resource> array = new ArrayList<Resource>();
		for (Resource res : ressource) {
			array.add(res);
		}
		return array;
	}

	/**
	 * adds effects
	 * 
	 * @param effect effects to be added
	 * @return array list with effects
	 */
	private ArrayList<Effect> addEArray(Effect... effect) {
		ArrayList<Effect> array = new ArrayList<Effect>();
		for (Effect eff : effect) {
			array.add(eff);
		}
		return array;
	}

	/**
	 * sell a card and add it to the trash
	 * 
	 * @param card   card to be sold
	 * @param player player
	 */
	public void sellCard(Card card, Player player) {
		swController.getSoundController().play(Sound.COIN);
		player.addCoins(3);
		player.getHand().remove(card);
		player.setChooseCard(null);
		swController.getGame().getCurrentGameState().getTrash().add(card);
	}

	/**
	 * place a card on the WonderBoard and pay for the required resources if necessary
	 * 
	 * @param card      card
	 * @param player    player
	 * @param trade     trade option or null
	 * @param freeBuild true if olympia ability is used
	 */
	public void placeCard(Card card, Player player, TradeOption trade, boolean freeBuild) {
		placeCard(Main.getSWController().getGame().getCurrentGameState(), card, player, trade, freeBuild);
	}

	/**
	 * place a card on the WonderBoard and pay for the required resources if necessary
	 * 
	 * @param state     game state
	 * @param card      card to be placed
	 * @param player    player
	 * @param trade     the trade that was made or null if the card is built with own resources
	 * @param freeBuild true if the player uses olympia ability to build this card
	 */
	public void placeCard(GameState state, Card card, Player player, TradeOption trade, boolean freeBuild) {
		if (trade != null && trade.getLeftCost() + trade.getRightCost() > player.getCoins()) {
			System.err.println("[Card Controller] place card cancelled");
			System.err.println("[Card Controller] card: " + card.getName());
			System.err.println("[Card Controller] player: " + player.getName());
			System.err.println("[Card Controller] trade: " + trade.toString());
			return;
		}

		swController.getSoundController().play(Sound.BUILD);
		player.getHand().remove(card);
		player.setChooseCard(null);
		player.getBoard().addCard(card);
		if (card.getEffects() != null) {
			for (Effect effect : card.getEffects()) {
				if (effect.getType() == EffectType.WHEN_PLAYED)
					effect.run(player, swController.getGame());
			}
		}

		if (!freeBuild && card.getRequired() != null) {
			for (Resource resource : card.getRequired()) {
				if (resource.getType() == ResourceType.COINS)
					player.addCoins(-resource.getQuantity());
			}
		}

		if (trade != null) {
			swController.getPlayerController().doTrade(state, player, trade);
		}
	}

	/**
	 * place a card in the next WonderBoard slot and pay for the required resources if necessary
	 * 
	 * @param card   card
	 * @param player player
	 * @param trade  trade or null
	 */
	public void setSlotCard(Card card, Player player, TradeOption trade) {
		setSlotCard(Main.getSWController().getGame().getCurrentGameState(), card, player, trade);
	}

	/**
	 * place a card in the next WonderBoard slot and pay for the required resources if necessary
	 * 
	 * @param state  game state
	 * @param card   card to be places
	 * @param player player
	 * @param trade  the trade that was made or null if the wonder is built with own resources
	 */
	public void setSlotCard(GameState state, Card card, Player player, TradeOption trade) {

		if (trade != null && trade.getLeftCost() + trade.getRightCost() > player.getCoins())
			return;
		if (player.getBoard().isFilled(2))
			return;

		swController.getSoundController().play(Sound.CHOOSE_CARD);
		player.getHand().remove(card);
		player.setChooseCard(null);
		int slot = player.getBoard().nextSlot();
		player.getBoard().fill(slot);
		player.getBoard().setAgeOfSlotCards(slot, card.getAge());
		switch (slot) {
		case 0:
			player.getBoard().slot1();
			break;
		case 1:
			player.getBoard().slot2();
			break;
		case 2:
			player.getBoard().slot3();
			break;
		}

		if (trade != null) {
			swController.getPlayerController().doTrade(state, player, trade);
		}
	}

	/**
	 * get Card by name
	 * 
	 * @param cardname card's internal name
	 * @return Card object with the given name or null if no such card was found
	 */
	public Card getCard(String cardname) {
		return getCard(loadedCards, cardname);
	}

	/**
	 * get Card by name
	 * 
	 * @param cards    list of cards
	 * @param cardname card's internal name
	 * @return Card object with the given name or null if no such card was found
	 */
	public Card getCard(ArrayList<Card> cards, String cardname) {
		for (Card card : cards)
			if (card.getInternalName().equalsIgnoreCase(cardname))
				return card;
		return null;
	}

	/**
	 * generate the card top preview
	 * 
	 * @param card card
	 * @return a subimage of the card
	 */
	public Image getPreviewImage(Card card) {
		BufferedImage full = null;
		try {
			full = ImageIO.read(Main.class.getClassLoader().getResourceAsStream(card.getImage()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		switch (card.getType()) {
		case BROWN:
			return getResourceImage(card.getInternalName(), full);
		case GRAY:
			return getManufactureImage(card.getInternalName(), full);
		case BLUE:
			return getCivilImage(card.getInternalName(), full);
		case GREEN:
			return getResearchImage(card.getInternalName(), full);
		case RED:
			return getMilitaryImage(card.getInternalName(), full);
		case YELLOW:
			return getTradeImage(card.getInternalName(), full);
		case PURPLE:
			return getGuildImage(card.getInternalName(), full);
		}
		return null;
	}

	/**
	 * get image
	 * 
	 * @param cardname card name
	 * @param full     full image
	 * @return preview image
	 */
	private Image getResourceImage(String cardname, BufferedImage full) {
		switch (cardname) {
		case "brickyard":
			return getSubimage(full, new Rectangle(42, 10, 106, 50));
		case "claypit":
			return getSubimage(full, new Rectangle(39, 8, 116, 50));
		case "claypool":
			return getSubimage(full, new Rectangle(64, 12, 54, 50));
		case "excavation":
			return getSubimage(full, new Rectangle(40, 8, 116, 50));
		case "forestcave":
			return getSubimage(full, new Rectangle(41, 8, 116, 50));
		case "foundry":
			return getSubimage(full, new Rectangle(44, 9, 106, 50));
		case "lumberyard":
			return getSubimage(full, new Rectangle(63, 12, 54, 50));
		}
		return getResourceImage2(cardname, full);
	}

	/**
	 * get image 2
	 * 
	 * @param cardname card name
	 * @param full     full image
	 * @return preview image
	 */
	private Image getResourceImage2(String cardname, BufferedImage full) {
		switch (cardname) {
		case "mine":
			return getSubimage(full, new Rectangle(40, 9, 116, 50));
		case "orevein":
			return getSubimage(full, new Rectangle(62, 11, 54, 50));
		case "quarry":
			return getSubimage(full, new Rectangle(42, 7, 106, 50));
		case "sawmill":
			return getSubimage(full, new Rectangle(41, 14, 106, 50));
		case "stonepit":
			return getSubimage(full, new Rectangle(63, 9, 54, 50));
		case "timberyard":
			return getSubimage(full, new Rectangle(40, 9, 116, 50));
		case "treefarm":
			return getSubimage(full, new Rectangle(40, 10, 116, 50));
		}
		return null;
	}

	/**
	 * get image
	 * 
	 * @param cardname card name
	 * @param full     full image
	 * @return preview image
	 */
	private Image getManufactureImage(String cardname, BufferedImage full) {
		switch (cardname) {
		case "glassworks1":
		case "glassworks2":
			return getSubimage(full, new Rectangle(64, 10, 54, 50));
		case "loom1":
		case "loom2":
			return getSubimage(full, new Rectangle(64, 10, 54, 50));
		case "press1":
		case "press2":
			return getSubimage(full, new Rectangle(64, 8, 54, 50));
		}
		return null;
	}

	/**
	 * get preview of blue card
	 * 
	 * @param cardname card name
	 * @param full     full card
	 * @return preview image
	 */
	private Image getCivilImage(String cardname, BufferedImage full) {
		switch (cardname) {
		case "altar":
			return getSubimage(full, new Rectangle(59, 12, 60, 50));
		case "aqueduct":
			return getSubimage(full, new Rectangle(73, 12, 60, 50));
		case "baths":
			return getSubimage(full, new Rectangle(65, 10, 60, 50));
		case "courthouse":
			return getSubimage(full, new Rectangle(74, 11, 60, 50));
		case "gardens":
			return getSubimage(full, new Rectangle(73, 12, 60, 50));
		case "palace":
			return getSubimage(full, new Rectangle(67, 12, 60, 50));
		case "pantheon":
			return getSubimage(full, new Rectangle(71, 12, 60, 50));
		}
		return getCivilImage2(cardname, full);
	}

	/**
	 * get preview of blue card 2
	 * 
	 * @param cardname card name
	 * @param full     full card
	 * @return preview image
	 */
	private Image getCivilImage2(String cardname, BufferedImage full) {
		switch (cardname) {
		case "pawnshop":
			return getSubimage(full, new Rectangle(60, 11, 60, 50));
		case "senate":
			return getSubimage(full, new Rectangle(73, 11, 60, 50));
		case "statue":
			return getSubimage(full, new Rectangle(73, 11, 60, 50));
		case "temple":
			return getSubimage(full, new Rectangle(73, 12, 60, 50));
		case "theater":
			return getSubimage(full, new Rectangle(60, 12, 60, 50));
		case "townhall":
			return getSubimage(full, new Rectangle(69, 12, 60, 50));
		}
		return null;
	}

	/**
	 * get image
	 * 
	 * @param cardname card name
	 * @param full     full image
	 * @return preview image
	 */
	private Image getResearchImage(String cardname, BufferedImage full) {
		switch (cardname) {
		case "academy":
			return getSubimage(full, new Rectangle(78, 10, 51, 50));
		case "apothecary":
			return getSubimage(full, new Rectangle(72, 8, 51, 50));
		case "dispensary":
			return getSubimage(full, new Rectangle(77, 9, 51, 50));
		case "laboratory":
			return getSubimage(full, new Rectangle(78, 9, 51, 50));
		case "library":
			return getSubimage(full, new Rectangle(77, 10, 51, 50));
		case "lodge":
			return getSubimage(full, new Rectangle(76, 12, 51, 50));
		}
		return getResearchImage2(cardname, full);
	}

	/**
	 * get image 2
	 * 
	 * @param cardname card name
	 * @param full     full image
	 * @return preview image
	 */
	private Image getResearchImage2(String cardname, BufferedImage full) {
		switch (cardname) {
		case "observatory":
			return getSubimage(full, new Rectangle(78, 10, 51, 50));
		case "school":
			return getSubimage(full, new Rectangle(72, 11, 51, 50));
		case "scriptorium":
			return getSubimage(full, new Rectangle(72, 9, 51, 50));
		case "study":
			return getSubimage(full, new Rectangle(78, 11, 51, 50));
		case "university":
			return getSubimage(full, new Rectangle(78, 12, 51, 50));
		case "workshop":
			return getSubimage(full, new Rectangle(72, 9, 51, 50));
		}
		return null;
	}

	/**
	 * get image
	 * 
	 * @param cardname card name
	 * @param full     full image
	 * @return preview image
	 */
	private Image getMilitaryImage(String cardname, BufferedImage full) {
		switch (cardname) {
		case "archeryrange":
			return getSubimage(full, new Rectangle(53, 10, 100, 50));
		case "arsenal":
			return getSubimage(full, new Rectangle(45, 8, 124, 50));
		case "barracks":
			return getSubimage(full, new Rectangle(72, 9, 51, 50));
		case "circus":
			return getSubimage(full, new Rectangle(45, 8, 124, 50));
		case "fortifications":
			return getSubimage(full, new Rectangle(46, 9, 124, 50));
		case "guardtower":
			return getSubimage(full, new Rectangle(72, 10, 51, 50));
		}
		return getMilitaryImage2(cardname, full);
	}

	/**
	 * get image 2
	 * 
	 * @param cardname card name
	 * @param full     full image
	 * @return preview image
	 */
	private Image getMilitaryImage2(String cardname, BufferedImage full) {
		switch (cardname) {
		case "siegeworkshop":
			return getSubimage(full, new Rectangle(45, 10, 124, 50));
		case "stables":
			return getSubimage(full, new Rectangle(54, 11, 100, 50));
		case "stockade":
			return getSubimage(full, new Rectangle(72, 12, 51, 50));
		case "traininggroud":
			return getSubimage(full, new Rectangle(48, 10, 100, 50));
		case "walls":
			return getSubimage(full, new Rectangle(48, 8, 100, 50));
		}
		return null;
	}

	/**
	 * get image
	 * 
	 * @param cardname card name
	 * @param full     full image
	 * @return preview image
	 */
	private Image getTradeImage(String cardname, BufferedImage full) {
		switch (cardname) {
		case "arena":
			return getSubimage(full, new Rectangle(66, 10, 72, 50));
		case "bazar":
			return getSubimage(full, new Rectangle(32, 10, 119, 56));
		case "caravansery":
			return getSubimage(full, new Rectangle(45, 18, 126, 32));
		case "chamberofcommerce":
			return getSubimage(full, new Rectangle(65, 10, 60, 50));
		case "easttradingpost":
			return getSubimage(full, new Rectangle(40, 8, 126, 54));
		case "forum":
			return getSubimage(full, new Rectangle(44, 14, 128, 43));
		}
		return getTradeImage2(cardname, full);
	}

	/**
	 * get image 2
	 * 
	 * @param cardname card name
	 * @param full     full image
	 * @return preview image
	 */
	private Image getTradeImage2(String cardname, BufferedImage full) {
		switch (cardname) {
		case "haven":
			return getSubimage(full, new Rectangle(71, 10, 60, 50));
		case "lighthouse":
			return getSubimage(full, new Rectangle(71, 10, 60, 50));
		case "marketplace":
			return getSubimage(full, new Rectangle(35, 11, 125, 50));
		case "tavern":
			return getSubimage(full, new Rectangle(63, 8, 55, 50));
		case "vineyard":
			return getSubimage(full, new Rectangle(32, 10, 119, 56));
		case "westtradingpost":
			return getSubimage(full, new Rectangle(33, 10, 127, 50));
		}
		return null;
	}

	/**
	 * get image
	 * 
	 * @param cardname card name
	 * @param full     full image
	 * @return preview image
	 */
	private Image getGuildImage(String cardname, BufferedImage full) {
		switch (cardname) {
		case "craftsmensguild":
		case "magistratesguild":
		case "philosophersguild":
		case "spiesguild":
		case "tradersguild":
		case "workersguild":
			return getSubimage(full, new Rectangle(36, 9, 130, 50));
		case "buildersguild":
			return getSubimage(full, new Rectangle(37, 16, 125, 52));
		case "scientistsguild":
			return getSubimage(full, new Rectangle(34, 9, 134, 50));
		case "shipownersguild":
			return getSubimage(full, new Rectangle(47, 10, 104, 50));
		case "strategistsguild":
			return getSubimage(full, new Rectangle(36, 12, 130, 50));
		}
		return null;
	}

	/**
	 * get a subimage of an image
	 * 
	 * @param img  full image
	 * @param rect rectangle
	 * @return subimage with the specified coordinates
	 */
	public Image getSubimage(BufferedImage img, Rectangle rect) {
		return SwingFXUtils.toFXImage(img.getSubimage((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight()), null);
	}

}