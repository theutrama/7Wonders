package model.card;

public class ScienceCard extends Card {

	private ScienceType scienceType;

	public ScienceCard(ScienceType scienceType) {
		this.scienceType = scienceType;
	}

	public ScienceType getScienceType() {
		return scienceType;
	}
}
