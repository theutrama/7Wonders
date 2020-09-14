package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Random;

import javafx.scene.image.Image;

public class Utils {
	private static Random rand = new Random();

	/**
	 * Fix Wondername If its another language
	 * @param wondername
	 * @return correct Wondername
	 */
	public static String toWonder(String wondername) {
		if (wondername.equalsIgnoreCase("ephesus"))
			return "ephesos";
		if (wondername.equalsIgnoreCase("alexandria"))
			return "alexandria";
		if (wondername.equalsIgnoreCase("rhodes"))
			return "rhodos";
		if (wondername.equalsIgnoreCase("halicarnassus"))
			return "halikarnassus";
		if (wondername.equalsIgnoreCase("giza"))
			return "gizah";
		return wondername;
	}
	
	/**
	 * Returns the Value ;)
	 * @param <T> 
	 * @param value
	 * @return value
	 */
	public static <T> T getValue(T value) {
		return value;
	}

	/**
	 * returns max value of an integer array
	 * 
	 * @param count array
	 * @return index max value of array
	 */
	public static int getMax(int[] count) {
		int max = 0;
		int index = 0;
		for (int i = 0; i < count.length; i++) {
			if (count[i] > max) {
				max = count[i];
				index = i;
			}
		}
		return index;
	}

	/**
	 * Returns the correct Name of the Card
	 * @param card 
	 * @param age
	 * @return Cardname
	 */
	public static String toCard(String card, int age) {
		if (card.equalsIgnoreCase("glassworks"))
			return "glassworks" + age;
		if (card.equalsIgnoreCase("loom"))
			return "loom" + age;
		if (card.equalsIgnoreCase("press"))
			return "press" + age;
		if (card.equalsIgnoreCase("craftsmen_guild"))
			return "craftsmensguild";
		if (card.contains("_"))
			return card.replaceAll("_", "");
		return card;
	}

	/**
	 * Returns the Max Int
	 * @param ints
	 * @return int
	 */
	public static int max(int[] ints) {
		if (ints.length == 0)
			return 0;
		return max(ints, 0);
	}

	/**
	 * Recursive function for max
	 * @param ints
	 * @param index
	 * @return
	 */
	private static int max(int[] ints, int index) {
		if (ints.length == index)
			return Integer.MIN_VALUE;
		return Math.max(ints[index], max(ints, index + 1));
	}
	
	/**
	 * Adds an Value to a specific index of the array
	 * @param add
	 * @param index
	 * @param array
	 * @return array
	 */
	public static int[] addToIndex(int add, int index, int[] array) {
		array[index] += add;
		return array;
	}

	/**
	 * Random Integer function
	 * @param min
	 * @param max
	 * @return int
	 */
	public static int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	/**
	 * Open Image from Filepath
	 * @param path
	 * @return Image
	 * @throws IOException
	 */
	public static Image toImage(String path) throws IOException {
		InputStream fileIn = Main.cldr.getResourceAsStream(path);
		Image img = new Image(fileIn);
		fileIn.close();
		return img;
	}

	/**
	 * Returns per reflection an Variable
	 * @param <T>
	 * @param obj
	 * @param name
	 * @return
	 */
	public static <T> T getValue(Object obj, String name) {
		try {
			Field field = obj.getClass().getField(name);
			field.setAccessible(true);
			return (T) field.get(obj);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
