package controller.sound;

/** saves certain sounds as enums */
public enum Sound {
FIGHT("fight"),
BUTTON_CLICK("click"),
COIN("coin"),
BACKGROUND_MENU("background_menu"),
BACKGROUND_GAME(new String[] {"aoe_4","aoe_5","aoe_6","aoe_7","aoe_8"}),
CHOOSE_CARD("place_card"),
BUILD("build"),
KNOCK("knock");
	/** name of sound file */
	private String[] filename;
	/**
	 * connects sound to filename
	 * @param filename		name of sound file
	 */
	Sound(String... filename){
		this.filename=filename;
	}
	/** 
	 * getter for {@link #filename}
	 * @return filename
	 */
	public String[] getSoundFilenames() {
		return this.filename;
	}
}
