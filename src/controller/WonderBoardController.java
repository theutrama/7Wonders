package controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.reflections.Reflections;

import application.Utils;
import model.board.WonderBoard;

/** Controller for WonderBoard */
public class WonderBoardController {
	/** SevenWonder boards */
	private ArrayList<Class<? extends WonderBoard>> boards = new ArrayList<Class<? extends WonderBoard>>();

	/** loads the board classes */
	public WonderBoardController() {
		loadBoardClasses();
	}
	
	/**
	 * create new WonderBoard
	 * 
	 * @param name the WonderBoard's name
	 * @return new wonder board
	 */
	public WonderBoard createWonderBoard(String name) {
		return createWonderBoard(getClassByName(name));
	}

	/**
	 * create new WonderBoard
	 * 
	 * @param clazz the specific WonderBoard
	 * @return new wonder board
	 */
	public WonderBoard createWonderBoard(Class<? extends WonderBoard> clazz) {
		if (this.boards.isEmpty())
			loadBoardClasses();

		if (this.boards.contains(clazz)) {
			//this.boards.remove(clazz);
			
			try {
				Constructor<? extends WonderBoard> con = clazz.getConstructor();
				return con.newInstance(new Object[] {});
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * create new WonderBoard
	 * 
	 * @param clazz the specific WonderBoard
	 */
	private String toName(Class<? extends WonderBoard> clazz) {
		return clazz.getSimpleName().replaceAll("Board", "");
	}

	/**
	 * @return class by name
	 * @param name the name of the WonderBoard
	 */
	public Class<? extends WonderBoard> getClassByName(String name) {
		for (Class<? extends WonderBoard> clazz : this.boards) {
			if (toName(clazz).equalsIgnoreCase(name)) {
				return clazz;
			}
		}

		throw new NullPointerException("No Call found by name " + name);
	}

	/**
	 * @return names of all WonderBoards
	 */
	public String[] getWonderBoardNames() {
		String[] names = new String[this.boards.size()];

		for (int i = 0; i < this.boards.size(); i++) {
			names[i] = toName(this.boards.get(i));
		}
		return names;
	}

	/**
	 * @return random WonderBoards
	 */
	public WonderBoard createWonderBoard() {
		Class<? extends WonderBoard> clazz = this.boards.get(Utils.randInt(0, this.boards.size()));
		return createWonderBoard(clazz);
	}

	/**
	 * Clears all created boards
	 */
	public void reset() {
		this.boards.clear();
	}

	/**
	 * Loads all used boards
	 */
	public void loadBoardClasses() {
		reset();
		Reflections reflections = new Reflections("model.board");
		List<Class<? extends WonderBoard>> moduleClasses = new ArrayList<>(reflections.getSubTypesOf(WonderBoard.class));

		Collections.sort(moduleClasses, new Comparator<Class<? extends WonderBoard>>() {
			@Override
			public int compare(Class<? extends WonderBoard> obj1, Class<? extends WonderBoard> obj2) {
				return obj1.getSimpleName().compareTo(obj2.getSimpleName());
			}
		});

		for (Class<? extends WonderBoard> clazz : moduleClasses) {
			this.boards.add(clazz);
		}
	}
}
