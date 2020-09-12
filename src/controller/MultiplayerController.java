package controller;

import java.io.IOException;
import java.net.UnknownHostException;

import application.Main;
import main.api.events.EventHandler;
import main.api.events.EventListener;
import main.api.events.EventManager;
import main.api.events.events.PacketReceiveEvent;
import main.api.packet.Packet;
import main.client.PlayerClient;
import main.lobby.packets.client.PongPacket;
import main.lobby.packets.server.PingPacket;

public class MultiplayerController implements EventListener{
	private PlayerClient client;
	
	public MultiplayerController() {
		Packet.loadPackets();
		
		EventManager.register(this);
	}

	@EventHandler
	public void rec(PacketReceiveEvent ev) {
		if(ev.getPacket() instanceof PingPacket) {
			this.client.write(new PongPacket());
		}
	}
	
	public PlayerClient getClient() {
		return this.client;
	}
	
	public void connect(String name, String adress) {
		String host = "";
		int port = 6000;
		if(adress.contains(":")) {
			String[] split = adress.split(":");
			host = split[0];
			
			try {
				port = Integer.valueOf(split[1]);
			}catch(Exception e) {
				port = 6000;
			}
		}else host = adress;
		
		connect(name, host, port);
	}
	
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
	
	public boolean isConnected() {
		return this.client != null && this.client.isConnected();
	}
}
