package ro.enoor.rpg.entity;

import ro.enoor.rpg.level.Level;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Enemy extends Mob {
	public static TextureAtlas ATLAS = new TextureAtlas("atlases/enemy.atlas");
	
	public static final float SPEED = 1f;
	public static final int HEALTH = 28;
	public static final int ATT_SPEED = 200;

	public Enemy(Level level, float x, float y) {
		super(level, x, y, SPEED, ATT_SPEED, HEALTH, "enemy");
	}
	
	protected void updateTexture() {
		texture = ATLAS.findRegion("1");
		width = texture.getRegionWidth();
		height = texture.getRegionHeight();
	}

	protected void updateHitBox() {
		hitBox.set(position.x + 1, position.y, width - 2, height / 4);		
	}

	protected void mobAttack() {
		
	}
}