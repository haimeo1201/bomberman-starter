package uet.oop.bomberman.entities.MovingEntity.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.game.Input;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

public class Kondoria extends MovingEntity {

    protected Input e_input = new Input();

    protected float maxSpeed = 6f;
    protected float acceleration = 0.4f;
    public int _timeAfter = 20;
    Sound sound = new Sound();

    public Kondoria(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.dx = -0.1f;
    }

    public void input() {
        if (dx > 0 && dy == 0f) {
            e_input.setRight(true);
            e_input.setLeft(false);
            e_input.setUp(false);
            e_input.setDown(false);
        } else if (dx < 0 && dy == 0f) {
            e_input.setRight(false);
            e_input.setLeft(true);
            e_input.setUp(false);
            e_input.setDown(false);
        } else if (dy < 0 && dx == 0f) {
            e_input.setLeft(false);
            e_input.setRight(false);
            e_input.setUp(true);
            e_input.setDown(false);
        } else if (dy > 0 && dx == 0f) {
            e_input.setLeft(false);
            e_input.setRight(false);
            e_input.setUp(false);
            e_input.setDown(true);
        } else {
            e_input.setLeft(false);
            e_input.setRight(false);
            e_input.setUp(false);
            e_input.setDown(false);
        }
    }

    public void startEnemy() {
        if (e_input.isRight() && isCollide()) {
            setDx(0f);
            setDy(-1f);
        } else if (e_input.isLeft() && isCollide()) {
            setDx(1f);
            setDy(0f);
        } else if (e_input.isUp() && isCollide()) {
            setDx(0f);
            setDy(1f);
        } else if (e_input.isDown() && isCollide()) {
            setDx(-1f);
            setDy(0f);
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
        if (e_input.isRight()) {
            Image i = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2
                    , Sprite.kondoria_right3, _animate, 20).getFxImage();
            setImg(i);
        }
        if (e_input.isLeft()) {
            Image i = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2
                    , Sprite.kondoria_left3, _animate, 20).getFxImage();
            setImg(i);
        }
        if (e_input.isUp()) {
            Image i = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2
                    , Sprite.kondoria_right3, _animate, 20).getFxImage();
            setImg(i);
        }
        if (e_input.isDown()) {
            Image i = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2
                    , Sprite.kondoria_left3, _animate, 20).getFxImage();
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
                handleSound();
                return;
            }
            setImg(Sprite.kondoria_dead.getFxImage());
            return;
        }
        startEnemy();
        move();
        animate();
        mapCheck();
        updateE_IMG();
    }

    public void handleSound(){
        try {
            sound.enemydieSound();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("enemydie sound failed!");
        }
    }
}

