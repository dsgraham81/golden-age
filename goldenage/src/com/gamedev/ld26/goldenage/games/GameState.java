package com.gamedev.ld26.goldenage.games;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.core.Assets;
import com.gamedev.ld26.goldenage.utils.Config;

public abstract class GameState {

	protected final GoldenAgeGame _game;	
	protected final Paddle _player;
	
	protected GameState(GoldenAgeGame game, Paddle player) {
		_game = game;
		_player = player;
	}
		
	public void update(float delta) {
		updatePlayer(delta);
		updateScreen(delta);		
	}
	
	protected void updatePlayer(float delta) {
		final float scale = _game.input.getCurrMouse().x - _game.input.getPrevMouse().x;
		final float speed = 1.f;
		final float amount = speed * scale;
		_player.move(amount);
		
		// Undo move if we've gone too far
		final Rectangle rect = _player.getRect();
		if (rect.x < 0 || (rect.x + rect.width) > Config.window_width) {
			_player.move(-amount);
		}
	}
	
	protected abstract void updateScreen(float delta);
	
	public void render(float delta) {
		Assets.shapes.begin(ShapeType.Filled);
		_player.render();
		renderScreen(delta);
		Assets.shapes.end();
	}
	
	protected abstract void renderScreen(float delta);
}
