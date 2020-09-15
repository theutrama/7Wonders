package model.player.multiplayer.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import main.api.packet.Packet;
import model.card.Card;
import model.player.ai.Move.Action;

/** SelectedCardPacket for Player */
@SuppressWarnings("all")
public class PlayerSelectedCardPacket extends Packet{

	/** index of selected Card */
	private int handIndex;
	
	/** PlayerSelectedCardPacket */
	public PlayerSelectedCardPacket() {}
	
	/**
	 * create new PlayerSelectedCardPacket
	 * @param handIndex		the index of the selected Card 
	 */
	public PlayerSelectedCardPacket(int handIndex) {
		this.handIndex = handIndex;
	}

	/**
	 * parses handIndex from InputStream
	 * @param DataInputStream 		the DataInputStream
	 */
	@Override
	public void parseFromInput(DataInputStream input) throws IOException {
		this.handIndex = input.readInt();
	}

	/**
	 * writes handIndex into OutputStream
	 * @param DataOutputStream 		the DataOutputStream
	 */
	@Override
	public void writeToOutput(DataOutputStream out) throws IOException {
		out.writeInt(this.handIndex);
	}
	
	/**
	 * getter for {@link #handIndex}
	 * @return handIndex
	 */
	public int getHandIndex() {
		return this.handIndex;
	}
}
