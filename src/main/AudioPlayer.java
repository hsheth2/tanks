package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;

public class AudioPlayer {
	public static void play(String sound) {
		final Clip c;
		final AudioInputStream ais;
		final File f;

		try {
			c = AudioSystem.getClip();
			f = new File("assets/audio/" + sound);
			ais = AudioSystem.getAudioInputStream(f);

			c.open(ais);
			c.start();
			c.addLineListener(new LineListener() {
				@Override
				public void update(LineEvent event) {
					if (event.getType() == Type.STOP) {
						try {
							// System.out.println("closing sound file/thread");
							ais.close();
							c.close();
						} catch (IOException e) {
							e.printStackTrace();
							System.out.println("non-fatal error in sound system");
						}
					}
				}
			});
		} catch (FileNotFoundException | LineUnavailableException e) {
			System.out.println("can not play track: " + sound);
			System.err.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.err.println("No matching interface problem");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
