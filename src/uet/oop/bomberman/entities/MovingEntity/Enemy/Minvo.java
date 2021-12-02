package uet.oop.bomberman.entities.MovingEntity.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.game.Input;
import uet.oop.bomberman.graphics.Sprite;

public class Minvo extends MovingEntity {

    protected Input e_input = new Input();

    protected float maxSpeed = 10f;
    protected float acceleration = 1f;
    public int _timeAfter = 20;

    public Minvo(int xUnit, int yUnit, Image img) {
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
            Image i = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2
                    , Sprite.minvo_right3, _animate, 20).getFxImage();
            setImg(i);
        }
        if (e_input.isLeft()) {
            Image i = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2
                    , Sprite.minvo_left3, _animate, 20).getFxImage();
            setImg(i);
        }
    }

    @Override
    public void update() {
        if(!alive){
            if (_timeAfter > 0)
                _timeAfter--;
            else {
                remove();
                return;
            }
            setImg(Sprite.minvo_dead.getFxImage());
            return;
        }
        startEnemy();
        move();
        animate();
        mapCheck();
        updateE_IMG();
    }

}


