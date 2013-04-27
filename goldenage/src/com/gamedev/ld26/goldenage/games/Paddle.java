package com.gamedev.ld26.goldenage.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.utils.Config;
import com.gamedev.ld26.goldenage.utils.Utils;

public class Paddle {

	private Rectangle rect;
	private Color color;
	
	public Paddle(Vector2 pos, Vector2 size, Color color) {
		rect = new Rectangle(pos.x, pos.y, size.x, size.y);
		this.color = color;
	}
	
	/**
	 * Move along x axis by specified amount
	 * @param amount
	 */
	public void move(float amount) {
		rect.x += amount;
	}
	
	public void setPosition(float x, float y)
	{
		rect.x = Utils.clamp(x, 0, Config.window_width - rect.width);
		rect.y = Utils.clamp(y, 0, Config.window_height - rect.height);
	}
	
	public void render() {
		Assets.shapes.setColor(color);
		Assets.shapes.rect(rect.x, rect.y, rect.width, rect.height);
	}
	
	public Rectangle getRect() { return rect; }
	public Color getColor() { return color; }
	
}
