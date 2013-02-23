package ro.enoor.rpg.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import ro.enoor.rpg.entity.Enemy;
import ro.enoor.rpg.entity.Entity;
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
	private ArrayList<Entity> onScreenEntities;
	private Random random;

	public Level(World world, int width, int height) {
		this.world = world;
		this.width = width + 2;
		this.height = height + 2;
		
		entities = new ArrayList<Entity>();
		onScreenEntities = new ArrayList<Entity>();
		
		random = new Random();

		map = new int[height + 2][width + 2];
		generate();
	}

	public void initLevel() {
		entities.add(world.getPlayer());

		for(int y = height - 1; y >= 0; y--)
			for(int x = 0; x < width; x++)
				if(Tile.isObject(map[y][x]))
					entities.add(new ObjectTile(this, Tile.getTileById(map[y][x]), x, y));
		
		int x, y;
		for(int i = 0; i < 10; i++) {
			do {
				x = random.nextInt(width);
				y = random.nextInt(height);
			} while(Tile.isSolid(map[y][x]));
			
			entities.add(new Enemy(this, x * Tile.TILE_SIZE, y * Tile.TILE_SIZE));
		}
	}

	private void generate() {
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				if(y == 0 || y == height - 1) map[y][x] = Tile.WALL.getId();
				else if(x == 0 || x == width - 1) map[y][x] = Tile.WALL.getId();
				else if(random.nextBoolean()) map[y][x] = 1 + random.nextInt(4);
				else map[y][x] = Tile.GRASS.getId();
			}
	}
	
	public void update() {
		ArrayList<Entity> toRemove = new ArrayList<Entity>();
		onScreenEntities = getOnScreenEntities();
		
		for(Entity ent : onScreenEntities) {
			ent.update();
			if(ent.isRemoved())
				toRemove.add(ent);
		}
		
		for(Entity ent : toRemove)
			entities.remove(ent);
	}
	
	private ArrayList<Entity> getOnScreenEntities() {
		ArrayList<Entity> onScreenEntities = new ArrayList<Entity>();
		
		for(Entity ent : entities) 
			if(ent.isOnScreen())
				onScreenEntities.add(ent);
		
		return onScreenEntities;
	}

	public void renderEntities(SpriteBatch batch) {
		Collections.sort(onScreenEntities, new Comparator<Entity>() {
			public int compare(Entity a, Entity b) {
				return Integer.signum((int) ((-a.getPosition().y) - (-b.getPosition().y)));
			}
		});

		for(Entity ent : onScreenEntities)
			ent.draw(batch);
	}

	public void renderTiles(SpriteBatch batch) {
		OrthographicCamera camera = WorldRenderer.getCamera();

		int startX = (int) (camera.position.x - camera.viewportWidth / 2) / Tile.TILE_SIZE;
		int endX = (int) (camera.position.x + camera.viewportWidth / 2) / Tile.TILE_SIZE;
		int startY = (int) (camera.position.y - camera.viewportHeight / 2) / Tile.TILE_SIZE;
		int endY = (int) (camera.position.y + camera.viewportHeight / 2) / Tile.TILE_SIZE;
		
		startX = (startX >= 0) ? startX : 0;
		endX = (endX < width) ? endX : width - 1;
		startY = (startY >= 0) ? startY : 0;
		endY = (endY < height) ? endY : height - 1;
		
		for(int y = startY; y <= endY; y++)
			for(int x = startX; x <= endX; x++)
				if (!Tile.isObject(map[y][x])) 
					Tile.getTileById(map[y][x]).draw(batch, x, y);
	}

	public void renderTilesHitBox(ShapeRenderer shapeRenderer) {
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				if (Tile.isSolid(map[y][x])) 
					Tile.getTileById(map[y][x]).drawHitBox(shapeRenderer, x, y);
	}
	
	public void renderEntitiesHitBox(ShapeRenderer shape) {
		for(Entity ent : onScreenEntities) {
			ent.drawHitBox(shape);
		}
	}

	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public ArrayList<Entity> getEntities() { return entities; }
	public ArrayList<Entity> getVisibleEntities() { return onScreenEntities; }
}
