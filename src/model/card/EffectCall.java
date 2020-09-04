package model.card;

import java.io.Serializable;

import model.player.Player;

public interface EffectCall extends Serializable {
	/** apply the effect on the specified player */
	public void run(Player player);
}
