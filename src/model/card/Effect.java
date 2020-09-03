package model.card;

import model.player.Player;

public class Effect {

	private EffectType effectType;
	private EffectCall call;
	
	public Effect(EffectType effectType,EffectCall call) {
		this.effectType = effectType;
		this.call=call;
	}
	
	public EffectType getType() {
		return this.effectType;
	}

	public void run(Player player) {
		 this.call.run(player);
	}
}
