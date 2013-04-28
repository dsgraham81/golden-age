package com.gamedev.ld26.goldenage.games.spaceinvaders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.games.GameObject;
import com.gamedev.ld26.goldenage.games.GameState;

public class BaseInvader extends GameObject {

	private Circle _circle = new Circle();
	
	public BaseInvader(GameState gs, float size, Color color) {
		super(Vector2.Zero, Vector2.Zero, color, gs);
		setRadius(size/2);
	}
	
	public void setRadius(float radius) {
		float doubleRadius = radius*2;
		super.setSize(doubleRadius, doubleRadius);

		_circle.set(_circle.x, _circle.y, radius);
	}
	
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		_circle.set(x, y, _circle.radius);
	}
	
	public void render()
	{
		Assets.shapes.setColor(_color);
		Assets.shapes.circle(_circle.x, _circle.y, _circle.radius);
		Assets.shapes.end();
		
		Assets.shapes.begin(ShapeType.Line);
		Assets.shapes.setColor(Color.GRAY);
		Assets.shapes.circle(_circle.x, _circle.y, _circle.radius);
		Assets.shapes.end();
		
		Assets.shapes.begin(ShapeType.Filled);
	}
}
