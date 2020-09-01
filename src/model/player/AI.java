package model.player;

import model.board.WonderBoard;

public class AI extends Player {

	private Difficulty difficulty;

	public AI(Difficulty difficulty, WonderBoard board) {
		super("KI - " + difficulty.toString(), board);
		this.difficulty = difficulty;
	}

}
