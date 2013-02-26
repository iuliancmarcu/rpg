package ro.enoor.rpg.entity;

import ro.enoor.rpg.level.Level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Player extends Mob {
	private static final TextureAtlas ATLAS = new TextureAtlas("atlases/player.atlas");

	public static final float N_SPEED = 1f, R_SPEED = 2f;
	public static final int HEALTH = 14;
	public static final int ATT_SPEED = 200;
	
	public Player(Level level, float x, float y) {
		super(level, x, y, N_SPEED, ATT_SPEED, HEALTH, "player");
	}
	
	protected void updateTexture() {
		texture = ATLAS.findRegion("1" + facing);
		width = texture.getRegionWidth();
		height = texture.getRegionHeight();
	}

	protected void updateHitBox() {
		hitBox.set(position.x + 2, position.y + height / 8, width - 4, height / 8 + 2);		
	}
	
	public void mobAttack() {
		level.getEntities().add(new Bullet(level, this));
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
	
	public void setSpeed(float speed) { this.speed = speed; }
}