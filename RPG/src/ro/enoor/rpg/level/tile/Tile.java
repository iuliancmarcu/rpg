package ro.enoor.rpg.level.tile;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class Tile {
	public static final int TILE_SIZE = 16;
	public static final TextureAtlas ATLAS = new TextureAtlas("atlases/tiles.atlas");
    public static final Tile[] TILES = new Tile[256];
    
    public static final Tile VOID = new SolidTile(0, "void");
    public static final Tile GRASS = new BasicTile(1, "grass");
    public static final Tile DIRT = new BasicTile(2, "dirt");
    public static final Tile SAND = new BasicTile(3, "sand");
    public static final Tile WALL = new SolidTile(4, "wall");
    
    protected byte id;
    protected boolean solid;
    protected TextureRegion texture;

    public Tile(int id, boolean solid, String name) {
        this.id = (byte) id;
        if (TILES[id] != null)
            throw new RuntimeException("Duplicate tile id on " + id);
        this.solid = solid;
        TILES[id] = this;
        
        if(name == "void") texture = new TextureRegion(new Texture(TILE_SIZE, TILE_SIZE, Format.RGBA8888));
        else texture = ATLAS.findRegion(name);
    }
    
    public void drawHitBox(ShapeRenderer shapeRenderer, int x, int y) {
    	Rectangle hitBox = new Rectangle(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		shapeRenderer.rect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
	}
	
	public void draw(SpriteBatch batch, int x, int y) {
		batch.draw(texture, x * TILE_SIZE, y * TILE_SIZE);
	}
    
    public static Tile getTileById(int id) {
    	if(TILES[id] != null) return TILES[id];
    	return TILES[0];
    }
    
    public static boolean isSolid(int id) {
    	return getTileById(id).isSolid();
    }
    
    public int getId() { return id; }
    public boolean isSolid() { return solid; }
}