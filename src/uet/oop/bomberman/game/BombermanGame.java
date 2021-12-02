package uet.oop.bomberman.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity.Bomber;
import uet.oop.bomberman.entities.MovingEntity.Enemy.*;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.item.Item;
import uet.oop.bomberman.map.TileMap;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final double FPS = 24;

    //OBJ LIST
    public static final List<MovingEntity> movableEntities = new ArrayList<>();
    public static final List<Entity> stillObjects = new ArrayList<>();
    public static final List<AnimatedEntity> destroyableObjects = new ArrayList<>();
    ///** ADD CHOOSE MAP IN GUI **\\\
    public static TileMap map1 = new TileMap("res/levels/map1.txt");
    public static final int WIDTH = map1.getMapWidth();
    public static final int HEIGHT = map1.getMapHeight();
    private GraphicsContext gc;
    private Canvas canvas;
    public static final Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
    public static final List<Item> items = new ArrayList<>();

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
        bomberman.input(scene);

        //ENEMY
        Balloom balloom1 = new Balloom(13, 1, Sprite.balloom_left1.getFxImage());
        Balloom balloom2 = new Balloom(18, 3, Sprite.balloom_left1.getFxImage());
        //Balloom balloom3 = new Balloom(24, 5, Sprite.balloom_left1.getFxImage());

        //Oneal oneal1 = new Oneal(17 , 1 , Sprite.oneal_left1.getFxImage());
        Oneal oneal2 = new Oneal(24 , 3 , Sprite.oneal_left1.getFxImage());
        //Oneal oneal3 = new Oneal(26 , 11 , Sprite.oneal_left1.getFxImage());
        Oneal oneal4 = new Oneal(11 , 7 , Sprite.oneal_left1.getFxImage());

        Doll doll1 = new Doll(27, 5, Sprite.doll_left1.getFxImage());

        Minvo minvo1 = new Minvo(17 , 1 , Sprite.minvo_left1.getFxImage());
        Minvo minvo2 = new Minvo(26 , 11 , Sprite.minvo_left1.getFxImage());

        Kondoria kondoria1 = new Kondoria(5 , 11 , Sprite.minvo_left1.getFxImage());

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

        map1.drawMap();
        movableEntities.add(bomberman);
        movableEntities.add(balloom1);
        movableEntities.add(balloom2);
        //movableEntities.add(balloom3);

        //movableEntities.add(oneal1);
        movableEntities.add(oneal2);
        //movableEntities.add(oneal3);
        movableEntities.add(oneal4);

        movableEntities.add(doll1);

        movableEntities.add(minvo1);
        movableEntities.add(minvo2);

        movableEntities.add(kondoria1);
    }

    public void update() {
        destroyableObjects.forEach(Entity::update);
        for (int i = 0; i < destroyableObjects.size(); i++) {
            Entity a = destroyableObjects.get(i);
            if (a.isRemoved()) destroyableObjects.remove(i);
        }
        movableEntities.forEach(Entity::update);
        for (int i = 0; i < movableEntities.size(); i++) {
            Entity a = movableEntities.get(i);
            if (a.isRemoved()) movableEntities.remove(i);
        }
        items.forEach(Entity::update);
        for (int i = 0; i < items.size(); i++) {
            Entity a = items.get(i);
            if (a.isRemoved()) items.remove(i);
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        destroyableObjects.forEach(g -> g.render(gc));
        movableEntities.forEach(g -> g.render(gc));
        items.forEach(g -> g.render(gc));
    }
}
