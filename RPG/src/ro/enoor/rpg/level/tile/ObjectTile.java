package ro.enoor.rpg.level.tile;

import ro.enoor.rpg.entity.Entity;
import ro.enoor.rpg.level.Level;

import com.badlogic.gdx.math.Vector2;

public class ObjectTile extends Entity {
	private boolean solid;
	
	public ObjectTile(Level level, int x, int y, Tile tile) {
		super(level, x, y, "tile");
		position = new Vector2(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
		solid = tile.solid;
		texture = tile.texture;
	}
	
	protected void updateTexture() {
		texture = Tile.ATLAS.findRegion("wall");
		width = height = (int) Tile.TILE_SIZE;
	}

	protected void updateHitBox() {
		hitBox.set(position.x, position.y, width, height);
	}
	
	public void update(float delta) { }

	public boolean isSolid() { return solid; }
}
