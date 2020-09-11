package model.player.ai;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import controller.utils.TradeOption;
import model.GameState;
import model.card.Card;

/** suppresses PMD warnings */
@SuppressWarnings("PMD")

/** calculates next move for AI */
public class Move implements Serializable {
	public static Move parseFromInput(DataInputStream in) {
		ByteArrayInputStream byteIn = null;
		ObjectInputStream objIn = null;
		try {
			int length = in.readInt();
			byte[] ar = new byte[length];
			in.read(ar, 0, length);

			byteIn = new ByteArrayInputStream(ar);
			objIn = new ObjectInputStream(byteIn);
			Move copy = (Move) objIn.readObject();

			return copy;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			if (byteIn != null)
				try {
					byteIn.close();
				} catch (IOException e1) {}
			if (objIn != null)
				try {
					objIn.close();
				} catch (IOException e) {}
		}

		return null;
	}

	private static final long serialVersionUID = 1L;
	/** chosen Card of AI */
	private Card chosen;
	/** calculated action for AI */
	private Action action;
	/** calculated trade option for AI */
	private TradeOption tradeOption;

	/** create new move */
	public Move(Card chosen, Action action) {
		this.chosen = chosen;
		this.action = action;
	}

	/**
	 * deep clone move object
	 * 
	 */
	public void writeToOutput(DataOutputStream out) {
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
			objOut.writeObject(this);
			objOut.flush();
			objOut.close();
			byteOut.close();

			byte[] ar = byteOut.toByteArray();
			out.writeInt(ar.length);
			out.write(ar);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** setter for {@link #tradeOption} */
	public void setTradeOption(TradeOption tradeOption) {
		this.tradeOption = tradeOption;
	}

	/**
	 * getter for {@link #tradeOption}
	 * 
	 * @return tradeOption
	 */
	public TradeOption getTradeOption() {
		return tradeOption;
	}

	/**
	 * getter for {@link #chosen}
	 * 
	 * @return chosen
	 */
	public Card getCard() {
		return chosen;
	}

	/**
	 * getter for {@link #action}
	 * 
	 * @return action
	 */
	public Action getAction() {
		return action;
	}

	/** enum for Action */
	public enum Action {
		OLYMPIA, BUILD, PLACE_SLOT, SELL;
	}
}