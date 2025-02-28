package uet.oop.bomberman.entities.MovingEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.game.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.TileMap;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class MovingEntity extends AnimatedEntity {

    public final int tileSize = Sprite.SCALED_SIZE;
    public boolean alive;
    //SPEED
    protected float dx = 0.1f;
    protected float dy = 0f;
    protected boolean collide = false;
    protected int currFrame = 0;

    public MovingEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        alive = true;
    }
    public void killed(){
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public void mapCheck() {
        TileMap map = BombermanGame.getMap1();
        int[][] mapArr = BombermanGame.getMap1().getMap();
        int x1;
        int x2;

        int y1;
        int y2;

        /**
         * HORIZONTAL.
         */

        x1 = Math.round ((x + dx - 16) / tileSize);
        x2 = Math.round (x + tileSize + dx - 8) / tileSize;

        y1 = Math.round ((y + 2f) / tileSize);
        y2 = Math.round (y + tileSize - 1) / tileSize;

        if (x1 >= 0 && x2 < map.getMapWidth() && y1 >= 0 && y2 < map.getMapHeight()) {
            if (dx > 0) {
                if (mapArr[y1][x2] != 0 || mapArr[y2][x2] != 0) {
                    collide = true;
                    x = x2 * tileSize;
                    x -= tileSize - 4;
                    dx = 0.1f;
                } else {
                    collide = false;
                }
            } else if (dx < 0) {
                if (mapArr[y2][x1] != 0 || mapArr[y1][x1] != 0) {
                    collide = true;
                    x = (x1 + 1) * tileSize;
                    dx = 0.1f;
                } else {
                    collide = false;
                }
            }
        }

        /**
         * VERTICAL.
         */

        x1 = Math.round ((x)/ tileSize);
        x2 = Math.round (x + tileSize - 26) / tileSize;

        y1 = Math.round ((y + dy - 8)/ tileSize);
        y2 = Math.round (y + dy + tileSize - 1) / tileSize;

        if (x1 > 0 && x2 < map.getMapWidth() && y1 >= 0 && y2 < map.getMapHeight()) {
            if (dy > 0) {
                if (mapArr[y2][x1] != 0 || mapArr[y2][x2] != 0) {
                    collide = true;
                    y = y2 * tileSize;
                    y -= tileSize + 1;
                    dy = 0;
                } else {
                    collide = false;
                }
            } else if (dy < 0) {
                if (mapArr[y1][x1] != 0 || mapArr[y1][x2] != 0) {
                    collide = true;
                    y = (y1 + 0.65f) * tileSize;
                    dy = 0;
                } else {
                    collide = false;
                }
            }
        }
        x += dx;
        y += dy;
    }

    public boolean isCollide() {
        return collide;
    }

    public void setCollide(boolean collide) {
        this.collide = collide;
    }

    public void checkCollisionMap() {
        if (collide) {
            dx = 0;
            dy = 0;
        }
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    @Override
    public void update() {

    }
}
