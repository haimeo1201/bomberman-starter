package uet.oop.bomberman.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity.Bomber;
import uet.oop.bomberman.entities.MovingEntity.Enemy.*;
import uet.oop.bomberman.entities.MovingEntity.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.MapEntity.item.Item;
import uet.oop.bomberman.map.TileMap;
import uet.oop.bomberman.sound.Sound;

import javax.security.auth.login.AccountNotFoundException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public static final Sound bkgSound = new Sound();

    public static int isRunning = 0;

    public static void setIsRunning(int isRunning) {
        BombermanGame.isRunning = isRunning;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static TileMap getMap1() {
        return map1;
    }

    @Override
    public void start(Stage stage) throws Exception {
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

        //bkgSound.backgroundSound();

        //BOMBER
        bomberman.input(scene);

        //ENEMY
        Balloom balloom1 = new Balloom(13, 1, Sprite.balloom_left1.getFxImage());
        Balloom balloom2 = new Balloom(18, 3, Sprite.balloom_left1.getFxImage());
        Minvo minvo = new Minvo(15,7,Sprite.minvo_left1.getFxImage());

        Doll doll = new Doll(25, 6, Sprite.doll_left1.getFxImage());
        Oneal oneal4 = new Oneal(11, 7, Sprite.oneal_left1.getFxImage());

        Kondoria kondoria = new Kondoria(11, 8, Sprite.kondoria_right1.getFxImage());


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
                    update(stage);
                    try {
                        render(stage);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updates++;
                    delta--;
                    if(isRunning != 0) {
                        stop();
                    }
                }
            }
        };
        timer.start();

        map1.drawMap();

        movableEntities.add(bomberman);

        movableEntities.add(minvo);
        movableEntities.add(balloom1);
        movableEntities.add(balloom2);
        movableEntities.add(doll);
        movableEntities.add(oneal4);
        movableEntities.add(kondoria);
    }

public void update(Stage stage) {
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

    public void render(Stage stage) throws InterruptedException {
        if(isRunning == 0) {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            stillObjects.forEach(g -> g.render(gc));
            items.forEach(g -> g.render(gc));
            destroyableObjects.forEach(g -> g.render(gc));
            movableEntities.forEach(g -> g.render(gc));
        }
        else if (isRunning == 1){
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            stage.close();
            Stage loseStage = new Stage();
            StackPane root = new StackPane();
            Scene scene = new Scene(root, 720, 272);
            Image lose = new Image("https://scontent.fhan2-2.fna.fbcdn.net/v/t1.18169-9/422446_359366724083882_1343284803_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=e3f864&_nc_ohc=cZtuer57DfcAX-R3U5O&_nc_ht=scontent.fhan2-2.fna&oh=dfbb50d3ee9721b39ac1174d620c453e&oe=61D75DE9");
            BackgroundImage bImg = new BackgroundImage(lose,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            Background bGround = new Background(bImg);
            root.setBackground(bGround);
            loseStage.setScene(scene);
            loseStage.show();
            loseStage.centerOnScreen();
        }
        else{
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            stage.close();
            Stage winStage = new Stage();
            StackPane root = new StackPane();
            Scene scene = new Scene(root, 626, 417);
            Image win = new Image("https://img.freepik.com/free-vector/you-win-lettering-pop-art-text-banner_185004-60.jpg?size=626&ext=jpg");
            BackgroundImage bImg = new BackgroundImage(win,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            Background bGround = new Background(bImg);
            root.setBackground(bGround);
            winStage.setScene(scene);
            winStage.show();
            winStage.centerOnScreen();
        }
    }
}
