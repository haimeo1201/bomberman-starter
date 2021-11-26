package uet.oop.bomberman.entities.Bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;

public class Bomb extends AnimatedEntity {

    public int _timeAfter = 50; //time for explosions to disappear
    protected double _timeToExplode = 50; //2sec in 25fps
    protected boolean _exploded = false;
    public List<Image> Img = new ArrayList<>();
    public List<String> pos = new ArrayList<>();
    public List<Sprite[] > state = new ArrayList<>();

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
            if (!_exploded)
                explosion();
            else updateExplosion();

            if (_timeAfter > 0)
                _timeAfter--;
            else
                remove();
        }

        animate();

        for(int i = 0;i < pos.size();i++){
            Img.set(i, Sprite.movingSprite(state.get(i)[0], state.get(i)[1], state.get(i)[2], _animate, 90).getFxImage());
        }

    }
    @Override
    public void render(GraphicsContext gc){
        for(int i = 0;i < pos.size();i++){
            if(pos.get(i).equals("center")){
                gc.drawImage(Img.get(i),x,y);
            }
            if(pos.get(i).equals("right")){
                gc.drawImage(Img.get(i),x+32,y);
            }
            if(pos.get(i).equals("top")){
                gc.drawImage(Img.get(i),x,y-32);
            }
            if(pos.get(i).equals("down")){
                gc.drawImage(Img.get(i),x,y+32);
            }
            if(pos.get(i).equals("left")){
                gc.drawImage(Img.get(i),x-32,y);
            }
        }
    }

    public void push(Sprite[] sprites, String Pos){
        pos.add(Pos);
        state.add(sprites);
        Img.add(sprites[0].getFxImage());

        //setImg(Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 90).getFxImage());
    }

    private void explosion() {
        _exploded = true;
    }

    public void updateExplosion() {
        state.set(0,Sprite.bombs_ex);
        Img.set(0,Sprite.bombs_ex[0].getFxImage());
        push(Sprite.right_last, "right");
        push(Sprite.left_last, "left");
        push(Sprite.top_last, "top");
        push(Sprite.down_last, "down");


    }


}