package com.gamedev.ld26.goldenage.games.spaceinvaders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.DirectionVector;
import com.gamedev.ld26.goldenage.IShooter;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.games.GameObject;
import com.gamedev.ld26.goldenage.games.GameState;

public class BaseInvader extends GameObject implements IShooter {

	public BaseInvader(GameState gs, float size, Color color) {
		super(Vector2.Zero, new Vector2(size, size), color, gs);
	}
		
	public void render()
	{
		Assets.shapes.setColor(_color);
		Assets.shapes.rect(_rect.x, _rect.y, _rect.width, _rect.height);
		Assets.shapes.end();
		
		Assets.shapes.begin(ShapeType.Line);
		Assets.shapes.setColor(Color.GRAY);
		Assets.shapes.rect(_rect.x, _rect.y, _rect.width, _rect.height);
		Assets.shapes.end();
		
		Assets.shapes.begin(ShapeType.Filled);
	}

	@Override
	public Vector2 GetTip() {
		return new Vector2(_rect.x + (_rect.width/2), _rect.y);
	}

	@Override
	public int getShootSpeed() {
		return 1000 + Assets.random.nextInt(500);
	}

	@Override
	public Vector2 getShootDirection() {
		return new Vector2(DirectionVector.Down);
	}
}
