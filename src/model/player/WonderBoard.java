package model.player;

import java.util.ArrayList;

import model.card.Card;
import model.card.Resource;

public abstract class WonderBoard {

	private boolean[] filled;

	private WonderBoard wonderBoard;

	private ArrayList<Card> boardcards;

	private Resource resource;

	private Resource[] slotRequirements;

	public void slot1() {

	}

	public abstract void slot2();

	public void slot3() {

	}

}
