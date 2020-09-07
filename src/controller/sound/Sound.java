package controller.sound;

public enum Sound {
FIGHT("fight"),
BUTTON_CLICK("click"),
COIN("coin"),
BACKGROUND_MENU("background_menu"),
BACKGROUND_GAME(new String[] {"aoe_4","aoe_5","aoe_6","aoe_7","aoe_8"}),
CHOOSE_CARD("place_card"),
BUILD("build"),
KNOCK("knock");
	
	private String[] filename;
	
	Sound(String... filename){
		this.filename=filename;
	}
	
	public String[] getSoundFilenames() {
		return this.filename;
	}
	
}
