package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Game;
import model.ranking.Ranking;
/** Controller for Input-Output interaction */
public class IOController {
	
	private static final String GAME_FOLDER = "games", RANKING = "ranking";

	private SevenWondersController swController;
	/**
	 * creates new IOController
	 * @param swController		SevenWOnders Controller
	 */
	public IOController(SevenWondersController swController) {
		this.swController = swController;
	}
	
	/**
	 * loads the game with the specified name
	 * 
	 * @param filename the name of the game
	 * @return the game object
	 */
	public Game load(String filename) {
		return (Game) loadObject(File.separator + GAME_FOLDER + File.separator + filename);
	}
	
	private Object loadObject(String path) {
		File file = new File(getExecutionPath() + path);
		if (!file.exists())
			return null;

		try (ObjectInputStream oIn = new ObjectInputStream(new FileInputStream(file))) {
			Object obj = oIn.readObject();
			oIn.close();
			return obj;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Saves a game at any state as a file with the same name. If such a file
	 * already exists, it is overwritten.
	 * 
	 * @param game
	 */
	public void save(Game game) {
		save(game,File.separator + GAME_FOLDER + File.separator + game.getName());
	}
	
	private void save(Object obj,String path) {
		try {
			File file1 = new File(getExecutionPath());
			if (!file1.exists())
				file1.createNewFile();
			File file2 = new File(getExecutionPath() + path);
			if (!file2.exists())
				file2.createNewFile();
			ObjectOutputStream oOut = new ObjectOutputStream(new FileOutputStream(file2));
			oOut.writeObject(obj);
			oOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * reads all game files in the game directory
	 * 
	 * @return list of all game names
	 */
	public String[] listGameFiles() {
		File[] games = new File(getExecutionPath() + File.separator + GAME_FOLDER).listFiles();
		if (games == null) // directory does not exist
			return new String[] {};
		String[] result = new String[games.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = games[i].getName();
		}
		return result;
	}

	/**
	 * deletes the game with the specified name
	 * 
	 * @param filename a game name
	 * @return true if and only if the game file was deleted
	 */
	public boolean deleteFile(String filename) {
		File file = new File(getExecutionPath() + File.separator + GAME_FOLDER + File.separator + filename);
		if (!file.exists())
			return false;
		return file.delete();
	}

	/**
	 * saves the ranking object of the {@link SevenWondersController main controller} into a ranking file
	 */
	public void saveRanking() {
		save(swController.getRanking(),File.separator + RANKING);
	}

	/**
	 * Reads the ranking object from the resources and sets the ranking attribute of
	 * the {@link #swController main controller} to this one.<br>
	 * If no file exists a new Ranking object is assigned.
	 */
	public void loadRanking() {
		Ranking rank = (Ranking) loadObject(File.separator + RANKING);
		
		if(rank!=null) {
			swController.setRanking(rank);
		}else {
			swController.setRanking(new Ranking());
		}
	}

	/**
	 * @return resource location for this game
	 */
	private static String getExecutionPath() {
		return System.getProperty("user.dir") + "/SWResource";
	}

}
