package application;

import java.lang.reflect.Field;
import java.util.Random;

public class Utils {
	private static Random rand = new Random();

	public static int max(int[] ints) {
		if(ints.length == 0)return 0;
		return max(ints,0);
	}
	
	private static int max(int[] ints, int index) {
		if(ints.length == index)return Integer.MIN_VALUE;
		return Math.max(ints[index], max(ints, index+1));
	}
	
	public static int[] addToIndex(int add, int index, int[] amount) {
		amount[index]+=add;
		return amount;
	}
	
	public static int randInt(int min, int max) {
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
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
