package controller;

import model.board.BabylonBoard;
import model.board.RhodosBoard;
import model.board.WonderBoard;
import java.util.ArrayList;

import application.Utils;
import model.card.Card;
import model.card.ResourceType;
import model.player.AI;
import model.player.Difficulty;
import model.player.Player;

public class PlayerController {

	private SevenWondersController swController;
	private WonderBoardController wb;

	public PlayerController(SevenWondersController swController) {
		this.swController = swController;
		this.wb = swController.getWonderBoardController();
	}

	public Player createPlayer(String playername, String wonderboard) {
		WonderBoard board = wb.createWonderBoard(wonderboard);
		Player player = new Player(playername, board);
		board.setPlayer(player);

		return player;
	}

	public Player getPlayer(String playername) {
		int state = swController.getGame().getCurrentState();
		ArrayList<Player> list = swController.getGame().getStates().get(state).getPlayers();

		for (Player player : list) {
			if (player.getName().equalsIgnoreCase(playername)) {
				return player;
			}
		}
		return null;
	}

	public AI createAI(String playername, String wonderboard, Difficulty difficulty) {
		WonderBoard board = wb.createWonderBoard(wonderboard);
		AI ai = new AI(difficulty, board);
		return ai;
	}

	public void chooseCard(Card card, Player player) {
		player.setChooseCard(card);
	}

	public Player getLeftNeighbour(Player player) {
		ArrayList<Player> players = swController.getGame().getStates().get(swController.getGame().getCurrentState())
				.getPlayers();
		if (players.indexOf(player) == 0) {
			return players.get(players.size() - 1);
		}
		return players.get(players.indexOf(player) - 1);
	}

	public Player getRightNeighbour(Player player) {
		ArrayList<Player> players = swController.getGame().getStates().get(swController.getGame().getCurrentState())
				.getPlayers();
		if (players.indexOf(player) == players.size() - 1) {
			return players.get(0);
		}
		return players.get(players.indexOf(player) + 1);
	}

	public int getMilitaryPoints(Player player) {
		int miliPoints = 0;

		if (player.getBoard() instanceof RhodosBoard) {
			miliPoints += ((RhodosBoard) player.getBoard()).getMilitaryPoints();
		}

		ArrayList<Card> list = player.getBoard().getMilitary();
		for (Card card : list) {
			miliPoints += card.isProducing(ResourceType.MILITARY);
		}
		return miliPoints;
	}

	private int getSciencePoints(int[] amount) {
		int victory_points = 0;
		for (int i = 0; i < amount.length; i++) {
			victory_points += amount[i] * amount[i];
		}

		while (amount[0] >= 1 && amount[1] >= 1 && amount[2] >= 1) {
			victory_points += 7;
			amount[0]--;
			amount[1]--;
			amount[2]--;
		}

		return victory_points;
	}

	public int getSciencePoints(Player player) {
		int[] amount = new int[] { 0, 0, 0 };
		for (int i = 0; i < BabylonBoard.types.length; i++) {
			for (Card card : player.getBoard().getResearch()) {
				if (card.getScienceType() == BabylonBoard.types[i]) {
					amount[i]++;
				}
			}
		}

		boolean hasBabylon = (player.getBoard() instanceof BabylonBoard
				&& ((BabylonBoard) player.getBoard()).isFilled(2));
		boolean hasScientistsGuild = swController.getCardController().hasCard(player, "scientistsguild");

		if (hasBabylon && hasScientistsGuild) {
			int result = 0;
			int[] clone;
			for (int i = 0; i < 3; i++) {
				for (int a = 0; a < 3; a++) {
					int type = getSciencePoints(Utils.addToIndex(1, i, clone = amount.clone()));
					type = getSciencePoints(Utils.addToIndex(1, a, clone));
					result = Math.max(type, result);
				}
			}
			return result;
		} else if (hasBabylon || hasScientistsGuild) {
			int type1 = getSciencePoints(Utils.addToIndex(1, 0, amount.clone()));
			int type2 = getSciencePoints(Utils.addToIndex(1, 1, amount.clone()));
			int type3 = getSciencePoints(Utils.addToIndex(1, 2, amount.clone()));

			return Utils.max(new int[] { type1, type2, type3 });

		}
		return getSciencePoints(amount);
	}
}
