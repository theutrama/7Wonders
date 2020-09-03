package model.player;

public enum Difficulty {

	EASY, MEDIUM, HARDCORE;

	public static Difficulty fromString(String s) {
		switch (s.toLowerCase()) {
		case "einfach": return EASY;
		case "mittel": return MEDIUM;
		case "schwer": return HARDCORE;
		}
		return null;
	}
	
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
