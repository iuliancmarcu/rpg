package ro.enoor.rpg.entity;

import ro.enoor.rpg.level.Level;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Player extends Mob {
	private static TextureAtlas ATLAS = new TextureAtlas("atlases/player.atlas");

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
		hitBox.set(position.x + 1, position.y, width - 2, height / 4);	
	}
	
	public void mobAttack() {
		level.getEntities().add(new Bullet(level, this));
	}
	
	public boolean isColliding() {
		if(!super.isColliding()) {
			for(Entity ent : level.getVisibleEntities())
				if(!ent.equals(this) && hitBox.overlaps(ent.hitBox) && !(ent instanceof Bullet))
					return true;
		}
		return super.isColliding();
	}
	
	public void setSpeed(float speed) { this.speed = speed; }
}