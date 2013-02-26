package ro.enoor.rpg.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface YSortable {
	public Vector2 getPosition();
	public Rectangle getHitBox();
	public void draw(SpriteBatch batch);
}
