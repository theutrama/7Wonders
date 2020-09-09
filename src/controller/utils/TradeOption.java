package controller.utils;

import java.io.IOException;
import java.io.Serializable;

import application.Main;
import application.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import model.player.Player;

/** trade options for left and right neighbors */
public class TradeOption implements Serializable {
	private static final long serialVersionUID = 1L;
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
	public Button getNode(Player player, EventHandler<ActionEvent> tradeAction) {
		HBox hbox = new HBox();
		final ImageView arrowright = new ImageView();
		final ImageView arrowleft = new ImageView();
		if (leftCost != 0) {
			try {
				arrowleft.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowgreyleft.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			arrowleft.setFitWidth(45);
			arrowleft.setFitHeight(25);
			HBox.setMargin(arrowleft, new Insets(0,5.0,0,0));
			Label label = new Label(" von " + Main.getSWController().getPlayerController().getLeftNeighbour(player).getName()+" fuer ");
			label.getStyleClass().addAll("fontstyle","dropshadow");
			hbox.getChildren().addAll(arrowleft,leftTrade.createResourceImages(),label,createCoinsNode(leftCost));
			if (rightCost != 0) {
				Label label2 = new Label(" und ");
				label2.getStyleClass().addAll("fontstyle", "dropshadow");
				hbox.getChildren().add(label2);
			}
			
		}
		if (rightCost != 0) {
			try {
				arrowright.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowgrey.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			arrowright.setFitWidth(45);
			arrowright.setFitHeight(25);
			HBox.setMargin(arrowright, new Insets(0,0,0,3));
			Label label = new Label(" von " + Main.getSWController().getPlayerController().getRightNeighbour(player).getName()+" fuer ");
			label.getStyleClass().addAll("fontstyle","dropshadow");
			hbox.getChildren().addAll(rightTrade.createResourceImages(),label,createCoinsNode(rightCost));
		}
		Label labelbuy = new Label(" handeln");
		hbox.getChildren().add(labelbuy);
		if(rightCost != 0) hbox.getChildren().add(arrowright);
		labelbuy.getStyleClass().addAll("fontstyle", "dropshadow");
		hbox.setAlignment(Pos.CENTER);
		hbox.setStyle("-fx-background-color: white");
		hbox.setStyle("-fx-background-radius: 4px");
		Button btn = new Button();
		btn.setGraphic(hbox);
		btn.setOnAction(tradeAction);
		btn.hoverProperty().addListener((obs, oldVal, newValue) -> {
			try {
				if (newValue) {
					if(arrowleft != null)
						arrowleft.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowhoverleft.png"));
					if(arrowright != null)
						arrowright.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowhover.png"));
				} else {
					if(arrowleft != null)
						arrowleft.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowgreyleft.png"));
					if(arrowright != null)
						arrowright.setImage(Utils.toImage(Main.TOKENS_PATH + "arrowgrey.png"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return btn;
	}

	/**
	 * create a node that shows a specific amount of coins
	 * 
	 * @param coins number of coins
	 * @return node
	 */
	private HBox createCoinsNode(int coins) {
		HBox hbox = new HBox();
		Label label = new Label(""+coins);
		label.getStyleClass().addAll("fontstyle","dropshadow");
		ImageView img = null;
		try {
			img = new ImageView(Utils.toImage(Main.TOKENS_PATH + "coin.png"));
			img.setFitWidth(20);
			img.setFitHeight(20);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HBox.setMargin(label, new Insets(0,2,0,0));
		hbox.getChildren().addAll(label,img);
		hbox.setAlignment(Pos.CENTER);
		return hbox;
	}

	/**
	 * checks if the resources 
	 */
	@Override
	public boolean equals(Object obj) {
		try {
			TradeOption other = (TradeOption) obj;
			return ((leftTrade == null && other.leftTrade == null) || leftTrade.equals(other.leftTrade)) && ((rightTrade == null && other.rightTrade == null) || rightTrade.equals(other.rightTrade));
		} catch (Exception e) {
			return false;
		}
	}
}
