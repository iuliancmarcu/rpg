package ro.enoor.rpg.entity;

import ro.enoor.rpg.level.Level;

import com.badlogic.gdx.math.Vector2;

public abstract class MoveableEntity extends Entity {
	protected Vector2 velocity;
	protected float speed;

	public MoveableEntity(Level level, float x, float y, int width, int height, float speed, String type) {
		super(level, x, y, width, height, type);
		velocity = new Vector2();
		this.speed = speed;
	}
	
	public Vector2 getVelocity() { return velocity; }
	public float getSpeed() { return speed; }
}
