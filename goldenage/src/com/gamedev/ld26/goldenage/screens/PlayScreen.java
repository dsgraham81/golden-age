package com.gamedev.ld26.goldenage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.gamedev.ld26.goldenage.Globals;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.games.G1942.G1942State;
import com.gamedev.ld26.goldenage.games.breakout.BreakoutState;
import com.gamedev.ld26.goldenage.games.centipede.CentipedeState;
import com.gamedev.ld26.goldenage.games.pong.PongState;
import com.gamedev.ld26.goldenage.games.spaceinvaders.SpaceInvadersState;
import com.gamedev.ld26.goldenage.utils.Config;

public class PlayScreen implements Screen {

	private final OrthographicCamera camera = new OrthographicCamera();
	private final GoldenAgeGame game;
	private GameState _gameScreen;
	
	public PlayScreen(GoldenAgeGame game) {
		super();
		this.game = game;
		camera.setToOrtho(false, Config.window_width, Config.window_height);
	}
	
	public void transitionGame(Globals.Games title)	{
		switch (title) {
			case pong:
				_gameScreen = new PongState(game, _gameScreen);
				break;
			case breakout:
				_gameScreen = new BreakoutState(game, _gameScreen);
				break;
			case spaceinvaders:
				_gameScreen = new SpaceInvadersState(game, _gameScreen);
				break;
			case centipede:
				_gameScreen = new CentipedeState(game, _gameScreen);
				break;
			case g1942:
				_gameScreen = new G1942State(game, _gameScreen);
				break;
		}
	}
	
	public void update() {
		if (game.input.isKeyDown(Keys.ESCAPE)) {
			game.setScreen(game.title);
		}
		
		final float delta = Gdx.graphics.getDeltaTime();
		_gameScreen.update(delta);
	}
	
	@Override
	public void render(float delta) {
		if (_gameScreen == null) return;
			
		update();
		
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		_gameScreen.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		game.input.reset();
	}

	@Override
	public void hide() {
		game.input.reset();
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
