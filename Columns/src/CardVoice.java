import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;

public class CardVoice {


    public static void playVoice(String path)throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File file = new File(path);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
        clip.setMicrosecondPosition(0);
        clip.start();
    }

}