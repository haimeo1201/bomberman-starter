package uet.oop.bomberman.map;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MapEntity.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.game.BombermanGame;
import uet.oop.bomberman.item.Item;
import uet.oop.bomberman.item.SpeedItem;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TileMap {
    private int[][] map;

    private int level;
    private int mapWidth;
    private int mapHeight;

    private final List<Entity> mapObjects = new ArrayList<>();

    public TileMap(String mapName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(mapName));

            level = Integer.parseInt(br.readLine());
            mapHeight = Integer.parseInt(br.readLine());
            mapWidth = Integer.parseInt(br.readLine());
            map = new int[mapHeight][mapWidth];

            String splitter = " ";
            for(int r = 0 ; r < mapHeight ; r++) {
                String line = br.readLine();
                String[] tokens = line.split(splitter);
                for(int c = 0 ; c < mapWidth ; c++) {
                    map[r][c] = Integer.parseInt(tokens[c]);
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to get map file");
        }
    }

    public void render(GraphicsContext gc) {

    }

    public void update(int x,int y, int val) {
        map[x][y] = val;
    }

    public int getLevel() {
        return level;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int[][] getMap() {
        return map;
    }

    public void drawMap() {
        int[][] mapArr = getMap();
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                Entity object ;
                int ij = mapArr[i][j];
                if (ij == 1) {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                } else if (ij == 2) {
                    AnimatedEntity object1 = new Brick(j, i, Sprite.brick.getFxImage());
                    BombermanGame.destroyableObjects.add(object1);
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                } else if(ij == 3) {
                    object = new Portal(j, i, Sprite.portal.getFxImage());
                } else if(ij == 4) {
//                    Item speed = new SpeedItem(j, i, Sprite.powerup_flames.getFxImage());
//                    BombermanGame.items.add(speed);
                    AnimatedEntity flame = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                    BombermanGame.destroyableObjects.add(flame);
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                }
                else if(ij == 9){
                    object = new Bomb(j, i, Sprite.bomb.getFxImage());
                }
                else {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                }
                BombermanGame.stillObjects.add(object);
            }
        }
    }

}
