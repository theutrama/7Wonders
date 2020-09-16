package model.player.multiplayer.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import main.api.packet.Packet;
import model.player.ai.Move.Action;

/** ActionPacket for Player */
@SuppressWarnings("all")
public class CardStackPacket extends Packet{

	/** the action */
	private ArrayList<String> cardStack;
	
	/** CardStackPacket */
	public CardStackPacket() {}
	
	/**
	 * create new PlayerActionPacket
	 * @param action	the action
	 */
	public CardStackPacket(ArrayList<String> cardStack) {
		this.cardStack = cardStack;
	}

	/**
	 * parses Action from InputStream
	 * @param DataInputStream 		the DataInputStream
	 */
	@Override
	public void parseFromInput(DataInputStream input) throws IOException {
		cardStack = new ArrayList<String>();
		int length = input.readInt();
		for(int i = 0; i < length; i++)
			cardStack.add(input.readUTF());
	}

	/**
	 * writes Action into OutputStream
	 * @param DataOutputStream 		the DataOutputStream
	 */
	@Override
	public void writeToOutput(DataOutputStream out) throws IOException {
		out.writeInt(cardStack.size());
		for(String cardname : cardStack)
			out.writeUTF(cardname);
	}
	
	/**
	 * getter for {@link #cardStack}
	 * @return cardStack
	 */
	public ArrayList<String> getCardStack() {
		return this.cardStack;
	}
	
}
