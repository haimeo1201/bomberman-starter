package uet.oop.bomberman.entities.MapEntity.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.game.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item {

    public FlameItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (checkBoundBomber()) {
            this.remove();
            BombermanGame.bomberman.setLarge_explosion(true);
            BombermanGame.getMap1().update((Math.round((y)/ Sprite.SCALED_SIZE)) , Math.round((x)/Sprite.SCALED_SIZE), 0 );
        }
    }
}
