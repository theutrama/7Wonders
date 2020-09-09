package model.card;

import java.io.Serializable;

import controller.PlayerController;
import model.Game;
import model.player.Player;

/** EffectCall for player */
public interface EffectCall extends Serializable {
	/**
	 * apply the effect on the specified player
	 * 
	 * @param player     player
	 * @param controller game controller
	 * @param twoPlayers true if the game has two players
	 */
	public void run(Player player, Game game, boolean twoPlayers);
}
