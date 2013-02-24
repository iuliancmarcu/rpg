package ro.enoor.rpg.entity;

import ro.enoor.rpg.level.Level;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends MoveableEntity {
	public static final TextureAtlas ATLAS = new TextureAtlas("atlases/enemy.atlas");
	public static final int WIDTH = 16;
	public static final int HEIGHT = 32;
	public static final float SPEED = 1f;
	public static final int HEALTH = 28;

	public Enemy(Level level, float x, float y) {
		super(level, x, y, WIDTH, HEIGHT, SPEED, HEALTH, "enemy");
		this.texture = ATLAS.findRegion("1");
		this.hitBox = new Rectangle(position.x + 2, position.y + HEIGHT / 8, WIDTH - 4, HEIGHT / 8);
	}

	protected void upadateHitBox() {
		hitBox.set(position.x + 2, position.y + HEIGHT / 8, hitBox.width, hitBox.height);		
	}
	
	protected void updateTexture() {
		//texture = ATLAS.findRegion("1");
	}
}