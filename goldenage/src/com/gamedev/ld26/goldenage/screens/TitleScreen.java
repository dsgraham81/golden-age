package com.gamedev.ld26.goldenage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.gamedev.ld26.goldenage.Globals;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.utils.Config;
import com.gamedev.ld26.goldenage.utils.Utils;

public class TitleScreen implements Screen {

	private final OrthographicCamera camera = new OrthographicCamera();
	private final GoldenAgeGame game;
	private float accum = 0.f;
	
	public TitleScreen(GoldenAgeGame game) {
		super();
		this.game = game;
		camera.setToOrtho(false, Config.window_width, Config.window_height);
	}
	
	public void update() {
		if (game.input.isKeyDown(Keys.ESCAPE)) {
			Gdx.app.exit();
		} else {
			handleScreen();
		}
		
		accum += Gdx.graphics.getDeltaTime();
	}
	
	protected void handleScreen() {	
		if (game.input.isKeyDown(Keys.NUM_1)) {
			game.setGame(Globals.Games.pong);
		} else if (game.input.isKeyDown(Keys.NUM_2)) {
			game.setGame(Globals.Games.breakout);
		} else if (game.input.isKeyDown(Keys.NUM_3)) {
			game.setGame(Globals.Games.spaceinvaders);
		} else if (game.input.isKeyDown(Keys.NUM_4)) {
			game.setGame(Globals.Games.centipede);
		} else if (game.input.isKeyDown(Keys.NUM_5)) {
			game.setGame(Globals.Games.g1942);
		} 
	}
	
	@Override
	public void render(float delta) {
		update();
		
		Gdx.gl20.glClearColor(0.53f, 0.81f, 0.92f, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Assets.batch.setProjectionMatrix(camera.combined);
		Assets.batch.begin();
		Assets.batch.draw(Assets.background, Config.window_half_width - Assets.background.getWidth() / 2, 0);
		Assets.batch.end();
		
		final String testString1 = "Ludem ipsum dare: sit amit, ld48 !26?";
		final String testString2 = "Testing symbols: ,.?!'\"-+=/\\%()<>:;  -[]- =$= -#- =@=";
		final int w = 16, h = 16;
		final float speed = 8.f;
		final float range = 33.33f;
		final float minx = Config.window_width / 2 - w * ((testString1.length() + testString2.length()) / 4);
		float x1 = (float) Math.sin(accum * speed) * range + minx;
		float x2 = (float) Math.cos(accum * speed / 2) * range + minx;

		Utils.drawText(testString1, x1, Config.window_height / 2 + h / 2, w, h, Color.GREEN);
		Utils.drawText(testString2, x2, Config.window_height / 2 - h / 2, w, h, Color.ORANGE);
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
