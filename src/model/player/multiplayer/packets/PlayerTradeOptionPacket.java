package model.player.multiplayer.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import main.api.packet.Packet;
import model.card.Card;
import model.player.ai.Move.Action;

@SuppressWarnings("all")
public class PlayerTradeOptionPacket extends Packet{

	private int optonIndex;
	
	public PlayerTradeOptionPacket() {}
	
	public PlayerTradeOptionPacket(int optonIndex) {
		this.optonIndex = optonIndex;
	}

	@Override
	public void parseFromInput(DataInputStream in) throws IOException {
		this.optonIndex = in.readInt();
	}

	@Override
	public void writeToOutput(DataOutputStream out) throws IOException {
		out.writeInt(this.optonIndex);
	}
	
	public int getTradeOptionIndex() {
		return this.optonIndex;
	}
}
