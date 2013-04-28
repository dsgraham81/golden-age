package com.gamedev.ld26.goldenage.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.IShooter;

public class Bullet extends Ball {
	
	public Bullet(IShooter player, float radius, Color color, GameState gameState) {
		this(player.GetTip(), radius, color, player.getShootDirection(), player.getShootSpeed(), gameState);
	}
		
	public Bullet(Vector2 position, float radius, Color color, Vector2 direction, int speed, GameState gameState) {
		super(position,  radius, color, gameState);
		setSpeed(speed);
		setVelocity(direction);
	}
	
	protected void keepInsideRect(Rectangle bounds) {
		
	}
	
	public void update(float delta) {
		super.update(delta);
	}
}
