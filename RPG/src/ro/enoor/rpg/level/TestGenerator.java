package ro.enoor.rpg.level;

import java.util.Random;

import ro.enoor.rpg.level.tile.Tile;


public class TestGenerator {
	public static void generate(int[][] map, int width, int height) {
		Random rand = new Random();
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				if(y == 0 || y == height - 1) map[y][x] = Tile.WALL.getId();
				else if(x == 0 || x == width - 1) map[y][x] = Tile.WALL.getId();
				else if(rand.nextBoolean() && rand.nextBoolean() && rand.nextBoolean()) map[y][x] = Tile.WALL.getId();
				else map[y][x] = Tile.GRASS.getId();
			}
	}
}
