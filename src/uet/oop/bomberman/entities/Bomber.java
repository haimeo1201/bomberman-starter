package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.TileMap;

import java.util.List;

public class Bomber extends Entity {

    protected static boolean up;
    protected static boolean down;
    protected static boolean left;
    protected static boolean right;
    protected static boolean plant;

    public final int tileSize = Sprite.SCALED_SIZE;
    public final int maxFrame = 3;
    protected int currFrame;

    protected float dx = 0;
    protected float dy = 0;

    protected float maxSpeed = 4f;
    protected float acceleration = 1f;
    protected float deAcceleration = 0.5f;

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
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                up = event.getCode().equals(KeyCode.UP);

                down = event.getCode().equals(KeyCode.DOWN);

                left = event.getCode().equals(KeyCode.LEFT);

                right = event.getCode().equals(KeyCode.RIGHT);

                if(event.getCode().equals(KeyCode.SPACE)) {
                    plant = true;
                    BombermanGame.entities.add(new Bomb(Math.round(x / tileSize) , Math.round(y / tileSize)
                            ,Sprite.bomb.getFxImage()));
                } else {
                    plant = false;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.UP)) {
                    up = false;
                }
                if (event.getCode().equals(KeyCode.DOWN)) {
                    down = false;
                }
                if (event.getCode().equals(KeyCode.LEFT)) {
                    left = false;
                }
                if (event.getCode().equals(KeyCode.RIGHT)) {
                    right = false;
                }
                if (event.getCode().equals(KeyCode.SPACE)) {
                    ;
                }
            }
        });
    }

    public void move() {
        if (up) {
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
        if (down) {
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
        if (left) {
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
        if (right) {
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
        if (up) {
            setImg(Sprite.p_up[currFrame].getFxImage());
        }
        if (down) {
            setImg(Sprite.p_down[currFrame].getFxImage());
        }
        if (left) {
            setImg(Sprite.p_left[currFrame].getFxImage());
        }
        if (right) {
            setImg(Sprite.p_right[currFrame].getFxImage());
        }
    }

    public void mapCheck() {
        TileMap map = BombermanGame.getMap1();
        int[][] mapArr = BombermanGame.getMap1().getMap();
        int x1;
        int x2;

        int y1;
        int y2;

        /**
         * HORIZONTAL.
         */



        x1 = Math.round ((x + dx - 16) / tileSize);
        x2 = Math.round (x + tileSize + dx - 8) / tileSize;

        y1 = Math.round ((y + 2f) / tileSize);
        y2 = Math.round (y + tileSize - 1) / tileSize;

        if (x1 >= 0 && x2 < map.getMapWidth() && y1 >= 0 && y2 < map.getMapHeight()) {
            if (dx > 0) {
                if (mapArr[y1][x2] != 0 || mapArr[y2][x2] != 0) {
                    x = x2 * tileSize;
                    x -= tileSize - 4;
                    dx = 0;
                }
            } else if (dx < 0) {
                if (mapArr[y2][x1] != 0 || mapArr[y1][x1] != 0) {
                    x = (x1 + 1) * tileSize  ;
                    dx = 0;
                }
            }
        }

        /**
         * VERTICAL.
         */

        x1 = Math.round ((x)/ tileSize);
        x2 = Math.round (x + tileSize - 26) / tileSize;

        y1 = Math.round ((y + dy - 8)/ tileSize);
        y2 = Math.round (y + dy + tileSize - 1) / tileSize;

        if (x1 > 0 && x2 < map.getMapWidth() && y1 >= 0 && y2 < map.getMapHeight()) {
            if (dy > 0) {
                if (mapArr[y2][x1] != 0 || mapArr[y2][x2] != 0) {
                    y = y2 * tileSize;
                    y -= tileSize + 1;
                    dy = 0;
                }
            } else if (dy < 0) {
                if (mapArr[y1][x1] != 0 || mapArr[y1][x2] != 0) {
                    y = (y1 + 0.65f) * tileSize;
                    dy = 0;
                }
            }
        }
        x += dx;
        y += dy;
    }
}
