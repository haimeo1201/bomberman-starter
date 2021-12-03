package uet.oop.bomberman.entities.MovingEntity.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.game.Input;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends MovingEntity {

    protected Input e_input = new Input();

    protected float maxSpeed = 2f;
    protected float acceleration = 0.4f;
    public int _timeAfter = 20;

    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.dy = 0.01f;
        this.dx = 0f;
    }

    public void input() {
        if (dy > 0) {
            e_input.setUp(false);
            e_input.setDown(true);
        } else if (dy < 0) {
            e_input.setUp(true);
            e_input.setDown(false);
        } else {
            e_input.setUp(false);
            e_input.setDown(false);
        }
    }

    public void startEnemy() {
        if (e_input.isDown() && isCollide()) {
            setDy(-1f);
        } else if (e_input.isUp() && isCollide()) {
            setDy(1f);
        }
        input();
    }

    public void move() {
        if (e_input.isUp()) {
            dy -= acceleration;
            if (dy < -maxSpeed) {
                dy = -maxSpeed;
            }
        }
        if (e_input.isDown()) {
            dy += acceleration;
            if (dy > maxSpeed) {
                dy = maxSpeed;
            }
        }
    }

    public void updateE_IMG() {
        if (e_input.isUp()) {
            Image i = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2
                    , Sprite.doll_right3, _animate, 20).getFxImage();
            setImg(i);
        }
        if (e_input.isDown()) {
            Image i = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2
                    , Sprite.doll_left3, _animate, 20).getFxImage();
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
            setImg(Sprite.doll_dead.getFxImage());
            return;
        }
        startEnemy();
        move();
        animate();
        mapCheck();
        updateE_IMG();
    }

}

