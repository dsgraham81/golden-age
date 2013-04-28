package com.gamedev.ld26.goldenage.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.DirectionVector;

public class Bullet extends Ball {

	public Bullet(Paddle player, GameState gameState)
	{
		this(player.GetTip(), 9, Color.GREEN, DirectionVector.Up, 72, gameState);
	}
	
	public Bullet(Vector2 position, float radius, Color color, Vector2 direction, int speed, GameState gameState) {
		super(position,  radius, color, gameState);
		setSpeed(speed);
		setVelocity(direction);
	}
}
