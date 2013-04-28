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
import com.gamedev.ld26.goldenage.utils.Utils;

public class PongState extends GameState {

	private static final float div_width  = 16;
	private static final float div_height = 16;	
	private static final Vector2 size = new Vector2(Config.pong_paddle_size_x, Config.pong_paddle_size_y);
	
	private Rectangle _divider;
	private Rectangle _edgeLeft;
	private Rectangle _edgeRight;
	
	private Paddle _cpu;
	private Ball _ball;
		
	public PongState(GoldenAgeGame game, GameState previous) {
		super(game, previous);
		
		_divider = new Rectangle(Config.pong_window_bounds.x + div_width / 2, Config.window_half_height - div_height / 2, div_width, div_height);
		_edgeLeft = new Rectangle(Config.pong_window_bounds.x - 20, 0, 20, Config.window_height);
		_edgeRight = new Rectangle(Config.pong_window_bounds.x + Config.pong_window_bounds.width, 0, 20, Config.window_height);
		
		_cpu = new Paddle(new Vector2(Config.window_half_width, Config.window_height - size.y), size, Color.WHITE, this);
		_ball = new Ball(new Vector2(Config.window_half_width, Config.window_half_height), 10, Color.WHITE, this);
		_windowBounds = new Rectangle(Config.window_half_width / 2, 0, Config.window_half_width, Config.window_height);
	}
	
	@Override
	protected void updateScreen(float delta) {
		_ball.update(Gdx.graphics.getDeltaTime());
		Utils.constrainToRect(_player, Config.pong_window_bounds);
		Utils.constrainToRect(_cpu, Config.pong_window_bounds);
		Utils.constrainToRect(_ball, Config.pong_window_bounds);
		handleCollisions();
	}

	@Override
	protected void renderScreen(float delta) {
		_cpu.render();
		_ball.render();
		Assets.shapes.setColor(Color.WHITE);
		
		// Borders + divider
		float w = _divider.width * 2;
		int num = (int) (Config.pong_window_bounds.width / w);
		for(int i = 0; i < num; ++i) {
			float x = _divider.x + i * w;
			Assets.shapes.rect(x, _divider.y, _divider.width, _divider.height);
		}
		Assets.shapes.rect(_edgeLeft.x, _edgeLeft.y, _edgeLeft.width, _edgeLeft.height);
		Assets.shapes.rect(_edgeRight.x, _edgeRight.y, _edgeRight.width, _edgeRight.height);
	}	
	
	private void handleCollisions() {
		final Rectangle playerRect = _player.getRect();
		final Rectangle cpuRect = _cpu.getRect();
		final Circle ballCircle = _ball.getCircle();
		Vector2 ballDir = _ball.getDir();
		
		// Intersection tests - ball/paddle
		if (Intersector.overlaps(ballCircle, playerRect)) {
			ballCircle.y = playerRect.y + playerRect.height + ballCircle.radius;
			ballDir.y *= -1;
			_ball.setSpeed(_ball.getSpeed() + 50.f);
		}
		
		if (Intersector.overlaps(_ball.getCircle(), cpuRect)) {
			ballCircle.y = cpuRect.y - ballCircle.radius;
			ballDir.y *= -1;
			_ball.setSpeed(_ball.getSpeed() + 50.f);
		}
	}
	
}
