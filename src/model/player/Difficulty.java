package model.player;

public enum Difficulty {

	EASY, MEDIUM, HARDCORE;

	/**
	 * inverse function to {@link #toString()}
	 * 
	 * @param s description of level
	 * @return the associated difficulty constant
	 */
	public static Difficulty fromString(String string) {
		switch (string.toLowerCase()) {
		case "Einfach":
			return EASY;
		case "Mittel":
			return MEDIUM;
		case "Schwer":
			return HARDCORE;
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
