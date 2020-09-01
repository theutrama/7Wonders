package model.player;

import java.util.ArrayList;

import model.card.Card;

public class Player {

	private String name;

	private int coints;

	private int victory_points;

	private int lose_points;

	private int conflict_points;

	private boolean mausoleum;

	private ArrayList<Card> hand;

	private WonderBoard board;

}
