package ro.enoor.rpg.entity;

import ro.enoor.rpg.level.Level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends MoveableEntity {
	private static final Texture TEXTURE = new Texture("images/bullet.png");
	
	public static final float SPEED = 3f;
	public static final int DAMAGE = 4;
	
	private Entity owner;
	
	public Bullet(Level level, MoveableEntity owner) {
		super(level, owner.position.x + owner.width / 2 - 4, owner.position.y + owner.height / 4 - 4, SPEED, "bullet");
		this.owner = owner;
		facing = owner.getFacing();
	}

	protected void updateTexture() { 
		texture = new TextureRegion(TEXTURE);
		width = texture.getRegionWidth();
		height = texture.getRegionHeight();
	}

	protected void updateHitBox() {
		hitBox = new Rectangle(position.x + width / 4, position.y + width / 4, width / 2, width / 2);
	}
	
	public void update() {
		move(facing);
		
		position.add(velocity);
		updateHitBox();
		velocity.set(Vector2.Zero);
		
		for(Entity ent : level.getVisibleEntities())
			if(!ent.equals(owner) && (ent instanceof Mob) && hitBox.overlaps(ent.hitBox)) {
				setRemoved();
				((Mob) ent).hurt(DAMAGE);
			}
		if(isColliding() || !isOnScreen()) setRemoved();
	}
	
	public void draw(SpriteBatch batch) {
		float rotation = 360 - (facing * 90);
		batch.draw(texture.getTexture(), position.x, position.y, width / 2, width / 2, width, width, 1, 1, rotation, 0, 0, width, width, false, false);
	}
}
