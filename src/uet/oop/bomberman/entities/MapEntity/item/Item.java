package uet.oop.bomberman.entities.MapEntity.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity.Bomber;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.game.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Item extends Entity {
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    protected boolean isVisible = false;

    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public abstract void update();

    protected boolean checkBoundBomber() {
        if(Math.round(x)== Math.round(BombermanGame.bomberman.getX()/Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE &&
                Math.round(y) == Math.round(BombermanGame.bomberman.getY()/Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE){
            return true;
        }
        return false;
    }

}
