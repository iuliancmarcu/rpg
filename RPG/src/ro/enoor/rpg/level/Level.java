package ro.enoor.rpg.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import ro.enoor.rpg.entity.Entity;
import ro.enoor.rpg.entity.YSortable;
import ro.enoor.rpg.level.tile.ObjectTile;
import ro.enoor.rpg.level.tile.Tile;
import ro.enoor.rpg.world.World;
import ro.enoor.rpg.world.WorldRenderer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Level {
	private World world;
	public int[][] map;
	private int width, height;
	private ArrayList<Entity> entities;
	private ArrayList<ObjectTile> tiles;
	private ArrayList<YSortable> renderable;
	private Random random;

	public Level(World world, int width, int height) {
		this.world = world;
		this.width = width + 2;
		this.height = height + 2;
		entities = new ArrayList<Entity>();
		tiles = new ArrayList<ObjectTile>();
		renderable = new ArrayList<YSortable>();
		random = new Random();

		map = new int[height + 2][width + 2];
		generate();
	}

	public void initLevel() {
		entities.add(world.getPlayer());

		for(int y = height - 1; y >= 0; y--)
			for(int x = 0; x < width; x++)
				if(Tile.isObject(map[y][x]))
					tiles.add(new ObjectTile(Tile.getTileById(map[y][x]), x, y));
		System.out.println(tiles.size());
	}

	private void generate() {
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				if(y == 0 || y == height - 1) map[y][x] = Tile.WALL.getId();
				else if(x == 0 || x == width - 1) map[y][x] = Tile.WALL.getId();
				//else if(y > 6) map[y][x] = Tile.WALL.getId();
				else if(random.nextBoolean()) map[y][x] = 1 + random.nextInt(4);
				else map[y][x] = Tile.GRASS.getId();
			}
	}

	private boolean isOnScreen(YSortable obj) {
		OrthographicCamera camera = WorldRenderer.getCamera();
		
		if(obj.getPosition().x + 2 * Tile.TILE_SIZE > camera.position.x - camera.viewportWidth / 2 &&
				obj.getPosition().x - Tile.TILE_SIZE < camera.position.x + camera.viewportWidth / 2 &&
				obj.getPosition().y + 2 * Tile.TILE_SIZE > camera.position.y - camera.viewportHeight / 2 &&
				obj.getPosition().y - Tile.TILE_SIZE < camera.position.y + camera.viewportHeight / 2)
			return true;
		return false;
	}
	
	public void update() {
		renderable.clear();

		for(Entity e : entities)
			if(isOnScreen(e))
				renderable.add(e);
		for(ObjectTile o : tiles)
			if(isOnScreen(o))
				renderable.add(o);
	}

	public void render(SpriteBatch batch) {
		renderBackground(batch);
		renderObjects(batch);
	}

	private void renderObjects(SpriteBatch batch) {
		Collections.sort(renderable, new Comparator<YSortable>() {
			public int compare(YSortable a, YSortable b) {
				return Integer.signum((int) (fixPosition(a) - fixPosition(b)));
			}
			private float fixPosition(YSortable in) {
				return -in.getPosition().y;
			}
		});

		for(YSortable obj : renderable)
			obj.draw(batch);
	}

	private void renderBackground(SpriteBatch batch) {
		for(int y = height - 1; y >= 0; y--)
			for(int x = 0; x < width; x++)
				if (!Tile.isObject(map[y][x])) 
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
	public ArrayList<Entity> getEntities() { return entities; }
	public ArrayList<ObjectTile> getObjectTiles() { return tiles; }
}
