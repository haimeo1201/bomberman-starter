package uet.oop.bomberman.entities.MovingEntity;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.game.BombermanGame;
import uet.oop.bomberman.game.Input;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends MovingEntity {
    public int _timeAfter = 20; //time for explosions to disappear

    protected float maxSpeed = 3.5f;
    protected float acceleration = 2f;
    protected float deAcceleration = 0.6f;
    public boolean win = false;
    public final int maxFrame = 2;
    public boolean isMoving = false;
    private int s_timing = 0;
    private boolean large_explosion = false;

    public void setLarge_explosion(boolean large_explosion) {
        this.large_explosion = large_explosion;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setDeAcceleration(float deAcceleration) {
        this.deAcceleration = deAcceleration;
    }

    public void setBoomupnum(boolean boomupnum) {
        this.boomupnum = boomupnum;
    }

    public void setBoomleft(int boomleft) {
        this.boomleft = boomleft;
    }

    public boolean boomupnum = false;
    public int boomleft = 1;

    List<Bomb> bomb = new ArrayList();

    protected Input p_input = new Input();

    public Bomber(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void setWin(boolean win) {
        this.win = win;
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
        checkItem();
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
                s_timing = 7;
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
        if(win){
            remove();
            bomberWinSound();
            BombermanGame.setIsRunning(2);
            return;
        }
        if (!alive) {
            if (_timeAfter > 0)
                _timeAfter--;
            else {
                remove();
                bomberDieSound();
                BombermanGame.setIsRunning(1);
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
        if(large_explosion) {
            Bomb bom = new Bomb(Math.round(x / tileSize), Math.round(y / tileSize), Sprite.bomb.getFxImage(), true);
            bom.handleSound();
            bomb.add(bom);
            //BombermanGame.getMap1().update(Math.round(x / tileSize) , Math.round(y / tileSize) , 9);
            BombermanGame.destroyableObjects.add(bom);
        }
        else{
            Bomb bom = new Bomb(Math.round(x / tileSize), Math.round(y / tileSize), Sprite.bomb.getFxImage(), false);
            bom.handleSound();
            bomb.add(bom);
            BombermanGame.destroyableObjects.add(bom);
            //BombermanGame.getMap1().update(Math.round(y / tileSize), Math.round(x / tileSize), 9);
        }
    }

    public void checkItem() {
        for (AnimatedEntity e : BombermanGame.destroyableObjects) {
            if (x + Sprite.SCALED_SIZE == e.getX() && y == e.getY() && e.getClass().getName().contains("Brick")) {
                e.blown();
                return;
            }
        }
    }
    public void bomberDieSound(){
        Sound sound = new Sound();
        try {
            sound.bomberDieSound();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("die sound failed!");
        }
    }
    public void bomberWinSound(){
        Sound sound = new Sound();
        try {
            sound.bomberWinSound();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("die sound failed!");
        }
    }
}


