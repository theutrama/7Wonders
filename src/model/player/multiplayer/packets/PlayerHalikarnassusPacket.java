package model.player.multiplayer.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import main.api.packet.Packet;
import model.player.ai.Move.Action;

/** HalikarnassusPacket for Player */
@SuppressWarnings("all")
public class PlayerHalikarnassusPacket extends Packet{

	/** the index */
	private int index;
	
	/** PlayerHalikarnassusPacket */
	public PlayerHalikarnassusPacket() {}
	
	/**
	 * create new PlayerHalikarnassusPacket
	 * @param index		the index
	 */
	public PlayerHalikarnassusPacket(int index) {
		this.index = index;
	}

	/**
	 * parses index from InputStream
	 * @param DataInputStream 		the DataInputStream
	 */
	@Override
	public void parseFromInput(DataInputStream in) throws IOException {
		this.index = in.readInt();
	}

	/**
	 * writes index into OutputStream
	 * @param DataOutputStream 		the DataOutputStream
	 */
	@Override
	public void writeToOutput(DataOutputStream out) throws IOException {
		out.writeInt(index);
	}
	
	/**
	 * getter for {@link #index}
	 * @return index
	 */
	public int getHalikarnassusIndex() {
		return this.index;
	}
	
}
