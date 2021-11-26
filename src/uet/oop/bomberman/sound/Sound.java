package uet.oop.bomberman.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    public void bombSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("D:\\bomber4\\bomberman-starter-starter-2\\src\\uet\\oop\\bomberman\\sound\\bomb.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();

    }
    public void footSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("D:\\bomber4\\bomberman-starter-starter-2\\src\\uet\\oop\\bomberman\\sound\\footstep.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();

    }
}
