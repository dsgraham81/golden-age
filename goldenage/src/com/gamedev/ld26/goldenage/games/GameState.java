package com.gamedev.ld26.goldenage.games;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.core.Assets;


public abstract class GameState {

	protected final GoldenAgeGame _game;	
	protected final Paddle _player;
	private GameState _previousGame;
	
	protected GameState(GoldenAgeGame game, GameState previous, Paddle player) {
		_game = game;
		_player = player;
		_previousGame = previous;
	}
		
	public void update(float delta) {
		updatePlayer(delta);
		
		if (_previousGame != null) {
			if (!transitionScreen(delta)) {
				_previousGame.dispose();
				_previousGame = null;
			}
		} else {		
			updateScreen(delta);
		}
	}
	
	public boolean isTransitioning() {
		return (_previousGame != null);
	}
	
	protected boolean transitionScreen(float delta) {
		return false;				
	}
	
	protected void dispose() {		
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
