package ro.enoor.rpg.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import ro.enoor.rpg.entity.Enemy;
import ro.enoor.rpg.entity.Entity;
import ro.enoor.rpg.level.tile.EntityTile;
import ro.enoor.rpg.level.tile.Tile;
import ro.enoor.rpg.world.World;
import ro.enoor.rpg.world.WorldRenderer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level {
	private World world;
	public int[][] map;
	private int width, height;
	private ArrayList<Entity> entities;
	private ArrayList<Entity> onScreenEntities;

	public Level(World gameWorld, int levelWidth, int levelHeight) {
		world = gameWorld;
		
		if(levelWidth < 25) levelWidth = 25;
		if(levelHeight < 20) levelHeight = 20;
		
		width = levelWidth + 2;
		height = levelHeight + 2;
		
		entities = new ArrayList<Entity>();
		onScreenEntities = new ArrayList<Entity>();

		map = new int[height][width];
		TestGenerator.generate(map, height, width);
	}

	public void initLevel() {
		entities.add(world.getPlayer());

		for(int y = height - 1; y >= 0; y--)
			for(int x = 0; x < width; x++)
				if(Tile.isObject(map[y][x]))
					entities.add(new EntityTile(this, x, y, Tile.getTileById(map[y][x])));
		
		int x, y;
		Random rand = new Random();
		for(int i = 0; i < 5; i++) {
			do {
				x = rand.nextInt(width);
				y = rand.nextInt(height);
			} while(Tile.isSolid(map[y][x]));
			
			entities.add(new Enemy(this, x * Tile.TILE_SIZE, y * Tile.TILE_SIZE));
		}
	}
	
	public void update(float delta) {
		ArrayList<Entity> toRemove = new ArrayList<Entity>();
		
		onScreenEntities = new ArrayList<Entity>();
		for(Entity ent : entities) 
			if(ent.isOnScreen())
				onScreenEntities.add(ent);
		
		for(Entity ent : onScreenEntities) {
			ent.update(delta);
			if(ent.isRemoved())
				toRemove.add(ent);
		}
		
		for(Entity ent : toRemove)
			entities.remove(ent);
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

		int startX = (int) (Math.floor(camera.position.x - camera.viewportWidth / 2) / Tile.TILE_SIZE);
		int endX = (int) (Math.floor(camera.position.x + camera.viewportWidth / 2) / Tile.TILE_SIZE);
		int startY = (int) (Math.floor(camera.position.y - camera.viewportHeight / 2) / Tile.TILE_SIZE);
		int endY = (int) (Math.floor(camera.position.y + camera.viewportHeight / 2) / Tile.TILE_SIZE);
		
		startX = (startX >= 0) ? startX : 0;
		endX = (endX < width) ? endX : width - 1;
		startY = (startY >= 0) ? startY : 0;
		endY = (endY < height) ? endY : height - 1;
		
		for(int y = startY; y <= endY; y++)
			for(int x = startX; x <= endX; x++)
				if (!Tile.isObject(map[y][x]))
					Tile.getTileById(map[y][x]).draw(batch, x, y);
	}

	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public ArrayList<Entity> getEntities() { return entities; }
	public ArrayList<Entity> getVisibleEntities() { return onScreenEntities; }
}
