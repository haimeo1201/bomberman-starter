package uet.oop.bomberman.entities.MapEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends AnimatedEntity {
    int animate = 0;
    public int _timeAfter = 20; //time for explosions to disappear
    int MAX_ANIMATE = 5000;
    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if(destroyed){
            if (_timeAfter > 0)
                _timeAfter--;
            else {
                remove();
            }
            animate();
            setImg(Sprite.movingSprite(Sprite.brick_dead[0],Sprite.brick_dead[1],Sprite.brick_dead[2],animate,90).getFxImage());
        }
    }

    public void animate() {
        if (animate < MAX_ANIMATE) animate++;
        else animate = 0; //reset animation
    }
}
