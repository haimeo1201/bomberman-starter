package uet.oop.bomberman.entities.Bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity.Bomber;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.game.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends AnimatedEntity {
    boolean flag = false;
    boolean push_more;// push 4(possibly) new image or not
    public int _timeAfter = 20; //time for explosions to disappear
    protected double _timeToExplode = 50; //2sec in 25fps
    protected boolean _exploded = false;
    public List<Image> Img = new ArrayList<>();
    public List<String> pos = new ArrayList<>();
    public List<Sprite[]> state = new ArrayList<>();
    Sound bombSound = new Sound();

    public Bomb(int xUnit, int yUnit, Image img,boolean _more) {
        super(xUnit, yUnit, img);
        Img.add(Sprite.bomb.getFxImage());
        pos.add("center");
        state.add(Sprite.bombs);
        push_more = _more;
    }

    @Override
    public void setImg(Image img) {
        super.setImg(img);
    }

    @Override
    public void update() {
        if (_timeToExplode > 0) {
            _timeToExplode--;
        }
        else {
            if (!_exploded) {
                explosion();
            } else {
                if (!flag) updateExplosion();
            }

            if (_timeAfter > 0) {
                _timeAfter--;
            }
            else {
                remove();
            }
        }

        animate();

        for (int i = 0; i < pos.size(); i++) {
            Img.set(i, Sprite.movingSprite(state.get(i)[0], state.get(i)[1], state.get(i)[2], _animate, 90).getFxImage());
        }
    }

    public void handleSound(){
        try {
            bombSound.bombSound();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Bomb sound failed!");
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < pos.size(); i++) {
            if (pos.get(i).equals("center")) {
                gc.drawImage(Img.get(i), x, y);
            }
            if (pos.get(i).equals("right")) {
                gc.drawImage(Img.get(i), x + Sprite.SCALED_SIZE, y);
            }
            if (pos.get(i).equals("top")) {
                gc.drawImage(Img.get(i), x, y - Sprite.SCALED_SIZE);
            }
            if (pos.get(i).equals("down")) {
                gc.drawImage(Img.get(i), x, y + Sprite.SCALED_SIZE);
            }
            if (pos.get(i).equals("left")) {
                gc.drawImage(Img.get(i), x - Sprite.SCALED_SIZE, y);
            }
            if (pos.get(i).equals("leftlast")) {
                gc.drawImage(Img.get(i), x - 2*Sprite.SCALED_SIZE, y);
            }
            if (pos.get(i).equals("rightlast")) {
                gc.drawImage(Img.get(i), x + 2*Sprite.SCALED_SIZE, y);
            }
            if (pos.get(i).equals("toplast")) {
                gc.drawImage(Img.get(i), x , y - 2*Sprite.SCALED_SIZE);
            }
            if (pos.get(i).equals("downlast")) {
                gc.drawImage(Img.get(i), x , y + 2*Sprite.SCALED_SIZE);
            }
        }
    }

    public boolean shrapnel_collision(Sprite[] sprites, String Pos, int dx, int dy) {
        for (AnimatedEntity e : BombermanGame.destroyableObjects) {
            if (x + dx == e.getX() && y + dy == e.getY()) {
                e.blown();
                BombermanGame.getMap1().update((Math.round((y + dy) / Sprite.SCALED_SIZE)), Math.round((x + dx) / Sprite.SCALED_SIZE), 0);
                return false;
            }
        }
        for (Entity e : BombermanGame.stillObjects) {
            if (x + dx == e.getX() && y + dy == e.getY() && e.getClass().getName().contains("Wall")) return false;
        }
        for (MovingEntity e : BombermanGame.movableEntities) {
            if (Math.round(x + dx) == Math.round(e.getX() / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE
                    && Math.round(y + dy) == Math.round(e.getY() / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE) {
                e.killed();
            }
            if (Math.round(x) == Math.round(e.getX() / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE
                    && Math.round(y) == Math.round(e.getY() / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE) {
                e.killed();
            }
        }

        pos.add(Pos);
        state.add(sprites);
        Img.add(sprites[0].getFxImage());
        return true;
    }

    private void explosion() {
        _exploded = true;
    }

    public boolean is_exploded() {
        return _exploded;
    }

    public void updateExplosion() {
        BombermanGame.getMap1().update(Math.round(y / Sprite.SCALED_SIZE) , Math.round(x / Sprite.SCALED_SIZE) , 0);
        flag = true;
        state.set(0, Sprite.bombs_ex);
        Img.set(0, Sprite.bombs_ex[0].getFxImage());
        if(!push_more) {
            shrapnel_collision(Sprite.right_last, "right", Sprite.SCALED_SIZE, 0);
            shrapnel_collision(Sprite.left_last, "left", -Sprite.SCALED_SIZE, 0);
            shrapnel_collision(Sprite.top_last, "top", 0, -Sprite.SCALED_SIZE);
            shrapnel_collision(Sprite.down_last, "down", 0, Sprite.SCALED_SIZE);
            return;
        }
        if(shrapnel_collision(Sprite.ex_horizontal,"right", Sprite.SCALED_SIZE, 0)){
            shrapnel_collision(Sprite.right_last,"rightlast",2*Sprite.SCALED_SIZE,0);
        }
        if(shrapnel_collision(Sprite.ex_horizontal,"left", -Sprite.SCALED_SIZE, 0)){
            shrapnel_collision(Sprite.left_last,"leftlast",-2*Sprite.SCALED_SIZE,0);
        }
        if(shrapnel_collision(Sprite.ex_vertical,"top", 0, -Sprite.SCALED_SIZE)){
            shrapnel_collision(Sprite.top_last,"toplast",0,-2*Sprite.SCALED_SIZE);
        }
        if(shrapnel_collision(Sprite.ex_vertical,"down", 0, Sprite.SCALED_SIZE)){
            shrapnel_collision(Sprite.down_last,"downlast",0,2*Sprite.SCALED_SIZE);
        }
    }

}