package uet.oop.bomberman.entities.MapEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.game.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

public class SpeedItem extends AnimatedEntity {
    Sound sound = new Sound();

    public SpeedItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (checkBoundBomber()) {
            this.remove();
            handleSound();
            BombermanGame.bomberman.setMaxSpeed(6f);
            BombermanGame.getMap1().update((Math.round((y)/ Sprite.SCALED_SIZE)) , Math.round((x)/Sprite.SCALED_SIZE), 0 );
        }
    }
    public void handleSound(){
        try {
            sound.powerupSound();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("powerup sound failed!");
        }
    }
}

