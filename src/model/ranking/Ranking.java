package model.ranking;

import java.util.ArrayList;

public class Ranking {

	private ArrayList<PlayerStats> playerStats;
	
	public Ranking() {
		this.playerStats = new ArrayList<>();
	}
	
	public ArrayList<PlayerStats> getStats(){
		return this.playerStats;
	}
	
	public void addStats(PlayerStats stats) {
		this.playerStats.add(stats);
	}
}
