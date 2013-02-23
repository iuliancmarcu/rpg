package ro.enoor.rpg.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ro.enoor.rpg.entity.Entity;
import ro.enoor.rpg.level.tile.Tile;
import ro.enoor.rpg.world.World;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Level {
	public int[][] map;
	private World world;
	private int width;
	private int height;
	private ArrayList<Entity> objects;
	
	public Level(World world, int width, int height) {
		this.world = world;
		this.width = width + 2;
		this.height = height + 2;
		objects = new ArrayList<Entity>();

		map = new int[height + 2][width + 2];
		generate();
	}
	
	public void initLevel() {
		objects.add(world.getPlayer());
	}

	private void generate() {
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				if(y == 0 || y == height - 1) map[y][x] = Tile.WALL.getId();
				else if(x == 0 || x == width - 1) map[y][x] = Tile.WALL.getId();
				else if(y % 2 == 0 && (x * x) % 6 == 1) map[y][x] = Tile.WALL.getId();
				else map[y][x] = Tile.GRASS.getId();;
			}
	}
	
	public void renderLevel(SpriteBatch batch) {
		renderBackground(batch);
		renderObjects(batch);
	}

	private void renderObjects(SpriteBatch batch) {
		Collections.sort(objects, new Comparator<Entity>() {
		    public int compare(Entity a, Entity b) {
		        return Integer.signum((int) (fixPosition(a) - fixPosition(b)));
		    }
		    private float fixPosition(Entity in) {
		        return in.getPosition().y;
		    }
		});
		
		for(Entity ent : objects)
			ent.draw(batch);
	}

	private void renderBackground(SpriteBatch batch) {
		for (int y = height - 1; y >= 0; y--)
			for (int x = 0; x < width; x++)
				Tile.getTileById(map[y][x]).draw(batch, x, y);
	}
	
	public void renderTilesHitBox(ShapeRenderer shapeRenderer) {
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				if (Tile.isSolid(map[y][x])) 
					Tile.getTileById(map[y][x]).drawHitBox(shapeRenderer, x, y);
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
}
