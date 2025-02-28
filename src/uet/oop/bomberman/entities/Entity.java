package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.game.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected float x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected float y;
    protected Image img;

    protected boolean isRemoved = false;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public float getY() {
        return this.y;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void remove() {
        isRemoved = true;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

    public boolean isRemoved() {
        return isRemoved;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, Sprite.SCALED_SIZE - 5, Sprite.SCALED_SIZE - 5);
    }

    public boolean intersects(Entity e) {
        return e.getBoundary().intersects(this.getBoundary());
    }

}
