package application;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import controller.SevenWondersController;
import javafx.scene.image.Image;
import model.Game;
import model.player.Player;
import model.player.ai.ArtInt;
import model.player.ai.Difficulty;

public class Utils {
	private static Random rand = new Random();
	
	//C:\Users\obena\Downloads\ki-turnier-gesamt.csv
	
	
	public static void main(String[] args) {
		load("C:"+File.separator+"Users"+File.separator+"obena"+File.separator+"Downloads"+File.separator+"ki-turnier-gesamt.csv");
	}
	
	public static void load(String filepath) {
		File file = new File(filepath);
		SevenWondersController con = Main.getSWController();
		
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			
			int counter = 0;
			String line = null;
			line = in.readLine(); // age,card
			String wonder1 = in.readLine(); //
			String wonder2 = in.readLine();
			
			Player p = con.getPlayerController().createPlayer("Spieler", wonder1);
			ArtInt ai = con.getPlayerController().createAI("AI-Spieler", wonder2, Difficulty.HARDCORE);
			
			Game game = con.getGameController().createGame("KI-Turnier", new ArrayList<Player>(Arrays.asList(p,ai)));
			
			while((line = in.readLine()) != null) {
				if(counter > 0) {
					
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

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
	
	public static Image toImage(String path) throws IOException {
		return new Image(new FileInputStream(path));
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
