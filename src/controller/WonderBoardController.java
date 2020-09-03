package controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import org.reflections.Reflections;

import application.Utils;
import model.board.BabylonBoard;
import model.board.WonderBoard;
import model.card.Card;
import model.player.Player;

public class WonderBoardController {

	private SevenWondersController swController;

	private ArrayList<Class<? extends WonderBoard>> boards = new ArrayList<Class<? extends WonderBoard>>();
	
	public WonderBoardController(SevenWondersController swController) {
		this.swController=swController;
		loadBoardClasses();
	}
	
	public WonderBoard createWonderBoard(String name) {
		return createWonderBoard(getClassByName(name));
	}
	
	public WonderBoard createWonderBoard(Class<? extends WonderBoard> clazz) {
		if(this.boards.isEmpty())loadBoardClasses();
		
		if(this.boards.contains(clazz)) {
			this.boards.remove(clazz);
			try {
				Constructor<? extends WonderBoard> con = clazz.getConstructor();
				return con.newInstance(new Object[]{});
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private String toName(Class<? extends WonderBoard> clazz) {
		return clazz.getSimpleName().replaceAll("Board", "");
	}
	
	public Class<? extends WonderBoard> getClassByName(String name){
		for(Class<? extends WonderBoard> clazz : this.boards) {
			if(toName(clazz).equalsIgnoreCase(name))return clazz;
		}
		
		return null;
	}
	
	public String[] getWonderBoardNames() {
		String[] names = new String[this.boards.size()];
		
		for(int i = 0; i < this.boards.size(); i++) {
			names[i] = toName(this.boards.get(i));
		}
		return names;
	}
	
	public WonderBoard createWonderBoard() {
		Class<? extends WonderBoard> clazz = this.boards.get( Utils.randInt(0,this.boards.size()) );
		return createWonderBoard(clazz);
	}

	public void reset() {
		this.boards.clear();
	}
	
	private void loadBoardClasses() {
		Reflections reflections = new Reflections( "model.board" );
		List<Class<? extends WonderBoard>> moduleClasses = new ArrayList<>( reflections.getSubTypesOf( WonderBoard.class ) );

		Collections.sort(moduleClasses, new Comparator<Class<? extends WonderBoard>>() {
		    @Override
		    public int compare(Class<? extends WonderBoard> o1, Class<? extends WonderBoard> o2) {
		        return o1.getSimpleName().compareTo(o2.getSimpleName());
		    }
		});
		
		for ( Class<? extends WonderBoard> clazz : moduleClasses ){
			this.boards.add(clazz);
		}
	}
}
