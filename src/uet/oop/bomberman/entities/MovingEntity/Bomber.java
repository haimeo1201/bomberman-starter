package uet.oop.bomberman.entities.MovingEntity;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.game.BombermanGame;
import uet.oop.bomberman.game.Input;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.TileMap;
import uet.oop.bomberman.sound.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bomber extends MovingEntity {
    public int _timeAfter = 20; //time for explosions to disappear

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    protected float maxSpeed = 3f;
    protected float acceleration = 0.8f;
    protected float deAcceleration = 0.4f;
    public final int maxFrame = 2;
    public boolean isMoving = false;
    private int s_timing = 0;

    public void setBoomupnum(boolean boomupnum) {
        this.boomupnum = boomupnum;
    }

    public void setBoomleft(int boomleft) {
        this.boomleft = boomleft;
    }

    public boolean boomupnum = false;
    public int boomleft = 2;

    List<Bomb> bomb = new ArrayList();

    protected Input p_input = new Input();

    public Bomber(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        for(int i=1;i< BombermanGame.movableEntities.size();i++){
            AnimatedEntity e = BombermanGame.movableEntities.get(i);
            if(Math.round(x/Sprite.SCALED_SIZE)*Sprite.SCALED_SIZE == Math.round(e.getX()/Sprite.SCALED_SIZE)*Sprite.SCALED_SIZE &&
                    Math.round(y/Sprite.SCALED_SIZE)*Sprite.SCALED_SIZE == Math.round(e.getY()/Sprite.SCALED_SIZE)*Sprite.SCALED_SIZE ){
                killed();
            }
        }
        for (int i = 0; i < bomb.size(); i++) {
            Bomb a = bomb.get(i);
            if (a.isRemoved()) bomb.remove(i);
        }
        if (!alive) {
            doP_IMG();
            updateP_IMG();
            return;
        }
        move();
        handleSound();
        mapCheck();
        checkitem();
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
                isMoving = true;
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
            isMoving = false;
        });
    }

    public void handleSound() {
        if (s_timing == 0) {
            if (isMoving) {
                Sound footSound = new Sound();
                try {
                    footSound.footSound();
                } catch (Exception e) {
                    System.out.println("Failed to initialize foot sound!");
                    e.printStackTrace();
                }
                s_timing = 10;
            }
        } else {
            s_timing--;
        }
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
        if (!alive) {
            if (_timeAfter > 0)
                _timeAfter--;
            else {
                remove();
                bomberdieSound();
                return;
            }
            setImg(Sprite.dead[currFrame].getFxImage());
        }
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
        if(bomb.size() == boomleft) return;
        Bomb bom = new Bomb(Math.round(x / tileSize), Math.round(y / tileSize)
                , Sprite.bomb.getFxImage());
        bom.handleSound();
        bomb.add(bom);
        BombermanGame.destroyableObjects.add(bom);
    }

    public void checkitem() {
        for (AnimatedEntity e : BombermanGame.destroyableObjects) {
            if (x + 48 == e.getX() && y == e.getY() && e.getClass().getName().contains("Brick")) {
                System.out.println(1);
                e.blown();
                return;
            }
        }
    }
    public void bomberdieSound(){
        Sound sound = new Sound();
        try {
            sound.bomberdieSound();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("bomberdie sound failed!");
        }
    }
}


