package ro.enoor.rpg.entity;

import ro.enoor.rpg.level.Level;
import ro.enoor.rpg.level.tile.Tile;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends MoveableEntity {
	private static final TextureAtlas atlas = new TextureAtlas("atlases/player.atlas");

	public static final int WIDTH = 16;
	public static final int HEIGHT = 32;
	public static final float SPEED = 1.5f;

	public Player(Level level, float x, float y) {
		super(level, x, y, WIDTH, HEIGHT, SPEED, "player");
		this.texture = atlas.findRegion("1");
		this.hitBox = new Rectangle(position.x + WIDTH / 4, position.y + HEIGHT / 8, WIDTH / 2, HEIGHT / 8);
	}

	public void update() {
		position.add(velocity.x, 0);
		hitBox.set(position.x + WIDTH / 4, position.y + HEIGHT / 8, hitBox.width, hitBox.height);
		if(isColliding()) position.sub(velocity.x, 0);
		
		position.add(0, velocity.y);
		hitBox.set(position.x + WIDTH / 4, position.y + HEIGHT / 8, hitBox.width, hitBox.height);
		if(isColliding()) position.sub(0, velocity.y);
		
		velocity.set(Vector2.Zero);
	}

	public void move(int dir) {
		switch (dir) {
			case 0:
				velocity.add(new Vector2(0, -speed));
				break;
			case 1:
				velocity.add(new Vector2(-speed, 0));
				break;
			case 2:
				velocity.add(new Vector2(0, speed));
				break;
			case 3:
				velocity.add(new Vector2(speed, 0));
				break;
		}
	}
	
	public boolean isColliding() {
		int tileX, tileY;
		
		for(int i = 0; i < (int) hitBox.width; i++) {
			tileX = (int) ((hitBox.x + i) / Tile.TILE_SIZE);
			
			tileY = (int) (hitBox.y / Tile.TILE_SIZE);
			if (Tile.isSolid(level.map[tileY][tileX])) return true;
			
			tileY = (int) ((hitBox.y + hitBox.height) / Tile.TILE_SIZE);
			if (Tile.isSolid(level.map[tileY][tileX])) return true;
		}
		for(int i = 0; i < (int) hitBox.height; i++) {
			tileY = (int) ((hitBox.y + i) / Tile.TILE_SIZE);
			
			tileX = (int) (hitBox.x / Tile.TILE_SIZE);
			if (Tile.isSolid(level.map[tileY][tileX])) return true;
			
			tileX = (int) ((hitBox.x + hitBox.width) / Tile.TILE_SIZE);
			if (Tile.isSolid(level.map[tileY][tileX])) return true;
		}
		return false;		
	}
	
	public void setLevel(Level level) { this.level = level; }
}