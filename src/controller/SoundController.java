package controller;

import java.net.URISyntaxException;
import java.util.ArrayList;

import application.Main;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundController {
	/** mute sound */
	private boolean mute = false;
	/** list of media players */
	private ArrayList<MediaPlayer> players;
	/**
	 * create new Sound Controller
	 */
	public SoundController() {
		players = new ArrayList<MediaPlayer>();	
	}
	/**
	 * plays sound
	 * @param sound 	name of sound
	 */
	public void play(String sound) {
		try {
			MediaPlayer player = new MediaPlayer(
					new Media(getClass().getResource("../sounds/" + sound).toURI().toString()));
			player.setOnEndOfMedia(() -> players.remove(player));
			if (!mute)
				player.play();
			players.add(player);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	/**
	 * mutes or unmutes sound
	 * @return true if muted
	 */
	public boolean mute() {
		if (mute)
			for (MediaPlayer player : players)
				player.play();
		else
			for (MediaPlayer player: players)
				player.stop();
		return (mute ^= true);
	}
	/**
	 * @return true if muted
	 */
	public boolean isMuted() {
		return mute;
	}
	/**
	 * @param btn 		adds mute button
	 * @param imgv		adds mute button image
	 */
	public static void addMuteFunction(Button btn, ImageView imgv) {
		btn.setOnAction(event -> {
			Main.getSWController().getSoundController().mute();
			updateMuteIcon(imgv);
		});
		updateMuteIcon(imgv);
	}
	/**
	 * @param imgv		updates mute button image
	 */
	public static void updateMuteIcon(ImageView imgv) {
		if (Main.getSWController().getSoundController().isMuted())
			imgv.setImage(new Image(SoundController.class.getResourceAsStream("../view/images/musicoff.png")));
		else
			imgv.setImage(new Image(SoundController.class.getResourceAsStream("../view/images/music.png")));
	}
}