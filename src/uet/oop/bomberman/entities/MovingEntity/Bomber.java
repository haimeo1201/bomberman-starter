package uet.oop.bomberman.entities.MovingEntity;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.game.BombermanGame;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.game.Input;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Bomber extends MovingEntity {

    protected float maxSpeed = 4f;
    protected float acceleration = 1f;
    protected float deAcceleration = 0.5f;

    protected Input p_input = new Input();

    protected List<Bomb> bombList;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        move();
        mapCheck();
        doP_IMG();
        updateP_IMG();
    }


    public void input(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.SPACE)) {
                p_input.setPlant(true);
                placeBomb();
            } else {
                p_input.setPlant(false);
                p_input.setUp(event.getCode().equals(KeyCode.UP));
                p_input.setDown(event.getCode().equals(KeyCode.DOWN));
                p_input.setLeft(event.getCode().equals(KeyCode.LEFT));
                p_input.setRight(event.getCode().equals(KeyCode.RIGHT));
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.UP)) {
                p_input.setUp(false);
            }
            if (event.getCode().equals(KeyCode.DOWN)) {
                p_input.setDown(false);
            }
            if (event.getCode().equals(KeyCode.LEFT)) {
                p_input.setLeft(false);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                p_input.setRight(false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                p_input.setPlant(false);
            }
        });
    }

    public void move() {
        if (p_input.isUp()) {
            dy -= acceleration;
            if (dy < -maxSpeed) {
                dy = -maxSpeed;
            }
        } else {
            if (dy < 0) {
                dy += deAcceleration;
                if (dy > 0) {
                    dy = 0;
                }
            }
        }
        if (p_input.isDown()) {
            dy += acceleration;
            if (dy > maxSpeed) {
                dy = maxSpeed;
            }
        } else {
            if (dy > 0) {
                dy -= deAcceleration;
                if (dy < 0) {
                    dy = 0;
                }
            }
        }
        if (p_input.isLeft()) {
            dx -= acceleration;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else {
            if (dx < 0) {
                dx += deAcceleration;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }
        if (p_input.isRight()) {
            dx += acceleration;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= deAcceleration;
                if (dx < 0) {
                    dx = 0;
                }
            }
        }
    }

    public void doP_IMG() {
        currFrame++;
        if (currFrame > maxFrame) {
            currFrame = 0;
        }
    }

    public void updateP_IMG() {
        if (p_input.isUp()) {
            setImg(Sprite.p_up[currFrame].getFxImage());
        }
        if (p_input.isDown()) {
            setImg(Sprite.p_down[currFrame].getFxImage());
        }
        if (p_input.isLeft()) {
            setImg(Sprite.p_left[currFrame].getFxImage());
        }
        if (p_input.isRight()) {
            setImg(Sprite.p_right[currFrame].getFxImage());
        }
    }


    public void placeBomb() {
        Bomb bom = new Bomb(Math.round(x / tileSize), Math.round(y / tileSize)
                , Sprite.bomb.getFxImage());
        bom.update();
        BombermanGame.entities.add(bom);
    }
}
