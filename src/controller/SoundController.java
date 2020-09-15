package controller;

import java.util.ArrayList;
import application.Main;
import application.Utils;
import controller.sound.Sound;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/** Sound Controller for sounds in Game */
public class SoundController {

	private double volume = 1.0;
	/** mute sound */
	private boolean mute = false;
	/** list of media players */
	private ArrayList<SoundPlayer> players;

	/**
	 * create new Sound Controller
	 */
	public SoundController() {
		players = new ArrayList<SoundPlayer>();
	}

	/**
	 * set Volume for the Background Music!
	 * 
	 * @param vol between 0.0 and 1.0
	 */
	public void setVolume(double vol) {
		this.volume = vol;
		for (SoundPlayer player : players)
			if (player.getSound() == Sound.BACKGROUND_GAME || player.getSound() == Sound.BACKGROUND_MENU)
				player.setVolume(vol);
	}

	/**
	 * plays sound
	 * 
	 * @param sound name of sound
	 */
	public void play(Sound sound) {
		play(sound, false);
	}

	/**
	 * stops sound
	 * 
	 * @param sound name of sound
	 */
	public void stop(Sound sound) {
		SoundPlayer remove = null;
		for (SoundPlayer player : players) {
			if (player.getSound() == sound) {
				player.stop();
				remove = player;
				break;
			}
		}

		if (remove != null)
			players.remove(remove);
	}

	/**
	 * plays sound with/without loop
	 * 
	 * @param sound name of sound
	 * @param loop true if loop, false otherwise
	 */
	public void play(Sound sound, boolean loop) {
		if (Main.TEST)
			return;
		if (!isMuted() || (this.mute && loop)) {
			for (SoundPlayer player : players)
				for (String soundname : player.filenames)
					for (String soundname2 : sound.getSoundFilenames())
						if (soundname.equals(soundname2))
							return;
			SoundPlayer player = new SoundPlayer(sound, (loop ? volume : 1.0));
			if (loop)
				player.setLoop();
			else
				player.setAutoRemove();

			if (!this.mute) {
				player.play();
			}
			players.add(player);
		}
	}

	/**
	 * stops all sounds
	 */
	public void stopAll() {
		while (!players.isEmpty())
			players.get(0).stop();
		players.clear();
	}

	/**
	 * mutes or unmutes sound
	 * 
	 * @return true if muted
	 */
	public boolean mute() {
		this.mute = !this.mute;
		for (SoundPlayer player : players)
			if (this.mute)
				player.pause();
			else
				player.play();

		return mute;
	}

	/**
	 * @return true if muted
	 */
	public boolean isMuted() {
		return mute;
	}

	/**
	 * @param btn  adds mute button
	 * @param imgv adds mute button image
	 */
	public static void addMuteFunction(Button btn, ImageView imgv) {
		btn.setOnAction(event -> { Main.getSWController().getSoundController().mute(); updateMuteIcon(imgv); });
		updateMuteIcon(imgv);
	}

	/**
	 * @param imgv updates mute button image
	 */
	public static void updateMuteIcon(ImageView imgv) {
		if (Main.getSWController().getSoundController().isMuted())
			imgv.setImage(new Image(Main.cldr.getResourceAsStream(Main.DEFAULT_PATH + "musicoff.png")));
		else
			imgv.setImage(new Image(Main.cldr.getResourceAsStream(Main.DEFAULT_PATH + "music.png")));
	}

	private class SoundPlayer {
		private MediaPlayer player;
		private int index;
		private String[] filenames;
		private Sound sound;

		public SoundPlayer(Sound sound, double volume) {
			this.sound = sound;
			this.filenames = sound.getSoundFilenames();
			this.index = Utils.randInt(0, this.filenames.length - 1);

			String mediaURL = this.getClass().getClassLoader().getResource(Main.SOUNDS_PATH + this.filenames[this.index] + ".mp3").toExternalForm();
			this.player = new MediaPlayer(new Media(mediaURL));
			setVolume(volume);
		}

		public void setVolume(double vol) {
			this.player.setVolume(vol);
		}

		public void setAutoRemove() {
			SoundPlayer tthis = this;
			player.setOnEndOfMedia(() -> players.remove(tthis));
		}

		public boolean setLoop() {
			if (filenames.length > 1)
				player.setOnEndOfMedia(() -> {
					this.index++;
					if (this.filenames.length <= this.index)
						this.index = 0;

					this.player.stop();
					this.player = new MediaPlayer(new Media(this.getClass().getClassLoader().getResource(Main.SOUNDS_PATH + this.filenames[this.index] + ".mp3").toExternalForm()));
					setVolume(volume);
					this.player.play();
					setLoop();
				});
			else
				player.setCycleCount(MediaPlayer.INDEFINITE);
			return filenames.length > 1;
		}

		public Sound getSound() {
			return this.sound;
		}

		public void stop() {
			this.player.stop();
			players.remove(this);
		}

		public void pause() {
			this.player.pause();
		}

		public void play() {
			this.player.play();
		}
	}
}