package uet.oop.bomberman.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity.Bomber;
import uet.oop.bomberman.entities.MovingEntity.Enemy.Balloom;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.map.TileMap;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final double FPS = 30.0;

    //OBJ LIST
    public static final List<Entity> movableEntities = new ArrayList<>();
    public static final List<Entity> stillObjects = new ArrayList<>();
    public static final List<Entity> destroyableObjects = new ArrayList<>();
    ///** ADD CHOOSE MAP IN GUI **\\\
    public static TileMap map1 = new TileMap("res/levels/map1.txt");
    public static final int WIDTH = map1.getMapWidth();
    public static final int HEIGHT = map1.getMapHeight();
    private GraphicsContext gc;
    private Canvas canvas;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public static TileMap getMap1() {
        return map1;
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

        //BOMBER
        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        bomberman.input(scene);

        //ENEMY
        Balloom enemy = new Balloom(13, 1, Sprite.balloom_left1.getFxImage());

        //TIMER
        AnimationTimer timer = new AnimationTimer() {
            final double ns = 1000000000.0 / FPS;
            long lastTime = System.nanoTime();
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

        map1.drawMap(stillObjects);
        movableEntities.add(bomberman);
        movableEntities.add(enemy);
    }

    public void update() {
        destroyableObjects.forEach(Entity::update);
        for (int i = 0; i < destroyableObjects.size(); i++) {
            Entity a = destroyableObjects.get(i);
            if (a.isRemoved()) destroyableObjects.remove(i);
        }
        movableEntities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        destroyableObjects.forEach(g -> g.render(gc));
        movableEntities.forEach(g -> g.render(gc));
    }
}
