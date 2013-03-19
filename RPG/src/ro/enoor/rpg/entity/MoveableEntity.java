package ro.enoor.rpg.entity;

import ro.enoor.rpg.level.Level;
import ro.enoor.rpg.level.tile.Tile;

import com.badlogic.gdx.math.Vector2;

public abstract class MoveableEntity extends Entity {
	protected Vector2 velocity;
	protected float speed;
	protected int facing;

	public MoveableEntity(Level level, float x, float y, float normalSpeed, String type) {
		super(level, x, y, type);
		velocity = new Vector2();
		facing = 0;
		speed = normalSpeed;
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
	
	public void update(float delta) {
		position.add(velocity.mul(delta));
		updateHitBox();
		while(isColliding()) {
			position.sub(velocity);
			if(velocity.x > 0) velocity.x -= .01f;
			else velocity.x += .01f;
			if(velocity.y > 0) velocity.y -= .01f;
			else velocity.y += .01f;
			position.add(velocity);
			updateHitBox();
		}
		velocity.set(Vector2.Zero);
		
		updateTexture();
	}
	
	public boolean isColliding() {
		int tileX, tileY;
		
		for(int i = 0; i <= (int) hitBox.width; i += 2) {
			tileX = (int) ((hitBox.x + i) / Tile.TILE_SIZE);
			
			tileY = (int) (hitBox.y / Tile.TILE_SIZE);
			if (Tile.isSolid(level.map[tileY][tileX]) && !Tile.isObject(level.map[tileY][tileX])) return true;
			tileY = (int) ((hitBox.y + hitBox.height) / Tile.TILE_SIZE);
			if (Tile.isSolid(level.map[tileY][tileX]) && !Tile.isObject(level.map[tileY][tileX])) return true;
		}
		for(int i = 0; i <= (int) hitBox.height; i += 2) {
			tileY = (int) ((hitBox.y + i) / Tile.TILE_SIZE);
			
			tileX = (int) (hitBox.x / Tile.TILE_SIZE);
			if (Tile.isSolid(level.map[tileY][tileX]) && !Tile.isObject(level.map[tileY][tileX])) return true;
			tileX = (int) ((hitBox.x + hitBox.width) / Tile.TILE_SIZE);
			if (Tile.isSolid(level.map[tileY][tileX]) && !Tile.isObject(level.map[tileY][tileX])) return true;
		}
		
		return false;		
	}
	
	public Vector2 getVelocity() { return velocity; }
	public float getSpeed() { return speed; }
	public int getFacing() { return facing; }
}
