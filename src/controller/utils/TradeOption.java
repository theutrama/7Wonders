package controller.utils;

import java.io.IOException;

import application.Main;
import application.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import model.player.Player;

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
	 * @return the resources traded with the left neighbour. A null value means there is no trade with the left neighbour.
	 */
	public ResourceBundle getLeftTrade() {
		return leftTrade;
	}

	/**
	 * getter for {@link #rightTrade}
	 * 
	 * @return the resources traded with the right neighbour. A null value means there is no trade with the right neighbour.
	 */
	public ResourceBundle getRightTrade() {
		return rightTrade;
	}

	/**
	 * getter for {@link #leftCost}
	 * 
	 * @return the left cost. This value is 0 if and only if {@link #getLeftTrade()} is null.
	 */
	public int getLeftCost() {
		return leftCost;
	}

	/**
	 * getter for {@link #rightCost}
	 * 
	 * @return the right cost. This value is 0 if and only if {@link #getRightTrade()} is null.
	 */
	public int getRightCost() {
		return rightCost;
	}

	/**
	 * get description string
	 */
	@Override
	public String toString() {
		return "[" + String.valueOf(leftTrade) + "] (" + leftCost + " coins) <-> [" + String.valueOf(rightTrade) + "] (" + rightCost + " coins)";
	}

	/**
	 * creates the line represention this trade including a button
	 * 
	 * @param player      the trading player
	 * @param tradeAction the action executed by the created button
	 * @return a node that contains all resources
	 */
	public HBox getNode(Player player, EventHandler<ActionEvent> tradeAction) {
		HBox hbox = new HBox(5);
		if (leftCost != 0) {
			hbox.getChildren().add(leftTrade.createResourceImages());
			hbox.getChildren().add(new Label("an " + Main.getSWController().getPlayerController().getLeftNeighbour(player).getName()));
			hbox.getChildren().add(createCoinsNode(leftCost));
			if (rightCost != 0)
				hbox.getChildren().add(new Label(" und "));
		}
		if (rightCost != 0) {
			hbox.getChildren().add(rightTrade.createResourceImages());
			hbox.getChildren().add(new Label("an " + Main.getSWController().getPlayerController().getRightNeighbour(player).getName()));
			hbox.getChildren().add(createCoinsNode(rightCost));
		}
		Button btn = new Button("Kaufen");
		btn.setOnAction(tradeAction);
		hbox.getChildren().add(btn);

		return hbox;
	}

	/**
	 * create a node that shows a specific amount of coins
	 * 
	 * @param coins number of coins
	 * @return node
	 */
	private HBox createCoinsNode(int coins) {
		HBox hbox = new HBox();
		hbox.getChildren().add(new Label("(" + coins));
		try {
			hbox.getChildren().add(new ImageView(Utils.toImage("../view/images/tokens/coin.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		hbox.getChildren().add(new Label(")"));
		return hbox;
	}

}
