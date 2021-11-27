package uet.oop.bomberman.entities.Bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.game.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.TileMap;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends AnimatedEntity {
    boolean flag = false;// push 4(possibly) new image or not
    public int _timeAfter = 20; //time for explosions to disappear
    protected double _timeToExplode = 50; //2sec in 25fps
    protected boolean _exploded = false;
    public List<Image> Img = new ArrayList<>();
    public List<String> pos = new ArrayList<>();
    public List<Sprite[]> state = new ArrayList<>();

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        Img.add(Sprite.bomb.getFxImage());
        pos.add("center");
        state.add(Sprite.bombs);
    }

    @Override
    public void setImg(Image img) {
        super.setImg(img);
    }

    @Override
    public void update() {
        if (_timeToExplode > 0)
            _timeToExplode--;
        else {
            if (!_exploded) {
                explosion();
            }
            else{
                if(!flag) updateExplosion();
            }

            if (_timeAfter > 0)
                _timeAfter--;
            else {
                remove();
            }
        }

        animate();

        for (int i = 0; i < pos.size(); i++) {
            Img.set(i, Sprite.movingSprite(state.get(i)[0], state.get(i)[1], state.get(i)[2], _animate, 90).getFxImage());
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
        }
    }

    public void shrapnel_collision(Sprite[] sprites, String Pos, int dx,int dy) {
        for(AnimatedEntity e:BombermanGame.destroyableObjects){
            if(x + dx == e.getX() && y + dy == e.getY()){
                e.blown();
                BombermanGame.getMap1().update((Math.round((y + dy)/Sprite.SCALED_SIZE)) , Math.round((x + dx)/Sprite.SCALED_SIZE), 0 );
                return;
            }
        }
        for(Entity e:BombermanGame.stillObjects){
            if(x + dx == e.getX() && y + dy == e.getY() && e.getClass().getName().contains("Wall")) return;
        }
        for(MovingEntity e:BombermanGame.movableEntities){
            if((x + dx) - e.getX() < 0.001  && (y + dy) - e.getY() < 0.001){
                e.killed();
            }
        }
        pos.add(Pos);
        state.add(sprites);
        Img.add(sprites[0].getFxImage());
    }

    private void explosion() {
        _exploded = true;
    }

    public boolean is_exploded() {
        return _exploded;
    }

    public void updateExplosion() {
        flag = true;
        state.set(0, Sprite.bombs_ex);
        Img.set(0, Sprite.bombs_ex[0].getFxImage());
        shrapnel_collision(Sprite.right_last, "right",Sprite.SCALED_SIZE,0);
        shrapnel_collision(Sprite.left_last, "left",- Sprite.SCALED_SIZE,0);
        shrapnel_collision(Sprite.top_last, "top",0, -Sprite.SCALED_SIZE);
        shrapnel_collision(Sprite.down_last, "down",0, Sprite.SCALED_SIZE);
    }

}