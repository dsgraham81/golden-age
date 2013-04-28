package com.gamedev.ld26.goldenage.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.DirectionVector;
import com.gamedev.ld26.goldenage.IShooter;

public class Paddle extends GameObject implements IShooter {

	public int speed = 2000;
	public Vector2 direction = DirectionVector.Up;
	
	public Paddle(Vector2 pos, Vector2 size, Color color, GameState gs) {
		super(pos, size, color, gs);
	}
	
	public Vector2 GetTip() {
		return new Vector2(_rect.x + (_rect.width/2), _rect.y + _rect.height);
	}

	@Override
	public int getShootSpeed() {
		return speed;
	}

	@Override
	public Vector2 getShootDirection() {
		return direction;
	}
}