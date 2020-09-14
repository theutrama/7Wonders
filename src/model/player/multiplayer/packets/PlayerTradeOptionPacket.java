package model.player.multiplayer.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import main.api.packet.Packet;
import model.card.Card;
import model.player.ai.Move.Action;

/** TradeOptionPacket for Player */
@SuppressWarnings("all")
public class PlayerTradeOptionPacket extends Packet{

	/** index of the trade option */
	private int optionIndex;
	
	/** PlayerTradeOptionPacket */
	public PlayerTradeOptionPacket() {}
	
	/**
	 * create new PlayerTradeOptionPacket
	 * @param optionIndex
	 */
	public PlayerTradeOptionPacket(int optionIndex) {
		this.optionIndex = optionIndex;
	}

	/**
	 * parses optionIndex from InputStream
	 * @param DataInputStream 		the DataInputStream
	 */
	@Override
	public void parseFromInput(DataInputStream in) throws IOException {
		this.optionIndex = in.readInt();
	}

	/**
	 * writes optionIndex into OutputStream
	 * @param DataOutputStream 		the DataOutputStream
	 */
	@Override
	public void writeToOutput(DataOutputStream out) throws IOException {
		out.writeInt(this.optionIndex);
	}
	
	/**
	 * getter for {@link #optionIndex}
	 * @return optionIndex
	 */
	public int getTradeOptionIndex() {
		return this.optionIndex;
	}
}
