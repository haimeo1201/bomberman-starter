package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {

    protected Sprite sprite;

    protected static boolean up;
    protected static boolean down;
    protected static boolean left;
    protected static boolean right;

    protected int currFrameUp;
    protected int currFrameDown;
    protected int currFrameLeft;
    protected int currFrameRight;
    public final int maxFrame = 3;

    protected float dx = 0;
    protected float dy = 0;

    protected float maxSpeed = 3.5f;
    protected float acceleration = 1.5f;
    protected float deAcceleration = 0.4f;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        x += dx;
        y += dy;
        move();
        doP_IMG();
        updateP_IMG();
    }

    public void input(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.UP)) {
                    up = true;
                } else {
                    up = false;
                }
                if(event.getCode().equals(KeyCode.DOWN)) {
                    down = true;
                } else {
                    down = false;
                }
                if(event.getCode().equals(KeyCode.LEFT)) {
                    left = true;
                } else {
                    left = false;
                }
                if(event.getCode().equals(KeyCode.RIGHT)) {
                    right = true;
                } else {
                    right = false;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.UP)) {
                    up = false;
                }
                if(event.getCode().equals(KeyCode.DOWN)) {
                    down = false;
                }
                if(event.getCode().equals(KeyCode.LEFT)) {
                    left = false;
                }
                if(event.getCode().equals(KeyCode.RIGHT)) {
                    right = false;
                }
            }
        });
    }

    public void move() {
        if(up) {
            dy -= acceleration;
            if(dy < -maxSpeed) {
                dy = -maxSpeed;
            }
        } else {
            if(dy < 0) {
                dy += deAcceleration;
                if(dy > 0) {
                    dy = 0;
                }
            }
        }
        if(down) {
            dy += acceleration;
            if(dy > maxSpeed) {
                dy = maxSpeed;
            }
        } else {
            if(dy > 0) {
                dy -= deAcceleration;
                if(dy < 0) {
                    dy = 0;
                }
            }
        }
        if(left) {
            dx -= acceleration;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else {
            if(dx < 0) {
                dx += deAcceleration;
                if(dx > 0) {
                    dx = 0;
                }
            }
        }
        if(right) {
            dx += acceleration;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if(dx > 0) {
                dx -= deAcceleration;
                if(dx < 0) {
                    dx = 0;
                }
            }
        }
    }

    public void doP_IMG() {
        if(up) {
            currFrameUp++;
            if(currFrameUp > maxFrame) {
                currFrameUp = 0;
            }
        }
        if(down) {
            currFrameDown++;
            if(currFrameDown > maxFrame) {
                currFrameDown = 0;
            }
        }
        if(left) {
            currFrameLeft++;
            if(currFrameLeft > maxFrame) {
                currFrameLeft = 0;
            }
        }
        if(right) {
            currFrameRight++;
            if(currFrameRight > maxFrame) {
                currFrameRight = 0;
            }
        }
    }

    public void updateP_IMG() {
        if(up) {
            setImg(Sprite.p_up[currFrameUp].getFxImage());
        }
        if(down) {
            setImg(Sprite.p_down[currFrameDown].getFxImage());
        }
        if(left) {
            setImg(Sprite.p_left[currFrameLeft].getFxImage());
        }
        if(right) {
            setImg(Sprite.p_right[currFrameRight].getFxImage());
        }
    }
}
