package model.ranking;

import java.util.ArrayList;

import controller.IOController;

public class Ranking {
	/** ranking list */
	private ArrayList<PlayerStats> playerStats;

	/** create new ranking object, only called if the input of {@link IOController} fails */
	public Ranking() {
		this.playerStats = new ArrayList<>();
	}

	/**
	 * getter for {@link #playerStats}
	 * 
	 * @return ranking list
	 */
	public ArrayList<PlayerStats> getStats() {
		return this.playerStats;
	}

	/**
	 * adds a new stat to the {@link #playerStats} while maintaining the order
	 * 
	 * @param stats
	 */
	public void addStats(PlayerStats stats) {
		int count = 0;
		while (count < playerStats.size() && stats.getVictoryPoints() < playerStats.get(count).getVictoryPoints())
			count++;
		this.playerStats.add(count, stats);
	}
}
