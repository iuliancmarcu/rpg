package ro.enoor.rpg.entity;

import ro.enoor.rpg.level.Level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	protected Rectangle hitBox;
	protected Vector2 position;
	protected TextureRegion texture;
	protected Level level;
	private String type;

	public Entity(Level level, float x, float y, int width, int height, String type) {
		this.level = level;
		position = new Vector2(x, y);
		hitBox = new Rectangle(x, y, width, height);
		this.type = type;
	}

	public void update() {
		hitBox.set(position.x, position.y, hitBox.width, hitBox.height);
	}

	public void drawHitBox(ShapeRenderer shapeRenderer) {
		shapeRenderer.rect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture, position.x, position.y);
	}
	
	public Rectangle getHitBox() { return hitBox; }
	public Vector2 getPosition() { return position; }
	public TextureRegion getTexture() { return texture; }
	public String getType() { return type; }
}
