package application;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import controller.CardController;
import controller.PlayerController;
import controller.SevenWondersController;
import javafx.scene.image.Image;
import model.Game;
import model.GameState;
import model.card.Card;
import model.player.Player;
import model.player.ai.ArtInt;
import model.player.ai.Difficulty;

public class Utils {
	private static Random rand = new Random();
	
	//C:\Users\obena\Downloads\ki-turnier-gesamt.csv
	
	
	public static void main(String[] args) {
		load("C:"+File.separator+"Users"+File.separator+"obena"+File.separator+"Downloads"+File.separator+"ki-turnier-beispiel.csv");
	}
	
	public static String toWonder(String w) {
		if(w.equalsIgnoreCase("ephesus"))return "ephesos";
		return w;
	}
	
	public static String toCard(String c,int age) {
		if(c.equalsIgnoreCase("glassworks"))return "glassworks1";
		if(c.equalsIgnoreCase("loom"))return "loom"+age;
		if(c.equalsIgnoreCase("press"))return "press"+age;
		if(c.equalsIgnoreCase("craftsmen_guild"))return "craftsmensguild";
		if(c.contains("_"))return c.replaceAll("_", "");
		return c;
	}
	
	public static void load(String filepath) {
		File file = new File(filepath);
		SevenWondersController con = SevenWondersController.getInstance();
		CardController card_con = con.getCardController();
		PlayerController p_con = con.getPlayerController();
		
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			
			int counter = 0;
			String line = null;
			line = in.readLine(); // age,card
			String wonder1 = in.readLine().split(",")[1]; 
			String wonder2 = in.readLine().split(",")[1];
			
			Player p = p_con.createPlayer("Spieler", toWonder(wonder1));
			ArtInt ai = p_con.createAI("AI-Spieler", toWonder(wonder2), Difficulty.HARDCORE);
			
			Game game = new Game("KI-Turnier");
			ArrayList<Card> cards = new ArrayList<Card>();
			GameState state = new GameState(1, 1, new ArrayList<Player>(Arrays.asList(p,ai)), cards);
			
			String[] split;
			HashMap<Integer,ArrayList<Card>> map = new HashMap<Integer,ArrayList<Card>>();
			while((line = in.readLine()) != null) {
				if(line.contains("")) {
					split = line.split(",");
					
					int age = Integer.valueOf(split[0]);
					String cardname = toCard(split[1],age);

					Card card = card_con.getCard(cardname);
					
					System.out.println("CARD: "+card.getInternalName()+" "+card.getAge()+" == "+age);
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
