package model.player.multiplayer.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import main.api.packet.Packet;
import model.player.ai.Move.Action;

/** ActionPacket for Player */
@SuppressWarnings("all")
public class StartGamePacket extends Packet{

	/** the action */
	private ArrayList<String> cardStack;
	private HashMap<String,String> wonders;
	
	/** CardStackPacket */
	public StartGamePacket() {}
	
	/**
	 * create new PlayerActionPacket
	 * @param action	the action
	 */
	public StartGamePacket(ArrayList<String> cardStack,HashMap<String,String> wonders) {
		this.cardStack = cardStack;
		this.wonders = wonders;
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
		
		this.wonders = new HashMap<>();
		length = input.readInt();
		for(int i = 0; i < length; i++)
			this.wonders.put(input.readUTF(), input.readUTF());
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
		out.writeInt(this.wonders.size());
		for(String playername : this.wonders.keySet()) {
			out.writeUTF(playername);
			out.writeUTF(this.wonders.get(playername));
		}
	}
	
	/**
	 * getter for {@link #cardStack}
	 * @return cardStack
	 */
	public ArrayList<String> getCardStack() {
		return this.cardStack;
	}
	
	/**
	 * getter for {@link #cardStack}
	 * @return cardStack
	 */
	public HashMap<String,String> getWonders() {
		return this.wonders;
	}
	
}
