package uet.oop.bomberman.entities.Bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Bomb extends AnimatedEntity {

    protected double _timeToExplode = 60; //don't know how to calculate =))
    public int _timeAfter = 20; //time to explosions disapear
    protected boolean _exploded = false;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void setImg(Image img) {
        super.setImg(img);
    }

    @Override
    public void update() {
        if(_timeToExplode > 0)
            _timeToExplode--;
        else {
            if(!_exploded)
                explosion();

            if(_timeAfter > 0)
                _timeAfter--;
            else
                remove();
        }

        animate();
        setImg(Sprite.movingSprite(Sprite.bomb , Sprite.bomb_1 , Sprite.bomb_2 , _animate , 60).getFxImage());
    }

    private void explosion() {
        _exploded = true;
    }
}
