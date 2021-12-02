package uet.oop.bomberman.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.game.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item{

    public FlameItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (checkBoundBomber()) {
            this.isRemoved = true;
            BombermanGame.bomberman.setBoomleft(2);
            BombermanGame.bomberman.setBoomupnum(true);
            BombermanGame.getMap1().update((Math.round((y)/ Sprite.SCALED_SIZE)) , Math.round((x)/Sprite.SCALED_SIZE), 0 );
        }
    }
}
