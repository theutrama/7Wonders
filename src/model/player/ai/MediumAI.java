package model.player.ai;

import java.util.ArrayList;

import application.Main;
import controller.utils.BuildCapability;
import model.GameState;
import model.board.WonderBoard;
import model.card.Card;
import model.player.Player;

/** Medium Artificial Intelligence */
public class MediumAI extends AdvancedAI {
	private static final long serialVersionUID = 1L;

	/**
	 * create new Medium AI
	 * 
	 * @param name  name of AI
	 * @param board WonderBoard for the AI
	 */
	public MediumAI(String name, WonderBoard board) {
		super(name, board);
	}

	@Override
	protected int evaluate(GameState state, String playername) {

		Player player = Main.getSWController().getPlayerController().getPlayer(playername, state);

		int value = 0;

		switch (state.getAge()) {
		case 1:
			for (int i = 0; i < 3; i++) {
				if (player.getBoard().isFilled(i) || Main.getSWController().getPlayerController().hasResources(player, asList(player.getBoard().getSlotResquirement(i)), state) != BuildCapability.NONE)
					value += 1;
			}
			break;
		case 2:
			for (int i = 0; i < 3; i++) {
				if (player.getBoard().isFilled(i) || Main.getSWController().getPlayerController().hasResources(player, asList(player.getBoard().getSlotResquirement(i)), state) != BuildCapability.NONE)
					value += 2;
			}
			value += Main.getSWController().getPlayerController().getMilitaryPoints(player);
			value += getCivilPoints(player) / 2;
			break;
		case 3:
			// Victory points //////////////////////////////////////////

			Main.getSWController().getGameController().runEffects(player, player.getBoard().getTrade(), state.isTwoPlayers());
			Main.getSWController().getGameController().runEffects(player, player.getBoard().getGuilds(), state.isTwoPlayers());
			Main.getSWController().getGameController().runEffects(player, player.getBoard().getCivil(), state.isTwoPlayers());
			// conflicts
			player.addVictoryPoints(player.getConflictPoints());
			player.addVictoryPoints(-player.getLosePoints());
			// coins
			player.addVictoryPoints(player.getCoins() / 3);
			// science
			player.addVictoryPoints(Main.getSWController().getPlayerController().getSciencePoints(player));

			value += player.getVictoryPoints();
			break;
		}

		return value;
	}

	@Override
	public Card getHalikarnassusCard(Player player, ArrayList<Card> trash, GameState state) {
		if (trash.isEmpty())
			return null;
		int maxValue = Integer.MIN_VALUE, maxIndex = 0;
		for (int i = 0; i < trash.size(); i++) {
			GameState newState = state.deepClone();
			Main.getSWController().getPlayerController().getPlayer(player.getName(), newState).getBoard().addCard(trash.get(i));
			int value = evaluate(newState, player.getName());
			if (value > maxValue) {
				maxValue = value;
				maxIndex = i;
			}
		}
		return trash.get(maxIndex);
	}

}
