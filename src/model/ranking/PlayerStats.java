package model.ranking;

import java.time.LocalDateTime;

public class PlayerStats {

	private String name;
	private LocalDateTime date;

	private int victoryPoints;
	private int losePoints;
	private int conflictPoints;
	private int coins;

	public PlayerStats(String name, int victory, int lose, int conflict_points, int coins) {
		this.name = name;
		this.date = LocalDateTime.now();
		this.victoryPoints = victory;
		this.losePoints = lose;
		this.conflictPoints = conflict_points;
		this.coins = coins;
	}
	
	public String getName() {
		return name;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public int getVictoryPoints() {
		return victoryPoints;
	}
	
	public int getLosePoints() {
		return losePoints;
	}
	
	public int getConflictPoints() {
		return conflictPoints;
	}
	
	public int getCoins() {
		return coins;
	}
}
