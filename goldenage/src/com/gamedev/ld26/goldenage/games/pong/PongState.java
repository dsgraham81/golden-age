package com.gamedev.ld26.goldenage.games.pong;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.utils.Config;

public class PongState extends GameState {

	private Paddle cpu;
	private Circle ball;
	
	static Vector2 size = new Vector2(80, 20);
		
	public PongState(GoldenAgeGame game) {
		super(game, new Paddle(new Vector2(Config.window_width / 2, 0), size, Color.WHITE));
		
		cpu = new Paddle(new Vector2(Config.window_width / 2, Config.window_height - size.y), size, Color.WHITE);
		ball = new Circle(Config.window_width / 2, Config.window_height / 2, 10);
	}
	
	@Override
	protected void updateScreen(float delta) {
	
	}
		
	@Override
	protected void renderScreen(float delta) {
		cpu.render();
		Assets.shapes.circle(ball.x, ball.y, ball.radius);
		Assets.shapes.setColor(Color.WHITE);
	}	
}
