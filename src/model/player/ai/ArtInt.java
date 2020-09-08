package model.player.ai;

import java.util.ArrayList;

import model.board.WonderBoard;
import model.player.Player;
import model.player.ai.Move.Action;

/** Artificial Intelligence for SevenWonders */
public class ArtInt extends Player {
	/** level of skill */
	private Difficulty difficulty;

	/**
	 * creates a new AI using the given board
	 * @param difficulty level
	 * @param board wonder board
	 */
	public ArtInt(Difficulty difficulty, WonderBoard board) {
		super("KI - " + difficulty.toString(), board);
		this.difficulty = difficulty;
	}
	
	public Difficulty getDifficulty() {
		return this.difficulty;
	}
	
	public int max(int tiefe,int alpha, int beta) {
		if(tiefe == 0)return 0;
		int maxWert = alpha;
		
		
		return maxWert;
	}
	
	public int min(int tiefe,int alpha, int beta) {
		if(tiefe == 0)return 0;
		int minWert = beta;
		
		ArrayList<Move> moves = generateMoves();
		for(Move m : moves) {
			
		}
		return minWert;
	}
	
	public int doMove(Move m) {
		
		
		return 0;
	}
	
	public ArrayList<Move> generateMoves() {
		ArrayList<Move> list = new ArrayList<Move>();
		
		for(int i = 0; i < this.getHand().size(); i++) {
			for(Action action : Move.Action.values()) {
				list.add(new Move(this, this.getHand().get(i), action));
			}
		}
		
		return list;
	}
}