package model.player;

public enum Difficulty {

	EASY, MEDIUM, HARDCORE;

	/**
	 * inverse function to {@link #toString()}
	 * @param s description of level 
	 * @return the associated difficulty constant
	 */
	public static Difficulty fromString(String s) {
		switch (s.toLowerCase()) {
		case "einfach": return EASY;
		case "mittel": return MEDIUM;
		case "schwer": return HARDCORE;
		}
		return null;
	}
	
	/**
	 * returns a String representing the skill level
	 */
	public String toString() {
		switch (this) {
		case EASY:
			return "Einfach";
		case MEDIUM:
			return "Mittel";
		case HARDCORE:
			return "Schwer";
		default:
			return null;
		}
	}

}
