package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.game.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class AnimatedEntity extends Entity {
    public boolean destroyed = false;
    protected final int MAX_ANIMATE = 600;
    protected int _animate = 0;

    public AnimatedEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }


    public void blown(){
        destroyed = true;
    }

    protected void animate() {
        if (_animate < MAX_ANIMATE) _animate++;
        else _animate = 0; //reset animation
    }

    @Override
    public void update() {

    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    }

    public boolean intersects(Entity e) {
        return e.getBoundary().intersects(this.getBoundary());
    }

    protected boolean checkBoundBomber() {
        for (MovingEntity e: BombermanGame.movableEntities ) {
            if (this.intersects(e)) {
                return true;
            }
        }
        return false;
    }
}
