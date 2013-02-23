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
	public static final float SPEED = 1f;

	public Player(Level level, float x, float y) {
		super(level, x, y, WIDTH, HEIGHT, SPEED, "player");
		this.texture = atlas.findRegion("1");
		this.hitBox = new Rectangle(position.x + 2, position.y + HEIGHT / 8, WIDTH - 4, HEIGHT / 8);
	}

	private void upadateHitBox() {
		hitBox.set(position.x + 2, position.y + HEIGHT / 8, hitBox.width, hitBox.height);		
	}
	
	public void update() {
		position.add(velocity.x, 0);
		upadateHitBox();
		while(isColliding()) {
			position.sub(velocity.x, 0);
			if(velocity.x > 0) velocity.x -= .1f;
			else velocity.x += .01f;
			position.add(velocity.x, 0);
			upadateHitBox();
		}
		
		position.add(0, velocity.y);
		upadateHitBox();
		while(isColliding()) {
			position.sub(0, velocity.y);
			if(velocity.y > 0) velocity.y -= .01f;
			else velocity.y += .01f;
			position.add(0, velocity.y);
			upadateHitBox();
		}
		
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