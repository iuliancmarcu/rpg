package ro.enoor.rpg.entity;

import ro.enoor.rpg.level.Level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Player extends Mob {
	private static final TextureAtlas ATLAS = new TextureAtlas("atlases/player.atlas");

	public static final float SPEED = 1f;
	public static final int HEALTH = 14;
	
	private long shootIntervalMS = 200, lastShoot;
	
	public Player(Level level, float x, float y) {
		super(level, x, y, SPEED, HEALTH, "player");
	}
	
	protected void updateTexture() {
		texture = ATLAS.findRegion("1" + facing);
		width = texture.getRegionWidth();
		height = texture.getRegionHeight();
	}

	protected void updateHitBox() {
		hitBox.set(position.x + 2, position.y + height / 8, width - 4, height / 8 + 2);		
	}
	
	public void shoot() {
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastShoot > shootIntervalMS) {
			level.getEntities().add(new Bullet(level, this));
			lastShoot = currentTime;
		}
	}
	
	public boolean isColliding() {
		if(!super.isColliding()) {
			for(Entity ent : level.getVisibleEntities())
				if(!ent.equals(this) && (ent instanceof Mob) && hitBox.overlaps(ent.hitBox))
					return true;
		}
		return super.isColliding();
	}
	
	public void draw(SpriteBatch batch) {
		Vector2 shadowPosition = new Vector2(position.x, position.y - 10);
		batch.draw(ATLAS.findRegion("shadow"), shadowPosition.x, shadowPosition.y);
		super.draw(batch);
	}
}