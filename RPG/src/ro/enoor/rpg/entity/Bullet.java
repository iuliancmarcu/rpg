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
	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;
	public static final int DAMAGE = 2;
	
	private Entity owner;
	
	public Bullet(Level level, MoveableEntity owner) {
		super(level, owner.position.x + owner.width / 2 - WIDTH / 2, owner.position.y + owner.height / 4 - HEIGHT / 2, WIDTH, HEIGHT, SPEED, 0,"bullet");
		this.owner = owner;
		facing = owner.getFacing();
		texture = new TextureRegion(TEXTURE);
	}
	
	public void update() {
		move(facing);
		
		position.add(velocity);
		upadateHitBox();
		velocity.set(Vector2.Zero);
		
		for(Entity ent : level.getVisibleEntities())
			if(!ent.equals(owner) && (ent.getType().equals("player") || ent.getType().equals("enemy")) && hitBox.overlaps(ent.hitBox)) {
				setRemoved();
				((MoveableEntity) ent).hurt(DAMAGE);
			}
		if(isColliding() || !isOnScreen()) setRemoved();
	}

	protected void upadateHitBox() {
		hitBox = new Rectangle(position.x + WIDTH / 4, position.y + WIDTH / 4, WIDTH / 2, HEIGHT / 2);
	}
	
	public void draw(SpriteBatch batch) {
		float rotation = 360 - (facing * 90);
		batch.draw(texture.getTexture(), position.x, position.y, WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, 1, rotation, 0, 0, WIDTH, HEIGHT, false, false);
	}

	protected void updateTexture() { }
}
