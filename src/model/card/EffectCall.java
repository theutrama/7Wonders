package model.card;

import model.player.Player;

public interface EffectCall<T> {
	public T run(Player player);
}
