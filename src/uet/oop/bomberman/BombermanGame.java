package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.TileMap;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static TileMap map1 = new TileMap("res/levels/map1.txt");

    public static final int WIDTH = map1.getMapWidth();
    public static final int HEIGHT = map1.getMapHeight();

    private GraphicsContext gc;
    private Canvas canvas;
    public static final List<Entity> entities = new ArrayList<>();
    public static final List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());

        bomberman.input(scene);
        AnimationTimer timer = new AnimationTimer() {
            long lastTime = System.nanoTime();
            final double ns = 1000000000.0 / 25.0;
            double delta = 0;
            int updates = 0;
            @Override
            public void handle(long now) {
                delta += (now - lastTime) / ns;
                lastTime = now;
                while (delta >= 1) {
                    render();
                    update();
                    updates++;
                    delta--;
                }
            }
        };
        timer.start();
        createMap();

        entities.add(bomberman);
    }

    public void createMap() {
        int mapArr[][] = map1.getMap();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object ;
                int ij = mapArr[i][j];
                if (ij == 1) {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                } else if (ij == 2) {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                } else if(ij == 3) {
                    object = new Brick(j, i, Sprite.portal.getFxImage());
                } else if(ij == 4) {
                    object = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                }
                else {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public static TileMap getMap1() {
        return map1;
    }

    public List<Entity> getStillObjects() {
        return stillObjects;
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
