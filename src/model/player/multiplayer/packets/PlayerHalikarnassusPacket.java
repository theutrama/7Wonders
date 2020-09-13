package model.player.multiplayer.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import main.api.packet.Packet;
import model.player.ai.Move.Action;

@SuppressWarnings("all")
public class PlayerHalikarnassusPacket extends Packet{

	private int index;
	
	public PlayerHalikarnassusPacket() {}
	
	public PlayerHalikarnassusPacket(int index) {
		this.index = index;
	}

	@Override
	public void parseFromInput(DataInputStream in) throws IOException {
		this.index = in.readInt();
	}

	@Override
	public void writeToOutput(DataOutputStream out) throws IOException {
		out.writeInt(index);
	}
	
	public int getHalikarnassusIndex() {
		return this.index;
	}
	
}
