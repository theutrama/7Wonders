package controller.sound;

public enum Sound {
FIGHT("fight"),
BUTTON_CLICK("click"),
COIN("coin"),
BACKGROUND_MENU("background_menu"),
BACKGROUND_GAME(""),
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
