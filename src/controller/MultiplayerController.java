package controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import application.Main;
import main.api.events.EventHandler;
import main.api.events.EventListener;
import main.api.events.EventManager;
import main.api.events.events.PacketReceiveEvent;
import main.api.packet.Packet;
import main.client.PlayerClient;
import main.client.connector.PacketListener;
import main.client.packets.PingPacket;
import model.board.WonderBoard;
import model.card.Card;
import model.player.Player;
import model.player.multiplayer.Multiplayer;
import model.player.multiplayer.events.PlayerActionEvent;
import model.player.multiplayer.events.PlayerHalikarnassusEvent;
import model.player.multiplayer.events.PlayerSelectedCardEvent;
import model.player.multiplayer.events.PlayerTradeOptionEvent;
import model.player.multiplayer.packets.PlayerActionPacket;
import model.player.multiplayer.packets.PlayerHalikarnassusPacket;
import model.player.multiplayer.packets.PlayerSelectedCardPacket;
import model.player.multiplayer.packets.PlayerTradeOptionPacket;

@SuppressWarnings("all")
/** Controller for Multiplayer */
public class MultiplayerController implements EventListener{
	/** client of player */
	private PlayerClient client;
	
	/** create new Multiplayer Controller */
	public MultiplayerController() {
		Packet.loadPackets();
		Packet.loadPackets("model.player.multiplayer.packets");
		EventManager.register(this);
	}
	
	/**
	 * add a player and assign wonder board
	 * 
	 * @param playername  player's name
	 * @param wonderboard player's board
	 * @return Player object
	 */
	public Player createPlayer(String playername, String wonderboard) {
		WonderBoard board = Main.getSWController().getWonderBoardController().createWonderBoard(wonderboard);
		Player player = new Multiplayer(playername, board);
		board.setPlayer(player);
		return player;
	}
	
	public <E> int indexOf(ArrayList<E> list, E seek) {
		if(list== null && list.isEmpty())throw new NullPointerException("list is null or empty!");
		
		for(int i = 0; i < list.size(); i++)
			if(list.get(i).equals(seek))return i;
		return -1;
	}

	@EventHandler
	public void trade(PlayerTradeOptionEvent ev) {
		if(isConnected()) {
			PlayerTradeOptionPacket packet = new PlayerTradeOptionPacket(ev.getOptionIndex());
			getClient().write(packet);
		}
	}

	@EventHandler
	public void hali(PlayerHalikarnassusEvent ev) {
		if(isConnected()) {
			Card selected = ev.getCard();
			PlayerHalikarnassusPacket packet = new PlayerHalikarnassusPacket(indexOf(Main.getSWController().getGame().getCurrentGameState().getTrash(), selected));
			getClient().write(packet);
		}
	}
	
	@EventHandler
	public void select(PlayerSelectedCardEvent ev) {
		if(isConnected()) {
			Card selected = ev.getCard();
			Player player = ev.getPlayer();
			PlayerSelectedCardPacket packet = new PlayerSelectedCardPacket(indexOf(player.getHand(), selected));
			getClient().write(packet);
		}
	}

	@EventHandler
	public void action(PlayerActionEvent ev) {
		if(isConnected()) {
			PlayerActionPacket packet = new PlayerActionPacket(ev.getAction());
			getClient().write(packet);
		}
	}
	
	/**
	 * handling received Package
	 * @param ev	Event for received package
	 */
	@EventHandler
	public void rec(PacketReceiveEvent ev) {
		if(!isConnected())return;
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
			this.client.addToQueue(PlayerActionPacket.class);
			this.client.addToQueue(PlayerSelectedCardPacket.class);
			this.client.addToQueue(PlayerHalikarnassusPacket.class);
			this.client.addToQueue(PlayerTradeOptionPacket.class);
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
