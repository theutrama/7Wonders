package model.card;

import java.io.File;

public enum ResourceType {
	
	WOOD("src"+File.separator + "view" + File.separator + "images" + File.separator + "tokens" + File.separator + "wood.png"), 
	BRICK("src"+File.separator + "view" + File.separator + "images" + File.separator + "tokens" + File.separator + "clay.png"), 
	ORE("src"+File.separator + "view" + File.separator + "images" + File.separator + "tokens" + File.separator + "ore.png"), 
	STONE("src"+File.separator + "view" + File.separator + "images" + File.separator + "tokens" + File.separator + "stone.png"), 
	PAPYRUS("src"+File.separator + "view" + File.separator + "images" + File.separator + "tokens" + File.separator + "paper.png"), 
	GLASS("src"+File.separator + "view" + File.separator + "images" + File.separator + "tokens" + File.separator + "glass.png"), 
	CLOTH("src"+File.separator + "view" + File.separator + "images" + File.separator + "tokens" + File.separator + "linen.png"), 
	COINS("src"+File.separator + "view" + File.separator + "images" + File.separator + "tokens" + File.separator + "coin.png"), 
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
