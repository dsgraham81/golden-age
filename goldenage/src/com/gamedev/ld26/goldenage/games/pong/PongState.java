package com.gamedev.ld26.goldenage.games.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.games.Ball;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.games.Paddle;
import com.gamedev.ld26.goldenage.utils.Config;

public class PongState extends GameState {

	private Paddle cpu;
	private Ball ball;
	
	static Vector2 size = new Vector2(Config.pong_paddle_size_x, Config.pong_paddle_size_y);
		
	public PongState(GoldenAgeGame game, GameState previous) {
		super(game, previous);
		_player = new Paddle(new Vector2(Config.window_width / 2, 0), size, Color.WHITE, this);
		cpu = new Paddle(new Vector2(Config.window_half_width, Config.window_height - size.y), size, Color.WHITE, this);
		ball = new Ball(new Vector2(Config.window_half_width, Config.window_half_height), 10, Color.WHITE, this);
	}
	
	@Override
	protected void updateScreen(float delta) {
		ball.update(Gdx.graphics.getDeltaTime());
		handleCollisions();
	}

	@Override
	protected void renderScreen(float delta) {
		cpu.render();
		ball.render();
		Assets.shapes.setColor(Color.WHITE);
	}	
	
	private void handleCollisions() {
		final Rectangle playerRect = _player.getRect();
		final Rectangle cpuRect = cpu.getRect();
		final Circle ballCircle = ball.getCircle();
		Vector2 ballDir = ball.getDir();
		
		// Intersection tests - ball/paddle
		if (Intersector.overlaps(ballCircle, playerRect)) {
			ballCircle.y = playerRect.y + playerRect.height + ballCircle.radius;
			ballDir.y *= -1;
			ball.setSpeed(ball.getSpeed() + 50.f);
		}
		
		if (Intersector.overlaps(ball.getCircle(), cpuRect)) {
			ballCircle.y = cpuRect.y - ballCircle.radius;
			ballDir.y *= -1;
			ball.setSpeed(ball.getSpeed() + 50.f);
		}
	}
	
}
