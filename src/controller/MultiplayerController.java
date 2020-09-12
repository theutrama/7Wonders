package controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

import main.api.events.EventHandler;
import main.api.events.EventListener;
import main.api.events.EventManager;
import main.api.events.events.PacketReceiveEvent;
import main.client.PlayerClient;
import main.lobby.packets.server.LobbyListPacket;

public class MultiplayerController implements EventListener{
	private PlayerClient client;
	
	public MultiplayerController() {
		EventManager.register(this);
	}

	@EventHandler
	public void rec(PacketReceiveEvent ev) {
		System.out.println("REC PACKET ");
		if(ev.getPacket() instanceof LobbyListPacket) {
			LobbyListPacket packet = (LobbyListPacket) ev.getPacket();
			HashMap<String,Integer> list = packet.getLobbys();
			
			System.out.println("Lobby List: "+list.size());
			for(String name : list.keySet()) {
				int v = list.get(name);
				
				System.out.println("LOBBY: "+name+" "+v);
			}
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
