package model.player.ai;

import application.Main;
import controller.utils.BuildCapability;
import model.GameState;
import model.board.WonderBoard;
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

	/**
	 * evaluate age 1
	 * 
	 * @param state  game state
	 * @param player player
	 * @return value
	 */
	private int evaluateAge1(GameState state, Player player) {
		int value = 0;
		for (int i = 0; i < 3; i++)
			if (player.getBoard().isFilled(i) || Main.getSWController().getPlayerController().hasResources(player, asList(player.getBoard().getSlotResquirement(i)), state) != BuildCapability.NONE)
				value += 1;
		return value;
	}

	/**
	 * evaluate age 2
	 * 
	 * @param state  game state
	 * @param player player
	 * @return value
	 */
	private int evaluateAge2(GameState state, Player player) {
		int value = 0;
		for (int i = 0; i < 3; i++)
			if (player.getBoard().isFilled(i) || Main.getSWController().getPlayerController().hasResources(player, asList(player.getBoard().getSlotResquirement(i)), state) != BuildCapability.NONE)
				value += 2;
		value += Main.getSWController().getPlayerController().getMilitaryPoints(player) + getCivilPoints(player) / 2;
		return value;
	}

	/**
	 * evaluate age 2
	 * 
	 * @param state  game state
	 * @param player player
	 * @return value
	 */
	private int evaluateAge3(GameState state, Player player) {
		Main.getSWController().getGameController().runEffects(state, player, player.getBoard().getTrade(), state.isTwoPlayers());
		Main.getSWController().getGameController().runEffects(state, player, player.getBoard().getGuilds(), state.isTwoPlayers());
		player.addVictoryPoints(player.getConflictPoints());
		player.addVictoryPoints(-player.getLosePoints());
		player.addVictoryPoints(player.getCoins() / 3);
		player.addVictoryPoints(Main.getSWController().getPlayerController().getSciencePoints(player));
		return player.getVictoryPoints();
	}

	@Override
	protected int evaluate(GameState state, String playername) {
		Player player = Main.getSWController().getPlayerController().getPlayer(playername, state);
		switch (state.getAge()) {
		case 1:
			return evaluateAge1(state, player);
		case 2:
			return evaluateAge2(state, player);
		case 3:
			return evaluateAge3(state, player);
		}
		return 0;
	}
}
