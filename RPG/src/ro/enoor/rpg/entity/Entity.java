package ro.enoor.rpg.entity;

import ro.enoor.rpg.level.Level;
import ro.enoor.rpg.level.tile.Tile;
import ro.enoor.rpg.world.WorldRenderer;

import com.badlogic.gdx.graphics.OrthographicCamera;
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
	protected boolean removed;
	protected int width, height;
	private String type;

	public Entity(Level level, float x, float y, String type) {
		this.level = level;
		this.type = type;
		position = new Vector2(x, y);
		hitBox = new Rectangle();
		
		updateTexture();
		updateHitBox();
	}
	
	public boolean isOnScreen() {
		OrthographicCamera camera = WorldRenderer.getCamera();
		
		if(position.x + 2 * Tile.TILE_SIZE > camera.position.x - camera.viewportWidth / 2 &&
				position.x - Tile.TILE_SIZE < camera.position.x + camera.viewportWidth / 2 &&
				position.y + 2 * Tile.TILE_SIZE > camera.position.y - camera.viewportHeight / 2 &&
				position.y - Tile.TILE_SIZE < camera.position.y + camera.viewportHeight / 2)
			return true;
		return false;
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture, position.x, position.y);
	}
	
	public void drawHitBox(ShapeRenderer shapeRenderer) {
		shapeRenderer.rect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
	}
	
	public abstract void update();
	protected abstract void updateTexture();
	protected abstract void updateHitBox();

	public void setLevel(Level level) { this.level = level; }
	public boolean isRemoved() { return removed; }
	public void setRemoved() { removed = true; }
	public Rectangle getHitBox() { return hitBox; }
	public Vector2 getPosition() { return position; }
	public String getType() { return type; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
}
