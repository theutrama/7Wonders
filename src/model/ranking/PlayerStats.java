package model.ranking;

import java.io.Serializable;
import java.time.LocalDateTime;

/** Game Statistics for a certain player */
public class PlayerStats implements Serializable {
	private static final long serialVersionUID = 1L;
	/** player name */
	private String name;
	/** date and time when the game was finished */
	private LocalDateTime date;
	/** player's victory points */
	private int victoryPoints;
	/** player's lose points */
	private int losePoints;
	/** player's conflict winning points */
	private int conflictPoints;
	/** player's coins */
	private int coins;

	/**
	 * create new stats object, the {@link #date} is set to {@link LocalDateTime#now()}
	 * 
	 * @param name            player name
	 * @param victory         victory points
	 * @param lose            lose points
	 * @param conflict_points conflict winning points
	 * @param coins           coins
	 */
	public PlayerStats(String name, int victory, int lose, int conflictPoints, int coins) {
		this.name = name;
		this.date = LocalDateTime.now();
		this.victoryPoints = victory;
		this.losePoints = lose;
		this.conflictPoints = conflictPoints;
		this.coins = coins;
	}

	/**
	 * getter for {@link #name}
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * getter for {@link #date}
	 * 
	 * @return date and time
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * getter for {@link #victoryPoints}
	 * 
	 * @return victory points
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}

	/**
	 * getter for lose points
	 * 
	 * @return lose points
	 */
	public int getLosePoints() {
		return losePoints;
	}

	/**
	 * getter for {@link #conflictPoints}
	 * 
	 * @return conflict points
	 */
	public int getConflictPoints() {
		return conflictPoints;
	}

	/**
	 * getter for {@link #coins}
	 * 
	 * @return coins
	 */
	public int getCoins() {
		return coins;
	}
}
