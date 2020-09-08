package model.card;

import java.io.Serializable;

import controller.PlayerController;
import model.player.Player;

/** EffectCall for player */
public interface EffectCall extends Serializable {
	/** 
	 * apply the effect on the specified player 
	 * @param player
	 * @param controller 
	 */
	public void run(Player player, PlayerController controller);
}
