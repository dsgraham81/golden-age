package com.gamedev.ld26.goldenage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.gamedev.ld26.goldenage.Globals;
import com.gamedev.ld26.goldenage.Globals.Games;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.games.breakout.BreakoutState;
import com.gamedev.ld26.goldenage.games.pong.PongState;
import com.gamedev.ld26.goldenage.utils.Config;
import com.gamedev.ld26.goldenage.utils.Utils;

public class PlayScreen implements Screen {

	private final OrthographicCamera camera = new OrthographicCamera();
	private final GoldenAgeGame game;
	private GameState _gameScreen;
	private float accum = 0.f;
	
	public PlayScreen(GoldenAgeGame game) {
		super();
		this.game = game;
		camera.setToOrtho(false, Config.window_width, Config.window_height);
	}
	
	public void transitionGame(Globals.Games title)	{
		switch (title) {
			case pong:
				_gameScreen = new PongState(game);
				break;
			case breakout:
				_gameScreen = new BreakoutState(game);
				break;
			case spaceinvaders:
				break;
			case centipede:
				break;
			case g1942:
				break;
		}
	}
	
	public void update() {
		if (game.input.isKeyDown(Keys.ESCAPE)) {
			game.setScreen(game.title);
		}
		
		final float delta = Gdx.graphics.getDeltaTime();
		accum += delta;
		_gameScreen.update(delta);
	}
	
	@Override
	public void render(float delta) {
		if (_gameScreen == null) return;
			
		update();
		
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		_gameScreen.render(Gdx.graphics.getDeltaTime());
		
		final int w = 16, h = 16;
		final float speed = 8.f;
		final float range = 33.33f;
		final float minx = Config.window_width / 2;
		float x = (float) Math.sin(accum * speed) * range + minx;
		float y = (float) Math.cos(accum * speed / 2) * range + minx;
		Utils.drawText("Play screen", x, y, w, h, Color.BLUE);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		game.input.resetKeys();
	}

	@Override
	public void hide() {
		game.input.resetKeys();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		
	}
	
}
