package application;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import controller.CardController;
import controller.GameController;
import controller.PlayerController;
import controller.SevenWondersController;
import controller.exceptions.CardOutOfAgeException;
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
		try {
			load("C:"+File.separator+"Users"+File.separator+"obena"+File.separator+"Downloads"+File.separator+"ki-turnier-beispiel.csv");
		} catch (CardOutOfAgeException e) {
			e.printStackTrace();
		}
	}
	
	public static String toWonder(String w) {
		if(w.equalsIgnoreCase("ephesus"))return "ephesos";
		return w;
	}
	
	public static String toCard(String c,int age) {
		if(c.equalsIgnoreCase("glassworks"))return "glassworks"+age;
		if(c.equalsIgnoreCase("loom"))return "loom"+age;
		if(c.equalsIgnoreCase("press"))return "press"+age;
		if(c.equalsIgnoreCase("craftsmen_guild"))return "craftsmensguild";
		if(c.contains("_"))return c.replaceAll("_", "");
		return c;
	}
	
	public static void load(String filepath) throws CardOutOfAgeException {
		File file = new File(filepath);
		SevenWondersController con = SevenWondersController.getInstance();
		GameController game_con = con.getGameController();
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
			con.setGame(game);
			ArrayList<Card> cards = new ArrayList<Card>();
			
			
			String[] split;
			while((line = in.readLine()) != null) {
				if(line.contains(",")) {
					split = line.split(",");
					int age = Integer.valueOf(split[0]);
					String cardname = toCard(split[1],age);
					Card card = card_con.getCard(cardname);
					
					if(age != card.getAge()) {
						throw new CardOutOfAgeException(card,age);
					}
					
					cards.add(card);
				}else System.out.println("This thing shouldn't never happen?! LINE:"+line);
			}
			
			//Reverse Array to get the right order by taking from the TOP
			Collections.reverse(cards);
			game_con.nextAge(game, new GameState(0, 1, new ArrayList<Player>(Arrays.asList(p,ai)), cards));
			game.getCurrentGameState().setFirstPlayer(0);
			game.getCurrentGameState().setCurrentPlayer(0);
			
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
