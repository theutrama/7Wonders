package controller.utils;

/**
 * constants to indicate the way a player can build a card or a wonder stage
 */
public enum BuildCapability {
	/** the player has all cards that are the requirement to build a card for free */
	FREE,
	/** the player has enough resources on his board */
	OWN_RESOURCE,
	/** the player doesn't have enough resources on his board, but has enough coins to buy the missing resources from his neighbours */
	TRADE,
	/** the required resources cannot be collected, including trading */
	NONE
}
