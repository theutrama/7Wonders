package model.card;

import java.io.Serializable;

import controller.PlayerController;
import model.Game;
import model.GameState;
import model.player.Player;

/** Effect of Cards and Wonders */
public class Effect implements Serializable {
	private static final long serialVersionUID = 1L;
	/** effect execution frequency */
	private EffectType effectType;
	/** applies the effect to a player */
	private EffectCall call;

	/**
	 * create effect
	 * 
	 * @param effectType sets {@link #effectType}
	 * @param call       sets {@link #call}
	 */
	public Effect(EffectType effectType, EffectCall call) {
		this.effectType = effectType;
		this.call = call;
	}

	/**
	 * getter for {@link #getType()}
	 * 
	 * @return effect type
	 */
	public EffectType getType() {
		return this.effectType;
	}
	
	/**
	 * runs effect on player and game
	 * @param player	the player
	 * @param game		the game
	 */
	public void run(Player player, Game game) {
		run(player, game.getCurrentGameState(), game.getCurrentGameState().isTwoPlayers());
	}

	/**
	 * calls {@link EffectCall#run(Player) run} on the {@link #call effect call}
	 * 
	 * @param controller the game controller
	 * @param twoPlayers true if it is a two players game
	 * @param player     the player the effect should be applied to
	 */
	public void run(Player player, GameState state, boolean twoPlayers) {
		this.call.run(player, state, twoPlayers);
	}
}
