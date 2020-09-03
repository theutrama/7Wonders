package application;

public class Utils {

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
}
