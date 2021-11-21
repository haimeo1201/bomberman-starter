package uet.oop.bomberman.game;

public class Input {

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean plant;

    public Input() {
        up = false;
        down = false;
        left = false;
        right = false;
        plant = false;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setPlant(boolean plant) {
        this.plant = plant;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isPlant() {
        return plant;
    }
}
