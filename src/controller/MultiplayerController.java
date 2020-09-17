package controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import application.Main;
import main.api.events.EventHandler;
import main.api.events.EventListener;
import main.api.events.EventManager;
import main.api.events.events.PacketReceiveEvent;
import main.api.events.events.PacketSendEvent;
import main.api.packet.Packet;
import main.client.PlayerClient;
import main.client.connector.PacketListener;
import main.client.packets.PingPacket;
import main.lobby.packets.server.LobbyClosePacket;
import main.lobby.packets.server.LobbyPlayersPacket;
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
import view.gameboard.GameBoardViewController;
import view.multiplayer.lobby.LobbyViewController;

/** suppresses all warnings */
@SuppressWarnings("all")
/** Controller for Multiplayer */
public class MultiplayerController implements EventListener{
	/** client of player */
	private PlayerClient client;
	/** boolean for ingame */
	private boolean ingame = false;
	public int orderId = 0;
	
	/** create new Multiplayer Controller */
	public MultiplayerController() {
		Packet.loadPackets();
		Packet.loadPackets("model.player.multiplayer.packets");
		EventManager.register(this);
	}
	
	/** 
	 * checks if Multiplayer is ingame
	 * @return	ingame	true if client is in game 
	 */
	public boolean isInGame() {
		return this.ingame;
	}
	
	/**
	 * setter for {@link #ingame}
	 * @param ingame	the boolean for ingame
	 */
	public void setInGame(boolean ingame) {
		if(ingame)orderId=0;
		this.ingame = ingame;
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
	
	/**
	 * finds the index of a certain element of a list
	 * @param <E> 		element
	 * @param list		the list
	 * @param seek		the element whose index is being searched
	 * @return	index of given element
	 */
	public <E> int indexOf(ArrayList<E> list, E seek) {
		if(list== null && list.isEmpty())throw new NullPointerException("list is null or empty!");
		
		for(int i = 0; i < list.size(); i++)
			if(list.get(i).equals(seek))return i;
		return -1;
	}

	/**
	 * writes the TradeOption for client
	 * @param event 	TradeOptionEvent for Player
	 */
	@EventHandler
	public void trade(PlayerTradeOptionEvent event) {
		if(isConnected()) {
			orderId++;
			System.out.println("SEND PlayerTradeOptionPacket "+orderId);
			PlayerTradeOptionPacket packet = new PlayerTradeOptionPacket(event.getOptionIndex());
			getClient().write(packet);
		}
	}

	/**
	 * writes the HalikarnassusEvent for client
	 * @param event		 HalikarnassusEvent for Player
	 */
	@EventHandler
	public void hali(PlayerHalikarnassusEvent event) {
		if(isConnected()) {
			Card selected = event.getCard();
			orderId++;
			System.out.println("SEND PlayerHalikarnassusPacket "+orderId);
			PlayerHalikarnassusPacket packet = new PlayerHalikarnassusPacket(indexOf(Main.getSWController().getGame().getCurrentGameState().getTrash(), selected));
			getClient().write(packet);
		}
	}
	
	/**
	 * writes the SelectedCardEvent for client
	 * @param event		 SelectedCardEvent for Player
	 */
	@EventHandler
	public void select(PlayerSelectedCardEvent event) {
		if(isConnected()) {
			Card selected = event.getCard();
			Player player = event.getPlayer();
			orderId++;
			System.out.println("SEND PlayerSelectedCardPacket "+orderId);
			PlayerSelectedCardPacket packet = new PlayerSelectedCardPacket(indexOf(player.getHand(), selected));
			getClient().write(packet);
		}
	}

	/**
	 * writes the PlayerActionEvent for client
	 * @param event		 ActionEvent for Player
	 */
	@EventHandler
	public void action(PlayerActionEvent event) {
		if(isConnected()) {
			orderId++;
			System.out.println("SEND PlayerActionPacket "+orderId);
			PlayerActionPacket packet = new PlayerActionPacket(event.getAction());
			getClient().write(packet);
		}
	}
	
	/**
	 * sends a packet
	 * @param event		the event to send
	 */
	@EventHandler
	public void send(PacketSendEvent event) {
		if(event.getPacket() instanceof PlayerSelectedCardPacket) {
			PlayerSelectedCardPacket player = event.getPacket(PlayerSelectedCardPacket.class);
			System.out.println("SEND PlayerSelectedCardPacket HandIndex: "+player.getHandIndex());
		}else if(event.getPacket() instanceof PlayerHalikarnassusPacket) {
			PlayerHalikarnassusPacket player = event.getPacket(PlayerHalikarnassusPacket.class);
			System.out.println("SEND PlayerHalikarnassusPacket getHalikarnassusIndex: "+player.getHalikarnassusIndex());
		}else if(event.getPacket() instanceof PlayerTradeOptionPacket) {
			PlayerTradeOptionPacket player = event.getPacket(PlayerTradeOptionPacket.class);
			System.out.println("SEND PlayerTradeOptionPacket getTradeOptionIndex: "+player.getTradeOptionIndex());
		}else if(event.getPacket() instanceof PlayerActionPacket) {
			PlayerActionPacket player = event.getPacket(PlayerActionPacket.class);
			System.out.println("SEND PlayerActionPacket Action: "+player.getAction().name());
		}
	}
	
	/**
	 * handling received Package
	 * @param event		Event for received package
	 */
	@EventHandler
	public void rec(PacketReceiveEvent event) {
		if(!isConnected())return;
		
		if(event.getPacket() instanceof PingPacket)return;
		
		if(!isInGame() && Main.primaryStage.getScene().getRoot() instanceof PacketListener) {
			System.out.println("RECEIVED PAKCET FOR PACKETLISTENER "+event.getPacket().getPacketName());
			PacketListener view = (PacketListener)Main.primaryStage.getScene().getRoot();
			view.handle(event.getPacket());
		}else if(isInGame() && event.getPacket() instanceof LobbyClosePacket) {
			LobbyViewController view = null;
			Main.primaryStage.getScene().setRoot(view=new LobbyViewController());
			view.error("Der Owner hat die Lobby verlassen...");
			Main.getSWController().setGame(null);
		}else if(isInGame() && event.getPacket() instanceof LobbyPlayersPacket) {
			LobbyViewController view = null;
			if(Main.primaryStage.getScene().getRoot() instanceof GameBoardViewController)
				((GameBoardViewController)Main.primaryStage.getScene().getRoot()).exit();
			Main.primaryStage.getScene().setRoot(view=new LobbyViewController());
			view.error("Jemand hat das Spiel verlassen...");
			Main.getSWController().setGame(null);
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
			setInGame(false);
			this.client = new PlayerClient(name, host, port);
			this.client.addToQueue(PlayerActionPacket.class);
			this.client.addToQueue(PlayerSelectedCardPacket.class);
			this.client.addToQueue(PlayerHalikarnassusPacket.class);
			this.client.addToQueue(PlayerTradeOptionPacket.class);
			Main.primaryStage.setOnCloseRequest( stage -> {
				Main.getSWController().getMultiplayerController().close();
				Main.getSWController().getIOController().saveRanking();
				 System.exit(0);
			});
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** closes connection to client */
	public void close(){
		if(this.client != null) {
			this.client.close();
			this.client = null;
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
