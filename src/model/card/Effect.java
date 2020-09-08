package model.card;

import java.io.Serializable;

import model.player.Player;

/** Effect of Cards and Wonders */
public class Effect implements Serializable{
	private static final long serialVersionUID = 1L;
	/** effect execution frequency */
	private EffectType effectType;
	/** applies the effect to a player */
	private EffectCall call;
	
	/**
	 * create effect
	 * @param effectType sets {@link #effectType}
	 * @param call sets {@link #call}
	 */
	public Effect(EffectType effectType,EffectCall call) {
		this.effectType = effectType;
		this.call=call;
	}
	
	/**
	 * getter for {@link #getType()}
	 * @return effect type
	 */
	public EffectType getType() {
		return this.effectType;
	}

	/**
	 * calls {@link EffectCall#run(Player) run} on the {@link #call effect call}
	 * @param player the player the effect should be applied to
	 */
	public void run(Player player) {
		 this.call.run(player);
	}
}
