package uet.oop.bomberman.sound;

import uet.oop.bomberman.entities.MovingEntity.Bomber;
import uet.oop.bomberman.game.BombermanGame;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    public boolean stop = true;

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
        stop = false;
        File file = new File("res/sounds/background.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        if(stop) {
            clip.stop();
        }
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

    public void bomberWinSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("res/sounds/win.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }

    public void stop(){
        stop = true;
    }
}
