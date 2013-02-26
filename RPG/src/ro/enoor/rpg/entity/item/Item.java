package ro.enoor.rpg.entity.item;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ro.enoor.rpg.entity.Entity;
import ro.enoor.rpg.level.Level;

public abstract class Item extends Entity {
	public static final TextureAtlas ATLAS = new TextureAtlas("atlases/items.atlas");
	public Item(Level level, float x, float y, String type) {
		super(level, x, y, type);
	}

	public void update(float delta) {
		
	}

	protected void updateTexture() {
		
	}

	protected void updateHitBox() {
		
	}

}
