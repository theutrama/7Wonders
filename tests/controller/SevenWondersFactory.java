package controller;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import application.Main;
import model.Game;
import model.GameState;
import model.card.Card;
import model.player.Player;
import model.ranking.Ranking;

/** creates SevenWonders tests */
public class SevenWondersFactory {
	/** creates new SevenWonders Controller in Factory */
	public static SevenWondersController create() {

		SevenWondersController sevenWondersController = new SevenWondersController();
		PlayerController playerController = sevenWondersController.getPlayerController();
		CardController cardController = sevenWondersController.getCardController();
		GameController gameController = sevenWondersController.getGameController();
		/*
		 * GameController gameController = sevenWondersController.getGameController(); WonderBoardController wwonderBoardController = sevenWondersController.getWonderBoardController();
		 * IOController ioController = sevenWondersController.getIOController(); SoundController soundController = sevenWondersController.getSoundController();
		 */

		Player player1 = playerController.createPlayer("erster", "Alexandria");  
		Player player2 = playerController.createPlayer("zweiter", "Babylon");
		Player player3 = playerController.createPlayer("dritter", "Ephesos");
		Player player4 = playerController.createPlayer("vierter", "Gizah");
		Player player5 = playerController.createPlayer("fünfter", "Halikarnassus");
		Player player6 = playerController.createPlayer("sechster", "Olympia");
		Player player7 = playerController.createPlayer("siebter", "Rhodos");

		ArrayList<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		players.add(player5);
		players.add(player6);
		players.add(player7);

		//Game game = gameController.createGame("Testgame",  players);
		
		Game game = new Game("testgame1");
		sevenWondersController.setGame(game);
		ArrayList<Card> cards = cardController.generateCardStack(players);
		
		game.getStates().add(new GameState(1, 1, players, cards));
		game.setCurrentState(0);
		sevenWondersController.setRanking(new Ranking());
		

		// @formatter:off
		 /*
		  *  4th round; currentPlayer Player 4;
		  *  Player1 Cards: circus - 3 military points
		  *  				apothecary - compass
		  *  				laboratory - gear
		  *  				library - tablet
		  *  				tree farm (Baumschule) - holz/ziegel
		  *  				clay pit (Tongrube) - ziegel/erz
		  *  
		  *  Player2 Cards: archeryrange - 2 military points
		  *  				academy - compass
		  *  				observatory - gear
		  *  				school - tablet
		  *  				lodge - compass
		  *  				apothecary - compass
		  *  				excavation (Ausgrabungsstätte)
		  *  				timberyard (Forstwirtschaft)
		  *  				brickyard (Ziegelbrennerei)
		  *  				westtradingpost (Kontor west)
		  *  
		  *  Player3 Cards: barracks - 1 military points
		  *  				lodge - compass
		  *  				study - gear
		  *  				forestcave (Waldhöhle)
		  * 				mine (Mine)
		  *  
		  *  Player4 Cards: stables - 2 military points
		  *  				lodge - compass
		  *  
		  *  Player5 Cards: archeryrange - 2 military points
		  *  				lodge - compass
		  *  				apothecary - compass	
		  *  				academy - compass			
		  *  
		  *  Player6 Cards: circus - 3 military points
		  *  				laboratory - gear
		  *  				study - gear
		  *  
		  *  Player7 Cards: circus - 3 military points
		  *  				archeryrange - 2 military points
		  *  				alle science Karten
		  *  				
		  */

		/*player1 = playerController.getPlayer("erster");
		player1 = playerController.getPlayer("zweiter");
		player1 = playerController.getPlayer("dritter");
		player1 = playerController.getPlayer("vierter");
		player1 = playerController.getPlayer("fünfter");
		player1 = playerController.getPlayer("sechster");
		player1 = playerController.getPlayer("siebter");*/
		// @formatter:on

		Card card26 = cardController.getCard(cards, "lumberyard");
		
		Card card14 = cardController.getCard(cards, "treefarm");
		Card card16 = cardController.getCard(cards, "claypit");
		Card card17 = cardController.getCard(cards, "excavation");
		Card card18 = cardController.getCard(cards, "timberyard");
		Card card19 = cardController.getCard(cards, "forestcave");
		Card card20 = cardController.getCard(cards, "mine");
		Card card21 = cardController.getCard(cards, "brickyard");
		Card card15 = cardController.getCard(cards, "westtradingpost");

		Card card13 = cardController.getCard(cards, "circus"); // 3 military points
		Card card11 = cardController.getCard(cards, "archeryrange"); // 2 military points
		Card card10 = cardController.getCard(cards, "barracks"); // 1 military point
		Card card12 = cardController.getCard(cards, "stables"); // 2 military points

		Card card1 = cardController.getCard(cards, "apothecary"); // compass
		Card card2 = cardController.getCard(cards, "academy"); // commpass
		Card card3 = cardController.getCard(cards, "lodge"); // commpass

		Card card4 = cardController.getCard(cards, "laboratory"); // gear
		Card card5 = cardController.getCard(cards, "observatory"); // gear
		Card card6 = cardController.getCard(cards, "study"); // gear

		Card card7 = cardController.getCard(cards, "library"); // tablet
		Card card8 = cardController.getCard(cards, "school"); // tablet
		Card card9 = cardController.getCard(cards, "scriptorium"); // tablet
		
		

		cards.remove(card1);
		cards.remove(card2);
		cards.remove(card3);
		cards.remove(card4);
		cards.remove(card5);
		cards.remove(card6);
		cards.remove(card7);
		cards.remove(card8);
		cards.remove(card9);
		cards.remove(card10);
		cards.remove(card11);
		cards.remove(card12);
		cards.remove(card13);
		cards.remove(card14);
		cards.remove(card15);
		cards.remove(card16);
		cards.remove(card17);
		cards.remove(card18);
		cards.remove(card19);
		cards.remove(card20);
		cards.remove(card21);

		player1.getBoard().addCard(card13);
		player1.getBoard().addCard(card1);
		player1.getBoard().addCard(card4);
		player1.getBoard().addCard(card7);
		player1.getBoard().addCard(card14);
		player1.getBoard().addCard(card16);
		ArrayList<Card> hand= new ArrayList <Card>();
		hand.add(card1);
		hand.add(card2);
		hand.add(card3);
		hand.add(card4);
		hand.add(card5);
		hand.add(card6);
		hand.add(card7);
		
		player1.setHand(hand);
		player2.setHand(hand);
		player3.setHand(hand);
		player4.setHand(hand);
		player5.setHand(hand);
		player6.setHand(hand);
		player7.setHand(hand);
		
		

		player2.getBoard().addCard(card11);
		player2.getBoard().addCard(card2);
		player2.getBoard().addCard(card5);
		player2.getBoard().addCard(card8);
		player2.getBoard().addCard(card17);
		player2.getBoard().addCard(card18);
		player2.getBoard().addCard(card15);
		player2.getBoard().addCard(card21);
		player2.getBoard().addCard(card3);
		player2.getBoard().addCard(card1);

		player3.getBoard().addCard(card10);
		player3.getBoard().addCard(card3);
		player3.getBoard().addCard(card6);
		player3.getBoard().addCard(card19);
		player3.getBoard().addCard(card20);

		player4.getBoard().addCard(card12);
		player4.getBoard().addCard(card3);

		player5.getBoard().addCard(card11);
		player5.getBoard().addCard(card3);
		player5.getBoard().addCard(card2);
		player5.getBoard().addCard(card1);

		player6.getBoard().addCard(card13);
		player6.getBoard().addCard(card4);
		player6.getBoard().addCard(card6);

		player7.getBoard().addCard(card13);
		player7.getBoard().addCard(card1);
		player7.getBoard().addCard(card2);
		player7.getBoard().addCard(card3);
		player7.getBoard().addCard(card4);
		player7.getBoard().addCard(card5);
		player7.getBoard().addCard(card6);
		player7.getBoard().addCard(card7);
		player7.getBoard().addCard(card8);
		player7.getBoard().addCard(card9);
		player7.getBoard().addCard(card11);

		player1.setChooseCard(card1);
		player2.setChooseCard(card2);
		player3.setChooseCard(card3);
		player4.setChooseCard(card1);
		player4.setChooseCard(card2);
		player5.setChooseCard(card1);
		player5.setChooseCard(card2);
		player5.setChooseCard(card3);
	

		return sevenWondersController;
	}
}