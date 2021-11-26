package uet.oop.bomberman.game;

public class Input {

    protected boolean up;
    protected boolean down;
    protected boolean left;
    protected boolean right;
    protected boolean plant;

    public Input() {
        up = false;
        down = false;
        left = false;
        right = false;
        plant = false;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isPlant() {
        return plant;
    }

    public void setPlant(boolean plant) {
        this.plant = plant;
    }
}
