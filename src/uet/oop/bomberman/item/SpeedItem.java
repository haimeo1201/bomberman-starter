package uet.oop.bomberman.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MovingEntity.Bomber;
import uet.oop.bomberman.game.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item{
    public SpeedItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
//        if (checkBoundBomber()) {
            this.isRemoved = false;
            BombermanGame.bomberman.setMaxSpeed(4f);
            BombermanGame.getMap1().update((Math.round((y)/ Sprite.SCALED_SIZE)) , Math.round((x)/Sprite.SCALED_SIZE), 0 );
//        }
    }
}
