package model.player.multiplayer.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import main.api.packet.Packet;
import model.player.ai.Move.Action;

@SuppressWarnings("all")
public class PlayerActionPacket extends Packet{

	private Action action;
	
	public PlayerActionPacket() {}
	
	public PlayerActionPacket(Action action) {
		this.action = action;
	}

	@Override
	public void parseFromInput(DataInputStream in) throws IOException {
		this.action = Action.values()[in.readInt()];
	}

	@Override
	public void writeToOutput(DataOutputStream out) throws IOException {
		out.writeInt(this.action.ordinal());
	}
	
	public Action getAction() {
		return this.action;
	}
	
}
