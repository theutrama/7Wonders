package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Random;

import javafx.scene.image.Image;

public class Utils {
	private static Random rand = new Random();

	public static String toWonder(String w) {
		if (w.equalsIgnoreCase("ephesus"))
			return "ephesos";
		if (w.equalsIgnoreCase("alexandria"))
			return "alexandria";
		if (w.equalsIgnoreCase("rhodes"))
			return "rhodos";
		if (w.equalsIgnoreCase("halicarnassus"))
			return "halikarnassus";
		if (w.equalsIgnoreCase("giza"))
			return "gizah";
		return w;
	}
	
	public static <T> T getValue(T v) {
		return v;
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

	public static String toCard(String c, int age) {
		if (c.equalsIgnoreCase("glassworks"))
			return "glassworks" + age;
		if (c.equalsIgnoreCase("loom"))
			return "loom" + age;
		if (c.equalsIgnoreCase("press"))
			return "press" + age;
		if (c.equalsIgnoreCase("craftsmen_guild"))
			return "craftsmensguild";
		if (c.contains("_"))
			return c.replaceAll("_", "");
		return c;
	}

	public static int max(int[] ints) {
		if (ints.length == 0)
			return 0;
		return max(ints, 0);
	}

	private static int max(int[] ints, int index) {
		if (ints.length == index)
			return Integer.MIN_VALUE;
		return Math.max(ints[index], max(ints, index + 1));
	}

	public static int[] addToIndex(int add, int index, int[] amount) {
		amount[index] += add;
		return amount;
	}

	public static int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static Image toImage(String path) throws IOException {
		FileInputStream fileIn = new FileInputStream(path);
		Image img = new Image(fileIn);
		fileIn.close();
		return img;
	}

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
