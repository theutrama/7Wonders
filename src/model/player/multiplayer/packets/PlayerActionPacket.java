package model.player.multiplayer.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import main.api.packet.Packet;
import model.player.ai.Move.Action;

/** ActionPacket for Player */
@SuppressWarnings("all")
public class PlayerActionPacket extends Packet{

	/** the action */
	private Action action;
	
	/** PlayerActionPacket */
	public PlayerActionPacket() {}
	
	/**
	 * create new PlayerActionPacket
	 * @param action	the action
	 */
	public PlayerActionPacket(Action action) {
		this.action = action;
	}

	/**
	 * parses Action from InputStream
	 * @param DataInputStream 		the DataInputStream
	 */
	@Override
	public void parseFromInput(DataInputStream input) throws IOException {
		this.action = Action.values()[input.readInt()];
	}

	/**
	 * writes Action into OutputStream
	 * @param DataOutputStream 		the DataOutputStream
	 */
	@Override
	public void writeToOutput(DataOutputStream out) throws IOException {
		out.writeInt(this.action.ordinal());
	}
	
	/**
	 * getter for {@link #action}
	 * @return action
	 */
	public Action getAction() {
		return this.action;
	}
	
}
