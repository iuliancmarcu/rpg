package ro.enoor.rpg.level.tile;

import ro.enoor.rpg.entity.YSortable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ObjectTile implements YSortable {
	private Vector2 position;
	private Rectangle hitBox;
	private TextureRegion texture;
	private boolean solid;
	
	public ObjectTile(Tile tile, int x, int y) {
		position = new Vector2(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
		hitBox = new Rectangle(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
		solid = tile.solid;
		texture = tile.texture;
	}
	
	public void drawHitBox(ShapeRenderer shape) {
		shape.rect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture, position.x, position.y);
	}
	
	public Vector2 getPosition() { return position; }
	public Rectangle getHitBox() { return hitBox; }
	public boolean isSolid() { return solid; }
}
