package model.card;

import java.io.Serializable;

import model.player.Player;

public interface EffectCall {
	public void run(Player player);
}
