package uet.oop.bomberman.entities.MovingEntity.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.game.Input;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends MovingEntity {

    protected Input e_input = new Input();

    protected float maxSpeed = 2f;
    protected float acceleration = 0.4f;

    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void input() {
        if (dx > 0) {
            e_input.setRight(true);
            e_input.setLeft(false);
        } else if (dx < 0) {
            e_input.setRight(false);
            e_input.setLeft(true);
        } else {
            e_input.setLeft(false);
            e_input.setRight(false);
        }
    }

    public void startEnemy() {
        if (e_input.isRight() && isCollide()) {
            setDx(-1f);
        } else if (e_input.isLeft() && isCollide()) {
            setDx(1f);
        }
        input();
    }

    public void move() {
        if (e_input.isLeft()) {
            dx -= acceleration;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
        if (e_input.isRight()) {
            dx += acceleration;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        }
    }

    public void updateE_IMG() {
        if (e_input.isRight()) {
            Image i = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2
                    , Sprite.balloom_right3, _animate, 20).getFxImage();
            setImg(i);
        }
        if (e_input.isLeft()) {
            Image i = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2
                    , Sprite.balloom_left3, _animate, 20).getFxImage();
            setImg(i);
        }
    }

    @Override
    public void update() {
        startEnemy();
        move();
        animate();
        mapCheck();
        updateE_IMG();
    }

}
