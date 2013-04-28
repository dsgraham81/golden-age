package com.gamedev.ld26.goldenage.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Paddle extends GameObject {

	public Paddle(Vector2 pos, Vector2 size, Color color, GameState gs) {
		super(pos, size, color, gs);
	}
	
	public Vector2 GetTip() {
		return new Vector2(_rect.x + (_rect.width/2), _rect.y + _rect.height);
	}
}
