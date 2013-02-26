package ro.enoor.rpg.level.tile;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Tile {
	public static final TextureAtlas ATLAS = new TextureAtlas("atlases/tiles.atlas");
	private static final TextureAtlas SHADOWS = new TextureAtlas("atlases/shadows.atlas");

	public static final int TILE_SIZE = 16;
	
    public static final Tile[] TILES = new Tile[256];
    public static final Tile VOID = new SolidTile(0, false, "void");
    public static final Tile GRASS = new BasicTile(1, false, "grass");
    public static final Tile DIRT = new BasicTile(2, false, "dirt");
    public static final Tile SAND = new BasicTile(3, false, "sand");
    public static final Tile WALL = new SolidTile(4, true, "wall");
    
    protected byte id;
    protected boolean solid;
    protected boolean object;
    protected TextureRegion texture;

    public Tile(int id, boolean solid, boolean object, String name) {
        this.id = (byte) id;
        if (TILES[id] != null) throw new RuntimeException("Duplicate tile id on " + id);
        this.solid = solid;
        this.object = object;
        TILES[id] = this;
        
        if(name == "void") texture = new TextureRegion(new Texture((int) TILE_SIZE, (int) TILE_SIZE, Format.RGBA8888));
        else texture = ATLAS.findRegion(name);
    }
    
    public static void drawShadow(SpriteBatch batch, int x, int y, int dir) {
    	batch.draw(SHADOWS.findRegion("" + dir), x * TILE_SIZE, y * TILE_SIZE);
    }
	
	public void draw(SpriteBatch batch, float x, float y) {
		batch.draw(texture, x * TILE_SIZE, y * TILE_SIZE);
	}
    
	public static Tile getTileById(int id) {
    	if(TILES[id] != null) return TILES[id];
    	return TILES[0];
    }
    
	public int getId() { return id; }
    public boolean isSolid() { return solid; }
    public static boolean isSolid(int id) { return getTileById(id).isSolid(); }
    public boolean isObject() { return object; }
    public static boolean isObject(int id) { return getTileById(id).isObject(); }
}