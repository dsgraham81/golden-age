package com.gamedev.ld26.goldenage.games;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.core.Assets;


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
		_player.setPosition(_game.input.getCurrMouse().x, 0);
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
