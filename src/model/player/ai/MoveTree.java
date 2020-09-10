package model.player.ai;

import java.util.ArrayList;
import java.util.Iterator;

import model.GameState;

public class MoveTree implements Iterable<Move> {

	private GameState state;
	private Move move;

	private ArrayList<MoveTree> children;

	public MoveTree(Move move, GameState state) {
		children = new ArrayList<>();
		this.move = move;
		this.state = state;
	}

	public MoveTree(GameState state) {
		this(null, state);
	}
	
	public void addChild(MoveTree child) {
		children.add(child);
	}
	
	public void clearState() {
		state = null;
	}
	
	public GameState getState() {
		return state;
	}

	@Override
	public Iterator<Move> iterator() {
		return new Iterator<Move>() {
			@Override
			public Move next() {
				return null;
			}

			@Override
			public boolean hasNext() {
				return false;
			}
		};
	}

}
