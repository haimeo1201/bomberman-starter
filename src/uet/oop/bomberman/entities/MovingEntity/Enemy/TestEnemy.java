package uet.oop.bomberman.entities.MovingEntity.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.game.Input;
import uet.oop.bomberman.graphics.Sprite;

public class TestEnemy extends MovingEntity {

    Input e_input = new Input();
    public final int tileSize = Sprite.SCALED_SIZE;
    public final int maxFrame = 3;
    protected int currFrame;

    protected float maxSpeed = 4f;
    protected float acceleration = 1f;
    protected float deAcceleration = 0.5f;

    public TestEnemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }


    @Override
    public void update() {

    }

}
