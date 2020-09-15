package model.card;

import java.io.Serializable;

import model.GameState;
import model.player.Player;

/** EffectCall for player */
public interface EffectCall extends Serializable {
	/**
	 * apply the effect on the specified player
	 * 
	 * @param player     player
	 * @param state      game state
	 * @param twoPlayers true if the game has two players
	 */
	public void run(Player player, GameState state, boolean twoPlayers);
}
