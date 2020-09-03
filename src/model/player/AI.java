package model.player;

import model.board.WonderBoard;

public class AI extends Player {
	/** level of skill */
	private Difficulty difficulty;

	/**
	 * creates a new AI using the given board
	 * @param difficulty level
	 * @param board wonder board
	 */
	public AI(Difficulty difficulty, WonderBoard board) {
		super("KI - " + difficulty.toString(), board);
		this.difficulty = difficulty;
	}

}
