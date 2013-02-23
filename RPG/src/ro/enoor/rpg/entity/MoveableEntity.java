package ro.enoor.rpg.entity;

import ro.enoor.rpg.level.Level;
import ro.enoor.rpg.level.tile.Tile;

import com.badlogic.gdx.math.Vector2;

public abstract class MoveableEntity extends Entity {
	protected Vector2 velocity;
	protected float speed;
	protected int facing;

	public MoveableEntity(Level level, float x, float y, int width, int height, float speed, String type) {
		super(level, x, y, width, height, type);
		velocity = new Vector2();
		facing = 0;
		this.speed = speed;
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
		facing = dir;
	}
	
	protected abstract void upadateHitBox();
	protected abstract void updateTexture();
	
	public void update() {
		position.add(velocity);
		upadateHitBox();
		while(isColliding()) {
			position.sub(velocity);
			if(velocity.x > 0) velocity.x -= .1f;
			else velocity.x += .01f;
			if(velocity.y > 0) velocity.y -= .01f;
			else velocity.y += .01f;
			position.add(velocity);
			upadateHitBox();
		}
		velocity.set(Vector2.Zero);
		
		updateTexture();
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
	
	public Vector2 getVelocity() { return velocity; }
	public float getSpeed() { return speed; }
	public int getFacing() { return facing; }
}
