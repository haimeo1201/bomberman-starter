package uet.oop.bomberman.map;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TileMap {
    private int x;
    private int y;

    private int tileSize;
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

    public void update() {

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

/*    public void draw() {
        for (int r = 0 ; r < mapHeight ; r++) {
            for (int c = 0 ; c < mapWidth ; c++) {
                int rc = map[r][c];
                Entity object;
                if(rc == 0) {
                    object = new Wall(c, r, Sprite.wall.getFxImage());
                }
                else if(rc == 1) {
                    object = new Grass(c, r, Sprite.grass.getFxImage());
                }
                else if(rc == 2) {
                    object = new Brick(c , r , Sprite.brick.getFxImage());
                }
                else {
                    object = new Grass(c, r, Sprite.grass.getFxImage());
                }

                mapObjects.add(object);
            }
        }
    }*/

}
