package uet.oop.bomberman.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    private static boolean playing;

    public void setPlaying(boolean playing) {
        Sound.playing = playing;
    }

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
        if(playing) {
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.stop();
        }
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

    public void bomberDieSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
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
