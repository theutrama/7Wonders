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

	private void addChildren(ArrayList<Move> moves) {
		for (Move move : moves) {
			children.add(new MoveTree(move, state));
		}
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
