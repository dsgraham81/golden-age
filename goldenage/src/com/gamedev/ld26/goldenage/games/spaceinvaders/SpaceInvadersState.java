package com.gamedev.ld26.goldenage.games.spaceinvaders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.gamedev.ld26.goldenage.GoldenAgeGame;
import com.gamedev.ld26.goldenage.games.GameState;
import com.gamedev.ld26.goldenage.games.Paddle;
import com.gamedev.ld26.goldenage.games.PlayerTransition;
import com.gamedev.ld26.goldenage.utils.Config;

public class SpaceInvadersState extends GameState {

	static final float TransitionTime = 1.5f;
	private PlayerTransition _playerTransition;
	
	public SpaceInvadersState(GoldenAgeGame game, GameState previous) {
		super(game, previous);			
	    setupTransition(previous);
	}
	
	protected Paddle createPlayer() {
		return new Paddle(new Vector2(Config.window_width / 2, 0), new Vector2(50, 50), Color.RED, this);
	}

	@Override
	protected void updateScreen(float delta) {
		// TODO Auto-generated method stub
		
	}
	
	private float _transition = 0;	
	
	protected boolean transitionScreen(float delta) {
		_transition += delta;
		
		if (_playerTransition != null) {
			_playerTransition.Update(delta);
		}
		return (_transition < TransitionTime);
	}
	
	private void setupTransition(GameState previousScreen) {
		if (previousScreen == null) return;
		
		_playerTransition = new PlayerTransition(_player,  previousScreen.getPlayer(), TransitionTime);
	}

	@Override
	protected void renderScreen(float delta) {
		// TODO Auto-generated method stub
		
	}

}
