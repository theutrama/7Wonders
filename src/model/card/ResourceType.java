package model.card;

import application.Main;

public enum ResourceType {
	
	WOOD(Main.TOKENS_PATH + "wood.png"), 
	BRICK(Main.TOKENS_PATH + "clay.png"), 
	ORE(Main.TOKENS_PATH + "ore.png"), 
	STONE(Main.TOKENS_PATH + "stone.png"), 
	PAPYRUS(Main.TOKENS_PATH + "paper.png"), 
	GLASS(Main.TOKENS_PATH + "glass.png"), 
	CLOTH(Main.TOKENS_PATH + "linen.png"), 
	COINS(Main.TOKENS_PATH + "coin.png"), 
	MILITARY(""),
	/* SCIENCE TYPES */
	TABLET(""), 
	GEAR(""), 
	COMPASS(""), 
	NONE("");

	private String imagePath;
	
	private ResourceType(String imgPath) {
		this.imagePath = imgPath;
	}
	
	public String getImagePath() {
		return this.imagePath;
	}
}
