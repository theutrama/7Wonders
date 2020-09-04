package controller.utils;

public class TradeOption {
	/** the traded resources to each side */
	private ResourceBundle leftTrade, rightTrade;
	/** cost for each trade */
	private int leftCost, rightCost;

	/**
	 * create a trade option
	 * 
	 * @param leftTrade  sets {@link #leftTrade}
	 * @param rightTrade sets {@link #rightTrade}
	 * @param leftCost   sets {@link #leftCost}
	 * @param rightCost  sets {@link #rightCost}
	 */
	public TradeOption(ResourceBundle leftTrade, ResourceBundle rightTrade, int leftCost, int rightCost) {
		super();
		this.leftTrade = leftTrade;
		this.rightTrade = rightTrade;
		this.leftCost = leftCost;
		this.rightCost = rightCost;
	}

	/**
	 * getter for {@link #leftTrade}
	 * 
	 * @return the leftTrade
	 */
	public ResourceBundle getLeftTrade() {
		return leftTrade;
	}

	/**
	 * getter for {@link #rightTrade}
	 * 
	 * @return the rightTrade
	 */
	public ResourceBundle getRightTrade() {
		return rightTrade;
	}

	/**
	 * getter for {@link #leftCost}
	 * 
	 * @return the left cost
	 */
	public int getLeftCost() {
		return leftCost;
	}

	/**
	 * getter for {@link #rightCost}
	 * 
	 * @return the right cost
	 */
	public int getRightCost() {
		return rightCost;
	}

}
