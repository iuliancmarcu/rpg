package ro.enoor.rpg.entity;

import ro.enoor.rpg.level.Level;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Player extends MoveableEntity {
	private static final TextureAtlas ATLAS = new TextureAtlas("atlases/player.atlas");

	public static final int WIDTH = 16;
	public static final int HEIGHT = 32;
	public static final float SPEED = 1f;
	
	private long shootIntervalMS = 200, lastShoot;
	
	public Player(Level level, float x, float y) {
		super(level, x, y, WIDTH, HEIGHT, SPEED, "player");
		this.texture = ATLAS.findRegion("1" + facing);
		this.hitBox = new Rectangle(position.x + 2, position.y + HEIGHT / 8, WIDTH - 4, HEIGHT / 8);
	}

	protected void upadateHitBox() {
		hitBox.set(position.x + 2, position.y + HEIGHT / 8, hitBox.width, hitBox.height);		
	}
	
	protected void updateTexture() {
		texture = ATLAS.findRegion("1" + facing);
	}
	
	public boolean isColliding() {
		if(!super.isColliding()) {
			for(Entity ent : level.getVisibleEntities())
				if(!ent.equals(this) && (ent.getType().equals("player") || ent.getType().equals("enemy")) && hitBox.overlaps(ent.hitBox))
					return true;
		}
		return super.isColliding();
	}
	
	public void shoot() {
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastShoot > shootIntervalMS) {
			level.getEntities().add(new Bullet(level, this));
			lastShoot = currentTime;
		}
	}
}