package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import org.reflections.Reflections;
import model.board.WonderBoard;

public class WonderBoardController {

	private Random rand = new Random();
	private SevenWondersController sw;

	private ArrayList<Class<? extends WonderBoard>> boards = new ArrayList<Class<? extends WonderBoard>>();
	
	public WonderBoardController(SevenWondersController sw) {
		this.sw=sw;
		loadBoardClasses();
	}
	
	public WonderBoard createWonderBoard(Class<? extends WonderBoard> clazz) throws InstantiationException, IllegalAccessException {
		if(this.boards.contains(clazz)) {
			this.boards.remove(clazz);
			return clazz.newInstance();
		}
		return null;
	}
	
	public WonderBoard createWonderBoard() throws InstantiationException, IllegalAccessException {
		Class<? extends WonderBoard> clazz = this.boards.get( randInt(0,this.boards.size()) );
		return createWonderBoard(clazz);
	}
	
	public int randInt(int min, int max) {
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
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
