package ro.enoor.rpg.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class GuiElement {
	public static final TextureAtlas ATLAS = new TextureAtlas("atlases/gui.atlas");
	protected Vector2 position;
	protected TextureRegion texture;
	protected boolean visible;
	
	public GuiElement(float x, float y, String name) {
		position = new Vector2(x, y);
		texture = ATLAS.findRegion(name);
		visible = true;
	}
	
	public abstract void update();
	public void draw(SpriteBatch batch) {
		if(visible) batch.draw(texture, position.x, position.y);
	}
}
