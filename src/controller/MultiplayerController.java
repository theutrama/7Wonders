package controller;

import java.io.IOException;
import java.net.UnknownHostException;

import application.Main;
import main.api.events.EventListener;
import main.api.events.EventManager;
import main.api.events.events.PacketReceiveEvent;
import main.api.packet.Packet;
import main.client.PlayerClient;
import main.client.connector.PacketListener;
import main.client.packets.PingPacket;

@SuppressWarnings({"javadoc", "all", "PMD"})
/** Controller for Multiplayer */
public class MultiplayerController implements EventListener{
	/** client of player */
	private PlayerClient client;
	
	/** create new Multiplayer Controller */
	public MultiplayerController() {
		Packet.loadPackets();
		EventManager.register(this);
	}

	/**
	 * handling received Package
	 * @param ev	Event for received package
	 */
	@main.api.events.EventHandler
	public void rec(PacketReceiveEvent ev) {
		if(ev.getPacket() instanceof PingPacket)return;
		
		if(Main.primaryStage.getScene().getRoot() instanceof PacketListener) {
			System.out.println("RECEIVED PAKCET FOR PACKETLISTENER "+ev.getPacket().getPacketName());
			PacketListener view = (PacketListener)Main.primaryStage.getScene().getRoot();
			view.handle(ev.getPacket());
		}
	}
	
	/** 
	 * getter for {@link #client}
	 * @return	client
	 */
	public PlayerClient getClient() {
		return this.client;
	}
	
	/**
	 * connects to given host and address
	 * @param name		name of host
	 * @param address	address of host
	 */
	public void connect(String name, String address) {
		String host = "";
		int port = 6000;
		if(address.contains(":")) {
			String[] split = address.split(":");
			host = split[0];
			
			try {
				port = Integer.valueOf(split[1]);
			}catch(Exception e) {
				port = 6000;
			}
		}else host = address;
		
		connect(name, host, port);
	}
	
	/**
	 * connects PlayerClient to host
	 * @param name		name of client
	 * @param host		host for connection
	 * @param port		port for connection
	 */
	public void connect(String name, String host, int port) {
		try {
			this.client = new PlayerClient(name, host, port);
			Main.primaryStage.setOnCloseRequest( s -> {
				this.client.close();
				Main.getSWController().getIOController().saveRanking();
			});
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * checks if client is connected
	 * @return this.client.isConnected() 	true if client is connected
	 */
	public boolean isConnected() {
		return this.client != null && this.client.isConnected();
	}
}
