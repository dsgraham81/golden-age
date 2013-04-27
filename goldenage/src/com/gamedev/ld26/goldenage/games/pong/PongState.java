package com.gamedev.ld26.goldenage.games.pong;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.utils.Config;

public class PongState implements GameState {

	private final GoldenAgeGame game;
	
	private Paddle player;
	private Paddle cpu;
	private Circle ball;
	
	public PongState(GoldenAgeGame game) {
		this.game = game;
		final Vector2 size = new Vector2(80, 20);
		player = new Paddle(new Vector2(Config.window_width / 2, 0), size, Color.WHITE);
		cpu = new Paddle(new Vector2(Config.window_width / 2, Config.window_height - size.y), size, Color.WHITE);
		ball = new Circle(Config.window_width / 2, Config.window_height / 2, 10);
	}
	
	@Override
	public void update(float delta) {
		final float scale = game.input.getCurrMouse().x - game.input.getPrevMouse().x;
		final float speed = 1.f;
		final float amount = speed * scale;
		player.move(amount);
		
		// Undo move if we've gone too far
		final Rectangle rect = player.getRect();
		if (rect.x < 0 || (rect.x + rect.width) > Config.window_width) {
			player.move(-amount);
		}
	}

	@Override
	public void render(float delta) {
		Assets.shapes.begin(ShapeType.Filled);
		player.render();
		cpu.render();
		Assets.shapes.circle(ball.x, ball.y, ball.radius);
		Assets.shapes.setColor(Color.WHITE);
		Assets.shapes.end();
	}
	
}
