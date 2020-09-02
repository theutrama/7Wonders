package model.player;

public enum Difficulty {

	EASY, MEDIUM, HARDCORE;

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
