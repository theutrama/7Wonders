/**
 * The Card-Controller controls the ingame cards.
 */

package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

import java.util.Collections;

import model.board.WonderBoard;
import model.card.Card;
import model.card.CardType;
import model.card.Effect;
import model.card.EffectType;
import model.card.Resource;
import model.card.ResourceType;
import model.player.Player;

public class CardController {

	Map<String, int[]> countCards = new HashMap<>();

	private SevenWondersController swController;

	public CardController(SevenWondersController swController) {
		this.swController = swController;
		loadCards();
	}

	/**
	 * Loading all ingame cards with their parameters and frequency Matrix-scheme:
	 * amount of players when another card needs to be created
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
		countCards.put("circus", new int[] { 4, 6, 6 });
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

	public ArrayList<Card> generateCardStack(ArrayList<Player> players) {
		ArrayList<Card> cards = new ArrayList<Card>();

		cards.add(new Card(ResourceType.COMPASS, 3, "Akademie", "academy", CardType.GREEN, null,
				addRArray(new Resource(3, ResourceType.STONE), new Resource(1, ResourceType.GLASS)),
				new String[] { "school" }, null));
		cards.add(new Card(1, "Altar", "altar", CardType.BLUE, null, null, null,
				addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					p.addVictoryPoints(2);
				})), 2));
		cards.add(new Card(ResourceType.COMPASS, 1, "Apotheke", "apothecary", CardType.GREEN, null,
				addRArray(new Resource(3, ResourceType.CLOTH)), new String[] { "school" }, null));

		cards.add(
				new Card(2, "Aquädukt", "aqueduct", CardType.BLUE, null, addRArray(new Resource(3, ResourceType.STONE)),
						new String[] { "baths" }, addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
							p.addVictoryPoints(5);
						})), 5));
		cards.add(new Card(2, "Schießplatz", "archeryrange", CardType.RED,
				addRArray(new Resource(2, ResourceType.MILITARY)),
				addRArray(new Resource(2, ResourceType.WOOD), new Resource(1, ResourceType.ORE)),
				new String[] { "workshop" }, null));
		cards.add(new Card(3, "Arena", "arena", CardType.YELLOW, null,
				addRArray(new Resource(2, ResourceType.STONE), new Resource(1, ResourceType.ORE)),
				new String[] { "dispensary" }, addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					p.addCoins(3 * (p.getBoard().nextSlot() == -1 ? 3 : p.getBoard().nextSlot()));
				}), new Effect(EffectType.AT_MATCH_END, p -> {
					p.addVictoryPoints(p.getBoard().nextSlot() == -1 ? 3 : p.getBoard().nextSlot());
				}))));
		cards.add(new Card(3, "Waffenlager", "arsenal", CardType.RED, addRArray(new Resource(3, ResourceType.MILITARY)),
				addRArray(new Resource(2, ResourceType.WOOD), new Resource(1, ResourceType.ORE),
						new Resource(1, ResourceType.CLOTH)),
				new String[] { "workshop" }, null));
		cards.add(new Card(1, "Kaserne", "barracks", CardType.RED, addRArray(new Resource(1, ResourceType.MILITARY)),
				addRArray(new Resource(1, ResourceType.ORE)), new String[] { "workshop" }, null));
		cards.add(new Card(1, "Bäder", "baths", CardType.BLUE, null, addRArray(new Resource(1, ResourceType.STONE)),
				null, addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					p.addVictoryPoints(3);
				})), 3));
		cards.add(new Card(2, "Basar", "bazar", CardType.YELLOW, null, null, null,
				addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					int count = 0;
					for (Card el : swController.getPlayerController().getLeftNeighbour(p).getBoard().getResources())
						if (el.getType() == CardType.GRAY)
							count++;
					for (Card el : swController.getPlayerController().getRightNeighbour(p).getBoard().getResources())
						if (el.getType() == CardType.GRAY)
							count++;
					for (Card el : p.getBoard().getResources())
						if (el.getType() == CardType.GRAY)
							count++;
					p.addCoins(2 * count);
				}))));
		cards.add(new Card(2, "Ziegelbrennerei", "brickyard", CardType.BROWN,
				addRArray(new Resource(2, ResourceType.BRICK)), addRArray(new Resource(1, ResourceType.COINS)), null,
				null));
		cards.add(
				new Card(3, "Gilde der Baumeister", "buildersguild", CardType.PURPLE, null,
						addRArray(new Resource(2, ResourceType.BRICK), new Resource(2, ResourceType.STONE),
								new Resource(1, ResourceType.GLASS)),
						null, addEArray(new Effect(EffectType.AT_MATCH_END, p -> {
							int purple1 = swController.getPlayerController().getLeftNeighbour(p).getBoard().nextSlot();
							int purple2 = swController.getPlayerController().getRightNeighbour(p).getBoard().nextSlot();
							int purple3 = p.getBoard().nextSlot();
							purple1 = purple1 == -1 ? 3 : purple1;
							purple2 = purple2 == -1 ? 3 : purple2;
							purple3 = purple3 == -1 ? 3 : purple3;
							p.addVictoryPoints(purple1 + purple2 + purple3);
						}))));
		cards.add(new Card(2, "Karawanserei", "caravansery", CardType.YELLOW,
				addRArray(new Resource(1, ResourceType.BRICK), new Resource(1, ResourceType.STONE),
						new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.WOOD)),
				addRArray(new Resource(2, ResourceType.WOOD)), new String[] { "market" }, null));
		cards.add(new Card(3, "Handelskammer", "chamberofcommerce", CardType.YELLOW, null,
				addRArray(new Resource(2, ResourceType.BRICK)), null,
				addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					int count = 0;
					for (Card el : p.getBoard().getResources())
						if (el.getType() == CardType.GRAY)
							count++;
					p.addCoins(2 * count);
				}), new Effect(EffectType.AT_MATCH_END, p -> {
					int count = 0;
					for (Card el : p.getBoard().getResources())
						if (el.getType() == CardType.GRAY)
							count++;
					p.addVictoryPoints(2 * count);
				}))));
		cards.add(new Card(3, "Zirkus", "circus", CardType.RED, addRArray(new Resource(3, ResourceType.MILITARY)),
				addRArray(new Resource(3, ResourceType.STONE), new Resource(1, ResourceType.ORE)),
				new String[] { "trainingground" }, null));
		cards.add(new Card(1, "Tongrube", "claypit", CardType.BROWN,
				addRArray(new Resource(1, ResourceType.BRICK), new Resource(1, ResourceType.ORE)),
				addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(1, "Ziegelei", "claypit", CardType.BROWN, addRArray(new Resource(1, ResourceType.BRICK)),
				null, null, null));
		cards.add(new Card(2, "Gericht", "courthouse", CardType.BLUE, null,
				addRArray(new Resource(2, ResourceType.BRICK), new Resource(1, ResourceType.STONE)),
				new String[] { "scriptorium" }, addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					p.addVictoryPoints(4);
				})), 4));
		cards.add(new Card(3, "Gilde der Künstler", "craftsmensguild", CardType.PURPLE, null,
				addRArray(new Resource(2, ResourceType.STONE), new Resource(2, ResourceType.ORE)), null,
				addEArray(new Effect(EffectType.AT_MATCH_END, p -> {
					int count = 0;
					for (Card el : swController.getPlayerController().getLeftNeighbour(p).getBoard().getResources())
						if (el.getType() == CardType.GRAY)
							count++;
					for (Card el : swController.getPlayerController().getRightNeighbour(p).getBoard().getResources())
						if (el.getType() == CardType.GRAY)
							count++;
					p.addVictoryPoints(2 * count);
				}))));
		cards.add(new Card(ResourceType.COMPASS, 2, "Arzneiausgabe", "dispensary", CardType.GREEN, null,
				addRArray(new Resource(2, ResourceType.ORE), new Resource(1, ResourceType.GLASS)),
				new String[] { "apothecary" }, null));
		cards.add(new Card(1, "Kontor Ost", "easttradingpost", CardType.YELLOW, null, null, null, null));
		cards.add(new Card(1, "Ausgrabungsstätte", "excavation", CardType.BROWN,
				addRArray(new Resource(1, ResourceType.BRICK), new Resource(1, ResourceType.STONE)),
				addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(1, "Waldhöhle", "forestcave", CardType.BROWN,
				addRArray(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.ORE)),
				addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(3, "Verteidigungsanlage", "fortifications", CardType.RED,
				addRArray(new Resource(3, ResourceType.MILITARY)),
				addRArray(new Resource(3, ResourceType.ORE), new Resource(1, ResourceType.STONE)),
				new String[] { "walls" }, null));
		cards.add(new Card(2, "Forum", "forum", CardType.YELLOW,
				addRArray(new Resource(1, ResourceType.GLASS), new Resource(1, ResourceType.CLOTH),
						new Resource(1, ResourceType.PAPYRUS)),
				addRArray(new Resource(2, ResourceType.BRICK)), new String[] { "easttradingpost", "westtradingpost" },
				null));
		cards.add(new Card(2, "Giesserei", "foundry", CardType.BROWN, addRArray(new Resource(2, ResourceType.ORE)),
				addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(3, "Gärten", "gardens", CardType.BLUE, null,
				addRArray(new Resource(2, ResourceType.BRICK), new Resource(1, ResourceType.STONE)),
				new String[] { "statue" }, addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					p.addVictoryPoints(5);
				})), 5));
		cards.add(new Card(1, "Glashütte", "glassworks1", CardType.GRAY, addRArray(new Resource(1, ResourceType.GLASS)),
				null, null, null));
		cards.add(new Card(2, "Glashütte", "glassworks2", CardType.GRAY, addRArray(new Resource(1, ResourceType.GLASS)),
				null, null, null));
		cards.add(new Card(1, "Wachturm", "guardtower", CardType.RED, addRArray(new Resource(1, ResourceType.MILITARY)),
				addRArray(new Resource(1, ResourceType.BRICK)), null, null));
		cards.add(new Card(3, "Hafen", "haven", CardType.YELLOW, null,
				addRArray(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.ORE),
						new Resource(1, ResourceType.CLOTH)),
				new String[] { "forum" }, addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					int count = 0;
					for (Card el : p.getBoard().getResources())
						if (el.getType() == CardType.BROWN)
							count++;
					p.addCoins(count);
				}), new Effect(EffectType.AT_MATCH_END, p -> {
					int count = 0;
					for (Card el : p.getBoard().getResources())
						if (el.getType() == CardType.BROWN)
							count++;
					p.addVictoryPoints(count);
				}))));
		cards.add(new Card(ResourceType.GEAR, 2, "Laboratorium", "laboratory", CardType.GREEN, null,
				addRArray(new Resource(2, ResourceType.BRICK), new Resource(1, ResourceType.PAPYRUS)),
				new String[] { "workshop" }, null));
		cards.add(new Card(ResourceType.TABLET, 2, "Bibliothek", "library", CardType.GREEN, null,
				addRArray(new Resource(2, ResourceType.STONE), new Resource(1, ResourceType.CLOTH)),
				new String[] { "scriptorium" }, null));
		cards.add(new Card(3, "Leuchtturm", "lighthouse", CardType.YELLOW, null,
				addRArray(new Resource(2, ResourceType.BRICK)), new String[] { "caravansery" },
				addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					p.addCoins(p.getBoard().getTrade().size());
				}), new Effect(EffectType.AT_MATCH_END, p -> {
					p.addVictoryPoints(p.getBoard().getTrade().size());
				}))));
		cards.add(new Card(ResourceType.COMPASS, 3, "Loge", "lodge", CardType.GREEN, null,
				addRArray(new Resource(2, ResourceType.BRICK), new Resource(1, ResourceType.PAPYRUS),
						new Resource(1, ResourceType.CLOTH)),
				new String[] { "dispensary" }, null));
		cards.add(new Card(1, "Webstuhl", "loom1", CardType.GRAY, addRArray(new Resource(1, ResourceType.CLOTH)), null,
				null, null));
		cards.add(new Card(2, "Webstuhl", "loom2", CardType.GRAY, addRArray(new Resource(1, ResourceType.CLOTH)), null,
				null, null));
		cards.add(new Card(1, "Holzplatz", "lumberyard", CardType.BROWN, addRArray(new Resource(1, ResourceType.WOOD)),
				null, null, null));
		cards.add(
				new Card(3, "Gilde der Beamten", "magistratesguild", CardType.PURPLE, null,
						addRArray(new Resource(3, ResourceType.WOOD), new Resource(1, ResourceType.STONE),
								new Resource(1, ResourceType.CLOTH)),
						null, addEArray(new Effect(EffectType.AT_MATCH_END, p -> {
							p.addVictoryPoints(p.getBoard().getCivil().size());
						}))));
		cards.add(new Card(1, "Markt", "marketplace", CardType.YELLOW, null, null, null, null));
		cards.add(new Card(1, "Mine", "mine", CardType.BROWN,
				addRArray(new Resource(1, ResourceType.STONE), new Resource(1, ResourceType.ORE)),
				addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(ResourceType.GEAR, 3, "Observatorium", "observatory", CardType.GREEN, null,
				addRArray(new Resource(2, ResourceType.ORE), new Resource(1, ResourceType.PAPYRUS),
						new Resource(1, ResourceType.GLASS)),
				new String[] { "laboratory" }, null));
		cards.add(new Card(1, "Erzbergwerk", "orevein", CardType.BROWN, addRArray(new Resource(1, ResourceType.ORE)),
				null, null, null));
		cards.add(new Card(3, "Palast", "palace", CardType.BLUE, null,
				addRArray(new Resource(1, ResourceType.STONE), new Resource(1, ResourceType.ORE),
						new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.BRICK),
						new Resource(1, ResourceType.GLASS), new Resource(1, ResourceType.PAPYRUS),
						new Resource(1, ResourceType.CLOTH)),
				null, addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					p.addVictoryPoints(8);
				})), 8));
		cards.add(new Card(3, "Pantheon", "pantheon", CardType.BLUE, null,
				addRArray(new Resource(1, ResourceType.ORE), new Resource(2, ResourceType.BRICK),
						new Resource(1, ResourceType.GLASS), new Resource(1, ResourceType.PAPYRUS),
						new Resource(1, ResourceType.CLOTH)),
				new String[] { "temple" }, addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					p.addVictoryPoints(7);
				})), 7));
		cards.add(new Card(1, "Pfandhaus", "pawnshop", CardType.BLUE, null, null, null,
				addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					p.addVictoryPoints(3);
				})), 3));
		cards.add(new Card(3, "Gilde der Philosophen", "philosophersguild", CardType.PURPLE, null,
				addRArray(new Resource(3, ResourceType.BRICK), new Resource(1, ResourceType.CLOTH),
						new Resource(1, ResourceType.PAPYRUS)),
				null, addEArray(new Effect(EffectType.AT_MATCH_END, p -> {
					int left = swController.getPlayerController().getLeftNeighbour(p).getBoard().getResearch().size();
					int right = swController.getPlayerController().getRightNeighbour(p).getBoard().getResearch().size();
					p.addVictoryPoints(left + right);
				}))));
		cards.add(new Card(1, "Presse", "press1", CardType.GRAY, addRArray(new Resource(1, ResourceType.PAPYRUS)), null,
				null, null));
		cards.add(new Card(2, "Presse", "press2", CardType.GRAY, addRArray(new Resource(1, ResourceType.PAPYRUS)), null,
				null, null));
		cards.add(new Card(2, "Bildhauerei", "quarry", CardType.BROWN, addRArray(new Resource(2, ResourceType.STONE)),
				addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(2, "Sägewerk", "sawmill", CardType.BROWN, addRArray(new Resource(2, ResourceType.WOOD)),
				addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(ResourceType.TABLET, 2, "Schule", "school", CardType.GREEN, null,
				addRArray(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.PAPYRUS)), null, null));
		cards.add(new Card(3, "Gilde der Wissenschaftler", "scientistsguild", CardType.PURPLE, null,
				addRArray(new Resource(2, ResourceType.WOOD), new Resource(2, ResourceType.ORE),
						new Resource(1, ResourceType.PAPYRUS)),
				null, addEArray(new Effect(EffectType.AT_MATCH_END, p -> {
					int[] count = new int[3];
					for (Card res : p.getBoard().getResearch()) {
						switch (res.getScienceType()) {
						case COMPASS:
							count[0]++;
							break;
						case GEAR:
							count[1]++;
							break;
						case TABLET:
							count[2]++;
							break;
						}
					}
					switch (getMax(count)) {
					case 0:
						p.getBoard().addCard(new Card(ResourceType.COMPASS, 1, "Gildenkarte", "guildcard",
								CardType.GREEN, null, null, null, null));
						break;
					case 1:
						p.getBoard().addCard(new Card(ResourceType.GEAR, 1, "Gildenkarte", "guildcard", CardType.GREEN,
								null, null, null, null));
						break;
					case 2:
						p.getBoard().addCard(new Card(ResourceType.TABLET, 1, "Gildenkarte", "guildcard",
								CardType.GREEN, null, null, null, null));
						break;
					}
				}))));
		cards.add(new Card(ResourceType.TABLET, 1, "Skriptorium", "scriptorium", CardType.GREEN, null,
				addRArray(new Resource(1, ResourceType.PAPYRUS)), null, null));
		cards.add(new Card(3, "Senat", "senate", CardType.BLUE, null,
				addRArray(new Resource(2, ResourceType.WOOD), new Resource(1, ResourceType.STONE),
						new Resource(1, ResourceType.ORE)),
				new String[] { "library" }, addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					p.addVictoryPoints(6);
				})), 6));
		cards.add(new Card(3, "Gilde der Reeder", "shipownersguild", CardType.PURPLE, null,
				addRArray(new Resource(3, ResourceType.WOOD), new Resource(1, ResourceType.GLASS),
						new Resource(1, ResourceType.PAPYRUS)),
				null, addEArray(new Effect(EffectType.AT_MATCH_END, p -> {
					int count = 0;
					for (Card el : p.getBoard().getResources())
						count++;
					for (Card el : p.getBoard().getGuilds())
						if (el.getType() == CardType.GRAY)
							count++;
					p.addVictoryPoints(count);
				}))));
		cards.add(new Card(3, "Belagerungsmaschinen", "siegeworkshop", CardType.RED,
				addRArray(new Resource(3, ResourceType.MILITARY)),
				addRArray(new Resource(3, ResourceType.BRICK), new Resource(1, ResourceType.WOOD)),
				new String[] { "laboratory" }, null));
		cards.add(new Card(3, "Gilde der Spione", "spiesguild", CardType.PURPLE, null,
				addRArray(new Resource(3, ResourceType.BRICK), new Resource(1, ResourceType.GLASS)), null,
				addEArray(new Effect(EffectType.AT_MATCH_END, p -> {
					int count = 0;
					int left = swController.getPlayerController().getLeftNeighbour(p).getBoard().getMilitary().size();
					int right = swController.getPlayerController().getRightNeighbour(p).getBoard().getMilitary().size();
					p.addVictoryPoints(left + right);
				}))));
		cards.add(new Card(2, "Ställe", "stables", CardType.RED, addRArray(new Resource(2, ResourceType.MILITARY)),
				addRArray(new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.BRICK),
						new Resource(1, ResourceType.WOOD)),
				new String[] { "apothecary" }, null));
		cards.add(new Card(2, "Statue", "statue", CardType.BLUE, null,
				addRArray(new Resource(1, ResourceType.WOOD), new Resource(2, ResourceType.ORE)),
				new String[] { "theater" }, addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					p.addVictoryPoints(4);
				})), 4));
		cards.add(new Card(3, "Befestigungsanlage", "stockade", CardType.RED,
				addRArray(new Resource(1, ResourceType.MILITARY)), addRArray(new Resource(1, ResourceType.WOOD)), null,
				null));
		cards.add(new Card(1, "Steinbruch", "stonepit", CardType.BROWN, addRArray(new Resource(1, ResourceType.STONE)),
				null, null, null));
		cards.add(
				new Card(3, "Gilde der Strategen", "strategistsguild", CardType.PURPLE, null,
						addRArray(new Resource(2, ResourceType.ORE), new Resource(1, ResourceType.STONE),
								new Resource(1, ResourceType.CLOTH)),
						null, addEArray(new Effect(EffectType.AT_MATCH_END, p -> {
							int count = 0;
							int left = swController.getPlayerController().getLeftNeighbour(p).getLosePoints();
							int right = swController.getPlayerController().getRightNeighbour(p).getLosePoints();
							p.addVictoryPoints(left + right);
						}))));
		cards.add(new Card(ResourceType.GEAR, 3, "Studierzimmer", "study", CardType.GREEN, null,
				addRArray(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.PAPYRUS),
						new Resource(1, ResourceType.CLOTH)),
				new String[] { "school" }, null));
		cards.add(new Card(1, "Taverne", "tavern", CardType.YELLOW, null, null, null,
				addEArray(new Effect(EffectType.WHEN_PLAYED, p -> p.addCoins(5)))));
		cards.add(new Card(2, "Temple", "temple", CardType.BLUE, null,
				addRArray(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.BRICK),
						new Resource(1, ResourceType.GLASS)),
				new String[] { "altar" }, addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					p.addVictoryPoints(3);
				})), 3));
		cards.add(new Card(1, "Theater", "theater", CardType.BLUE, null, null, null,
				addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					p.addVictoryPoints(2);
				})), 2));
		cards.add(new Card(1, "Forstwirtschaft", "timberyard", CardType.BROWN,
				addRArray(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.STONE)),
				addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(
				3, "Rathaus", "townhall", CardType.BLUE, null, addRArray(new Resource(2, ResourceType.STONE),
						new Resource(1, ResourceType.ORE), new Resource(1, ResourceType.GLASS)),
				null, addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					p.addVictoryPoints(6);
				})), 6));
		cards.add(
				new Card(3, "Gilde der Händler", "tradersguild", CardType.PURPLE, null,
						addRArray(new Resource(1, ResourceType.CLOTH), new Resource(1, ResourceType.PAPYRUS),
								new Resource(1, ResourceType.GLASS)),
						null, addEArray(new Effect(EffectType.AT_MATCH_END, p -> {
							int count = 0;
							int left = swController.getPlayerController().getLeftNeighbour(p).getBoard().getTrade()
									.size();
							int right = swController.getPlayerController().getRightNeighbour(p).getBoard().getTrade()
									.size();
							p.addVictoryPoints(left + right);
						}))));
		cards.add(new Card(2, "Trainingsgelände", "trainingground", CardType.RED,
				addRArray(new Resource(2, ResourceType.MILITARY)),
				addRArray(new Resource(2, ResourceType.ORE), new Resource(1, ResourceType.WOOD)), null, null));
		cards.add(new Card(1, "Baumschule", "treefarm", CardType.BROWN,
				addRArray(new Resource(1, ResourceType.WOOD), new Resource(1, ResourceType.BRICK)),
				addRArray(new Resource(1, ResourceType.COINS)), null, null));
		cards.add(new Card(ResourceType.TABLET, 3, "Universität", "university", CardType.GREEN, null,
				addRArray(new Resource(2, ResourceType.WOOD), new Resource(1, ResourceType.PAPYRUS),
						new Resource(1, ResourceType.GLASS)),
				new String[] { "library" }, null));
		cards.add(new Card(2, "Weinberg", "vineyard", CardType.YELLOW, null, null, null,
				addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {
					int count = 0;
					for (Card el : swController.getPlayerController().getLeftNeighbour(p).getBoard().getResources())
						if (el.getType() == CardType.BROWN)
							count++;
					for (Card el : swController.getPlayerController().getRightNeighbour(p).getBoard().getResources())
						if (el.getType() == CardType.BROWN)
							count++;
					for (Card el : p.getBoard().getResources())
						if (el.getType() == CardType.BROWN)
							count++;
					p.addCoins(count);
				}))));
		cards.add(new Card(2, "Mauern", "walls", CardType.RED, addRArray(new Resource(2, ResourceType.MILITARY)),
				addRArray(new Resource(3, ResourceType.STONE)), null, null));
		cards.add(new Card(1, "Kontor West", "westtradingpost", CardType.YELLOW, null, null, null, null));
		cards.add(new Card(3, "Gilde der Arbeiter", "workersguild", CardType.PURPLE, null,
				addRArray(new Resource(2, ResourceType.ORE), new Resource(1, ResourceType.BRICK),
						new Resource(1, ResourceType.STONE), new Resource(1, ResourceType.WOOD)),
				null, addEArray(new Effect(EffectType.AT_MATCH_END, p -> {
					int count = 0;
					for (Card el : swController.getPlayerController().getLeftNeighbour(p).getBoard().getResources())
						if (el.getType() == CardType.BROWN)
							count++;
					for (Card el : swController.getPlayerController().getRightNeighbour(p).getBoard().getResources())
						if (el.getType() == CardType.BROWN)
							count++;
					p.addVictoryPoints(count);
				}))));
		cards.add(new Card(ResourceType.GEAR, 1, "Werkstatt", "workshop", CardType.GREEN, null,
				addRArray(new Resource(1, ResourceType.GLASS)), null, null));

		addDescriptions(cards);

		// Clone cards depending on player number
		int playersize = players.size();
		if (playersize == 2)
			playersize++;
		ArrayList<Card> toadd = new ArrayList<Card>();
		for (int i = 0; i < cards.size(); i++) {
			int[] sizes = countCards.get(cards.get(i).getInternalName());

			if (sizes[0] == 0)
				continue;
			if (sizes[0] <= playersize) {
				toadd.add(new Card(cards.get(i)));
			}
			if (sizes[1] == 0)
				continue;
			if (sizes[1] <= playersize) {
				toadd.add(new Card(cards.get(i)));
			}
			if (sizes[2] == 0)
				continue;
			if (sizes[2] <= playersize) {
				toadd.add(new Card(cards.get(i)));
			}
		}
		cards.addAll(toadd);

		// shuffle cards
		Collections.shuffle(cards);

		return cards;
	}


	private int getMax(int[] count) {
		int max = 0;
		int index = 0;
		for (int i = 0; i < count.length; i++) {
			if (count[i] > max) {
				max = count[i];
				index = i;
			}
		}
		return index;
	}

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
				des.append("\r\nMilitär: " + resToString(card.getProducing()));
				break;
			case BLUE:
				des.append("\r\n+" + card.getvPoints() + " Siegpunkte");
				break;
			case GREEN:
				des.append("\r\nHinweis: Die Forschungsgebäude bringen auf zwei verschiedene Weisen Siegpunkte\r\n"
						+ "ein: Es gibt Punkte für Gruppen identischer Symbole und für jeweils drei\r\n"
						+ "verschiedene Symbole.\r\n"
						+ "Die Siegpunkte, die der Spieler auf diese beiden Weisen erhält,\r\n" + "werden addiert.");
				break;
			case PURPLE:
				des.append("\r\n");
				switch (card.getInternalName()) {
				case "buildersguild":
					des.append("1 Siegpunkt pro\r\n" + "Bauabschnitt der Weltwunder, die in den\r\n"
							+ "beiden Nachbarstädten UND in der eigenen\r\n" + "Stadt vollendet worden sind.");
					break;
				case "craftsmensguild":
					des.append("1 Siegpunkt für jede graue\r\n" + "Karte in den beiden Nachbarstädten.");
					break;
				case "magistratesguild":
					des.append("1 Siegpunkt für jede blaue\r\n" + "Karte in den beiden Nachbarstädten.");
					break;
				case "philosophersguild":
					des.append("1 Siegpunkt für jede\r\n" + "grüne Karte in den beiden Nachbarstädten.");
					break;
				case "scientistsguild":
					des.append("Der Spieler profi\r\n" + "tiert nach seiner Wahl von einem der drei\r\n"
							+ "Forschungssymbole.");
					break;
				case "shipownersguild":
					des.append("1 Siegpunkt für jede braune, graue\r\n" + "und violette Karte in der eigenen Stadt.\r\n"
							+ "Hinweis: Die Gilde der Reeder selbst zählt dabei auch mit.");
					break;
				case "spiesguild":
					des.append("1 Siegpunkt für jede rote\r\n" + "Karte in den beiden Nachbarstädten.");
					break;
				case "strategistsguild":
					des.append("1 Siegpunkt für jeden Niederlage-\r\n" + "Marker in den Nachbarstädten.");
					break;
				case "tradersguild":
					des.append("1 Siegpunkt für jede gelbe\r\n" + "Karte in den beiden Nachbarstädten.");
					break;
				case "workersguild":
					des.append("1 Siegpunkt für jede braune\r\n" + "Karte in den beiden Nachbarstädten.");
					break;
				default:
					System.out.println("Not assigned:" + card.getInternalName());
				}
				break;
			case YELLOW:
				des.append("\r\n");
				switch (card.getInternalName()) {
				case "arena":
					des.append("Diese Karte bringt 3 Münzen je Bauabschnitt des Weltwunders,\r\n"
							+ "der bereits vollendet ist, wenn die Karte ins Spiel kommt\r\n"
							+ "(also 3, 6, 9 oder 12 Münzen). Am Ende der Partie bringt\r\n"
							+ "die Karte 1 Siegpunkt pro Bauabschnitt des Weltwunders\r\n"
							+ "ein, der im Laufe der Partie vollendet worden ist (also 1, 2,\r\n"
							+ "3 oder 4 Siegpunkte).");
					break;
				case "bazar":
					des.append("Diese Karte bringt 2 Münzen für jede graue Karte in\r\n"
							+ "den Städten des Spielers UND seiner beiden Nachbarn.\r\n"
							+ "Hinweis: Dabei zählen auch Gebäude grauer Karten,\r\n"
							+ "die in derselben Runde wie der Basar in den\r\n" + "Nachbarstädten gebaut worden sind.");
					break;
				case "caravansery":
					des.append("Die Karte produziert jede Runde nach Wahl des\r\n"
							+ "Spielers einen der vier abgebildeten Rohstoffe.\r\n"
							+ "Hinweis: Diesen Ressourcen können die Nachbarstädte\r\n" + "nicht kaufen.");
					break;
				case "chamberofcommerce":
					des.append("Die Karte bringt 1 Münze für jede graue Karte, die sich in der\r\n"
							+ "Stadt des Spielers befi ndet, wenn die Karte ins Spiel gebracht\r\n"
							+ "wird. Am Ende der Partie bringt die Karte 2 Siegpunkte für jede\r\n"
							+ "graue Karte in der Stadt des Spielers.");
					break;
				case "easttradingpost":
					des.append("Wenn der Spieler diese Karte ins Spiel gebracht\r\n"
							+ "hat, zahlt er ab der nächsten Runde nur noch\r\n"
							+ "1 statt 2 Münzen, wenn er von seinem rechten\r\n" + "Nachbarn Rohstoffe kauft.");
					break;
				case "forum":
					des.append("Die Karte produziert jede Runde nach Wahl des\r\n"
							+ "Spielers eines der drei Manufakturprodukte.\r\n"
							+ "Hinweis: Diesen Ressourcen können die Nachbarstädte\r\n" + "nicht kaufen.");
					break;
				case "haven":
					des.append("Die Karte bringt 1 Münze für jede braune Karte, die sich in der\r\n"
							+ "Stadt des Spielers befi ndet, wenn die Karte ins Spiel gebracht\r\n"
							+ "wird. Am Ende der Partie bringt die Karte 1 Siegpunkt für jede\r\n"
							+ "braune Karte in der Stadt des Spielers.");
					break;
				case "lighthouse":
					des.append("Die Karte bringt 1 Münze für jede gelbe Karte, die sich in der\r\n"
							+ "Stadt des Spielers befi ndet, wenn die Karte ins Spiel gebracht\r\n"
							+ "wird. Am Ende der Partie bringt die Karte 1 Siegpunkt für jede\r\n"
							+ "gelbe Karte in der Stadt des Spielers.");
					break;
				case "marketplace":
					des.append("Wenn der Spieler diese Karte ins Spiel gebracht\r\n"
							+ "hat, zahlt er ab der nächsten Runde nur noch\r\n"
							+ "1 statt 2 Münzen, wenn er von einem seiner\r\n"
							+ "beiden Nachbarn Manufakturprodukte kauft.");
					break;
				case "tavern":
					des.append("Die Karte bringt 5 Münzen ein. Der Spieler\r\n"
							+ "erhält die Münzen aus dem allgemeinen Vorrat, sobald er die Karte\r\n"
							+ "ins Spiel bringt.");
					break;
				case "vineyard":
					des.append("Diese Karte bringt 1 Münze für jede braune Karte in\r\n"
							+ "den Städten des Spielers UND seiner beiden Nachbarn.\r\n"
							+ "Hinweis: Dabei zählen auch Gebäude brauner Karten,\r\n"
							+ "die in derselben Runde wie die Presse in den\r\n"
							+ "Nachbarstädten gebaut worden sind.");
					break;
				case "westtradingpost":
					des.append("Wenn der Spieler diese Karte ins Spiel gebracht\r\n"
							+ "hat, zahlt er ab der nächsten Runde nur noch\r\n"
							+ "1 statt 2 Münzen, wenn er von seinem linken\r\n" + "Nachbarn Rohstoffe kauft.");
					break;
				default:
					System.out.println("Not assigned:" + card.getInternalName());
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

	private static String resToString(ArrayList<Resource> resources) {
		StringBuilder string = new StringBuilder();
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
				if (res.getQuantity() == 1)
					string.append("" + res.getQuantity() + " " + "Münze");
				else
					string.append("" + res.getQuantity() + " " + "Münzen");
				break;
			case GLASS:
				string.append("" + res.getQuantity() + " " + "Glas");
				break;
			case MILITARY:
				if (res.getQuantity() == 1)
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
				if (res.getQuantity() == 1)
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

	public boolean hasCard(Player player, String cardname) {
		WonderBoard board = player.getBoard();

		for (Card card : board.getResources())
			if (card.getName().equalsIgnoreCase(cardname))
				return true;
		for (Card card : board.getCivil())
			if (card.getName().equalsIgnoreCase(cardname))
				return true;
		for (Card card : board.getGuilds())
			if (card.getName().equalsIgnoreCase(cardname))
				return true;
		for (Card card : board.getMilitary())
			if (card.getName().equalsIgnoreCase(cardname))
				return true;
		for (Card card : board.getResearch())
			if (card.getName().equalsIgnoreCase(cardname))
				return true;
		for (Card card : board.getTrade())
			if (card.getName().equalsIgnoreCase(cardname))
				return true;

		return false;
	}

	private ArrayList<Resource> addRArray(Resource... ressource) {
		ArrayList<Resource> array = new ArrayList<Resource>();
		for (Resource res : ressource) {
			array.add(res);
		}
		return array;
	}

	private ArrayList<Effect> addEArray(Effect... effect) {
		ArrayList<Effect> array = new ArrayList<Effect>();
		for (Effect eff : array) {
			array.add(eff);
		}
		return array;
	}

	public Card[] loadCardStack(String filepath) {
		return null;
	}

	/**
	 *  
	 */
	public void sellCard(Card card, Player player) {

	}

	/**
	 *  
	 */
	public void placeCard(Card card, Player player) {

	}

	/**
	 *  
	 */
	public void setSlotCard(Card card, Player player) {

	}

	/**
	 *  
	 */
	public Card getCard(String cardname) {
		return null;
	}

}