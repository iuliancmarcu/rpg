package ro.enoor.rpg.level.tile;

import ro.enoor.rpg.entity.Entity;
import ro.enoor.rpg.level.Level;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ObjectTile extends Entity {
	private boolean solid;
	
	public ObjectTile(Level level, Tile tile, int x, int y) {
		super(level, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, "tile");
		position = new Vector2(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
		hitBox = new Rectangle(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
		solid = tile.solid;
		texture = tile.texture;
	}
	
	public void update() { }
	public boolean isSolid() { return solid; }
}
