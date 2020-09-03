package model.card;

import model.player.Player;

public class Effect<T> {

	private EffectType effectType;
	private EffectCall<T> call;
	
	public Effect(EffectType effectType,EffectCall<T> call) {
		this.effectType = effectType;
		this.call=call;
	}
	
	public EffectType getType() {
		return this.effectType;
	}

	public T run(Player player) {
		return this.call.run(player);
	}
}
