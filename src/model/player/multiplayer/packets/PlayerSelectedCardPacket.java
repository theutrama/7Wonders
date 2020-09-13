package model.player.multiplayer.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import main.api.packet.Packet;
import model.card.Card;
import model.player.ai.Move.Action;

@SuppressWarnings("all")
public class PlayerSelectedCardPacket extends Packet{

	private int handIndex;
	
	public PlayerSelectedCardPacket() {}
	
	public PlayerSelectedCardPacket(int handIndex) {
		this.handIndex = handIndex;
	}

	@Override
	public void parseFromInput(DataInputStream in) throws IOException {
		this.handIndex = in.readInt();
	}

	@Override
	public void writeToOutput(DataOutputStream out) throws IOException {
		out.writeInt(this.handIndex);
	}
	
	public int getHandIndex() {
		return this.handIndex;
	}
}
