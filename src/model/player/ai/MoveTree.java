package model.player.ai;

import java.util.ArrayList;

import model.GameState;

/** builds tree containing move operations */
public class MoveTree {

	private GameState state;
	private Move move;
	private MoveTree parent;

	private ArrayList<MoveTree> children;

	/**
	 * create an inner move tree
	 * 
	 * @param move  move
	 * @param state game state
	 */
	public MoveTree(Move move, GameState state) {
		children = new ArrayList<>();
		this.move = move;
		this.state = state;
	}

	/**
	 * create root tree
	 * 
	 * @param state game state
	 */
	public MoveTree(GameState state) {
		this(null, state);
	}

	/**
	 * adds the given tree to {@link #children}
	 * 
	 * @param child new child of this tree
	 */
	public void addChild(MoveTree child) {
		children.add(child);
		child.parent = this;
	}

	/**
	 * getter for {@link #state}
	 * 
	 * @return game state
	 */
	public GameState getState() {
		return state;
	}

	/**
	 * getter for {@link #move}
	 * 
	 * @return move
	 */
	public Move getMove() {
		return move;
	}

	/**
	 * getter for {@link #children}
	 * 
	 * @return list of child trees
	 */
	public ArrayList<MoveTree> getChildren() {
		return children;
	}

	/**
	 * getter for {@link #parent}
	 * 
	 * @return parent tree or null if this tree is a root
	 */
	public MoveTree getParent() {
		return parent;
	}
}
