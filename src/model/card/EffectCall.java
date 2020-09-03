package model.card;

import model.player.Player;

public interface EffectCall {
	public void run(Player player, Player left, Player right);
}
