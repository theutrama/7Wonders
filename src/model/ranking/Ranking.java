package model.ranking;

import java.util.ArrayList;

public class Ranking {

	private ArrayList<PlayerStats> playerStats;
	
	public Ranking() {
		playerStats = new ArrayList<>();
	}
	
	public void addStats(PlayerStats stats) {
		playerStats.add(stats);
	}

}
