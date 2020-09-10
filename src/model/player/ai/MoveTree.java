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
	
	public Move getMove() {
		return move;
	}

	public ArrayList<MoveTree> getLeaves() {
		if (children.isEmpty()) {
			ArrayList<MoveTree> result = new ArrayList<>();
			result.add(this);
			return result;
		} else {
			ArrayList<MoveTree> result = children.get(0).getLeaves();
			for (int i = 1; i < children.size(); i++)
				result.addAll(children.get(i).getLeaves());
			return result;
		}
			
	}

	public ArrayList<MoveTree> getChildren() {
		return children;
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
