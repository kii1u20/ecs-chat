package uk.ac.soton.comp1206.utility;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A utility class for quick and handy static functions
 *
 * We will be adding to this later, but you can add things that are handy here too!
 */
public class Utility {

    private static final Logger logger = LogManager.getLogger(Utility.class);
    private static BooleanProperty audioEnabledProperty = new SimpleBooleanProperty(true);
    static MediaPlayer mediaPlayer;

    public static void playAudio(String file) {
        String toPlay = Utility.class.getResource("/" + file).toExternalForm();
        logger.info("Playing audio: " + toPlay);
        Media play = new Media(toPlay);
        mediaPlayer = new MediaPlayer(play);

        try {
            if (getAudioEnabled() == true) {
                mediaPlayer.play();
            }
        } catch (Exception e) {
            setAudioEnabled(false);
            e.printStackTrace();
            logger.error("Unable to play audio file, disabling audio");
        }
    }

    public static void setVolume(Double value) {
        mediaPlayer.setVolume(value);
    }

    public static void setAudioEnabled(boolean enabled) {
        logger.info("Audio enabled set to: " + enabled);
        audioEnabledProperty.set(enabled);
    }

    public static Boolean getAudioEnabled() {
        return audioEnabledProperty.get();
    }

    public static BooleanProperty audioEnabledProperty() {
        return audioEnabledProperty;
    }

	public static double getVolume() {
		return mediaPlayer.getVolume();
	}
}
