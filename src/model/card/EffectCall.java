package model.card;

import java.io.Serializable;

import model.player.Player;

/** EffectCall for player */
public interface EffectCall extends Serializable {
	/** 
	 * apply the effect on the specified player 
	 * @param player
	 */
	public void run(Player player);
}
