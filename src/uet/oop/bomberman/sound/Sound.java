package uet.oop.bomberman.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    public void bombSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("res/sounds/bomb.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();

    }
    public void footSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("res/sounds/footstep.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();

    }
    public void backgroundSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        File file = new File("res/sounds/background.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        //Thread.sleep(10000);
        //clip.start();
    }
    public void enemydieSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("res/sounds/enemydie.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();

    }
    public void bomberdieSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("res/sounds/bomberdie.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();

    }
    public void powerupSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("res/sounds/powerup.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();

    }
}
