package controller.utils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import application.Main;
import application.Utils;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.card.Resource;
import model.player.Player;

/** stores ResourceBundles for all cards */
public class ResourceBundle implements Serializable {
	private static final long serialVersionUID = 1L;
	/** counters for resource quantities */
	private int wood, stone, ore, cloth, glass, brick, papyrus, coins;

	/**
	 * creates a bundle and adds the given resource using {@link #add(Resource)}
	 * 
	 * @param resource a resource to be added as an initial value
	 */
	public ResourceBundle(Resource resource) {
		add(resource);
	}

	/**
	 * create a bundle and add all given resources using {@link #add(Resource)}
	 * 
	 * @param resources resource list
	 */
	public ResourceBundle(ArrayList<Resource> resources) {
		resources.forEach(resource -> add(resource));
	}

	/** default constructor, all values are 0 */
	public ResourceBundle() {
	}

	/**
	 * constructor to allow adding two bundles
	 * 
	 * @param wood    sets {@link #wood}
	 * @param stone   sets {@link #stone}
	 * @param ore     sets {@link #ore}
	 * @param cloth   sets {@link #cloth}
	 * @param glass   sets {@link #glass}
	 * @param brick   sets {@link #brick}
	 * @param papyrus sets {@link #papyrus}
	 * @param coins	   sets {@link #coins}
	 */
	public ResourceBundle(int wood, int stone, int ore, int cloth, int glass, int brick, int papyrus, int coins) {
		this.wood = wood;
		this.stone = stone;
		this.ore = ore;
		this.cloth = cloth;
		this.glass = glass;
		this.brick = brick;
		this.papyrus = papyrus;
		this.coins = coins;
	}

	/**
	 * calculates the cost of the resources represented by this object for a specified player
	 * 
	 * @param player          player
	 * @param toLeftNeighbour true if these resources are bought from the player's left neighbour
	 * @param twoPlayers      true if two player game
	 * @return cost of these resources
	 */
	public int getCostForPlayer(Player player, boolean toLeftNeighbour, boolean twoPlayers) {
		int cost = brick + stone + wood + ore;
		if (!((toLeftNeighbour || twoPlayers) && Main.getSWController().getCardController().hasCard(player, "westtradingpost"))
				&& !((!toLeftNeighbour || twoPlayers) && Main.getSWController().getCardController().hasCard(player, "easttradingpost")))
			cost *= 2;

		int cost2 = glass + cloth + papyrus;
		if (!Main.getSWController().getCardController().hasCard(player, "marketplace"))
			cost2 *= 2;

		return cost + cost2;
	}

	/**
	 * adds the given resource by increasing the associated integer value by it's quantity
	 * 
	 * @param resource a resource
	 */
	public void add(Resource resource) {
		switch (resource.getType()) {
		case WOOD:
			wood += resource.getQuantity();
			break;
		case STONE:
			stone += resource.getQuantity();
			break;
		case ORE:
			ore += resource.getQuantity();
			break;
		case CLOTH:
			cloth += resource.getQuantity();
			break;
		case GLASS:
			glass += resource.getQuantity();
			break;
		case BRICK:
			brick += resource.getQuantity();
			break;
		case PAPYRUS:
			papyrus += resource.getQuantity();
			break;
		case COINS:
			coins += resource.getQuantity();
		default:
			break;
		}
	}

	/**
	 * add a resource to this one and get the result
	 * 
	 * @param bundle a resource bundle
	 * @return a new resource bundle representing the combined resource requirements
	 */
	public ResourceBundle add(ResourceBundle bundle) {
		return new ResourceBundle(wood + bundle.wood, stone + bundle.stone, ore + bundle.ore, cloth + bundle.cloth, glass + bundle.glass, brick + bundle.brick, papyrus + bundle.papyrus,
				coins + bundle.coins);
	}

	/**
	 * get missing amount of resources relativ to the given bundle
	 * 
	 * @param bundle a resource bundle
	 * @return a new bundle that represents the atomic differences of all resource types: (bundle - this). If this bundle has more or the same amount of a resource than the given
	 *         bundle, the asociated value of the returned bundle is 0.
	 */
	public ResourceBundle getMissing(ResourceBundle bundle) {
		return new ResourceBundle(Math.max(bundle.wood - wood, 0), Math.max(bundle.stone - stone, 0), Math.max(bundle.ore - ore, 0), Math.max(bundle.cloth - cloth, 0),
				Math.max(bundle.glass - glass, 0), Math.max(bundle.brick - brick, 0), Math.max(bundle.papyrus - papyrus, 0), Math.max(bundle.coins - coins, 0));
	}

	/**
	 * compare two resource bundles
	 * 
	 * @param bundle another bundle
	 * @return true if and only if all atomic values of this bundle are greater or equal than the same value of bundle
	 */
	public boolean greaterOrEqualThan(ResourceBundle bundle) {
		return wood >= bundle.wood && stone >= bundle.stone && ore >= bundle.ore && cloth >= bundle.cloth && glass >= bundle.glass && brick >= bundle.brick && papyrus >= bundle.papyrus
				&& coins >= bundle.coins;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			ResourceBundle bundle = (ResourceBundle) obj;
			return wood == bundle.wood && stone == bundle.stone && ore == bundle.ore && cloth == bundle.cloth && glass == bundle.glass && brick == bundle.brick && papyrus == bundle.papyrus
					&& coins == bundle.coins;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * getter for {@link #coins}
	 * 
	 * @return coins
	 */
	public int getCoins() {
		return coins;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (wood > 0)
			builder.append(wood + " Holz");
		if (stone > 0)
			builder.append(stone + " Stein");
		if (ore > 0)
			builder.append(ore + " Erz");
		if (brick > 0)
			builder.append(brick + " Ziegel");
		if (cloth > 0)
			builder.append(cloth + " Stoff");
		if (glass > 0)
			builder.append(glass + " Glass");
		if (papyrus > 0)
			builder.append(papyrus + " Holz");
		return builder.toString();
	}

	/**
	 * creates a Hbox of images that represent the amount of resources
	 * 
	 * @return hbox
	 */
	public HBox createResourceImages() {
		HBox hbox = new HBox();
		try {
			for (int i = 0; i < wood; i++)
				hbox.getChildren().add(getImageView(Utils.toImage(Main.TOKENS_PATH + "wood.png")));
			for (int i = 0; i < stone; i++)
				hbox.getChildren().add(getImageView(Utils.toImage(Main.TOKENS_PATH + "stone.png")));
			for (int i = 0; i < brick; i++)
				hbox.getChildren().add(getImageView(Utils.toImage(Main.TOKENS_PATH + "clay.png")));
			for (int i = 0; i < ore; i++)
				hbox.getChildren().add(getImageView(Utils.toImage(Main.TOKENS_PATH + "ore.png")));
			for (int i = 0; i < glass; i++)
				hbox.getChildren().add(getImageView(Utils.toImage(Main.TOKENS_PATH + "glass.png")));
			for (int i = 0; i < cloth; i++)
				hbox.getChildren().add(getImageView(Utils.toImage(Main.TOKENS_PATH + "linen.png")));
			for (int i = 0; i < papyrus; i++)
				hbox.getChildren().add(getImageView(Utils.toImage(Main.TOKENS_PATH + "paper.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		hbox.setAlignment(Pos.CENTER);
		return hbox;
	}

	/**
	 * creates an ImageView with a size of 20x20
	 * 
	 * @param img image
	 * @return imageview
	 */
	private ImageView getImageView(Image img) {
		ImageView image = new ImageView(img);
		image.setFitWidth(25);
		image.setFitHeight(25);
		return image;
	}
}
