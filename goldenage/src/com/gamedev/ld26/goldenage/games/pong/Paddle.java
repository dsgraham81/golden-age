package com.gamedev.ld26.goldenage.games.pong;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.core.Assets;

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
	
	public void render() {
		Assets.shapes.setColor(color);
		Assets.shapes.rect(rect.x, rect.y, rect.width, rect.height);
	}
	
	public Rectangle getRect() { return rect; }
	public Color getColor() { return color; }
	
}
