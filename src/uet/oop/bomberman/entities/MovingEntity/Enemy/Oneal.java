package uet.oop.bomberman.entities.MovingEntity.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.game.Input;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Oneal extends MovingEntity {

    protected Input e_input = new Input();

    protected float maxSpeed = 2f;
    protected float acceleration = 0.4f;
    public int _timeAfter = 20;

    int state = 1;


    public Oneal(int xUnit, int yUnit, Image img) {
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
        switchState();
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

    public void switchState() {
        if(isCollide()) {
            int maximum = 2;
            int minimum = 1;
            Random rn = new Random();
            int n = maximum - minimum + 1;
            int i = rn.nextInt() % n;
            int randomNum =  minimum + i;
            state = randomNum;
        }

        if (state == 1) {
            setAcceleration(0.8f);
            setMaxSpeed(4f);
        } else {
            setAcceleration(0.4f);
            setMaxSpeed(2f);
        }
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void updateE_IMG() {
        if (e_input.isRight()) {
            Image i = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2
                    , Sprite.oneal_right3, _animate, 20).getFxImage();
            setImg(i);
        }
        if (e_input.isLeft()) {
            Image i = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2
                    , Sprite.oneal_left3, _animate, 20).getFxImage();
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
            setImg(Sprite.oneal_dead.getFxImage());
            return;
        }
        startEnemy();
        move();
        animate();
        mapCheck();
        updateE_IMG();
    }

}
